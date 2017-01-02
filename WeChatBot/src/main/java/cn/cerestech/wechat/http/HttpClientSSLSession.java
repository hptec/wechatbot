package cn.cerestech.wechat.http;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.cerestech.wechat.http.response.BotRequest;
import cn.cerestech.wechat.http.response.BotResponse;
import cn.cerestech.wechat.http.response.PostJson;

public abstract class HttpClientSSLSession {

	public static final String ENCODING_ISO8859_1 = "ISO8859-1";
	public static final String ENCODING_UTF8 = "UTF-8";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected CloseableHttpClient httpClient = null;

	protected WeChatCookieStore cookieStore = new WeChatCookieStore();

	public CloseableHttpClient getHttpClient() {
		if (httpClient == null) {
			System.setProperty("jsse.enableSNIExtension", "false");
			try {
				SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).build();
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
				return HttpClients.custom().setSSLSocketFactory(sslsf).build();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}
			RequestConfig config = RequestConfig.custom().setConnectTimeout(6000).setSocketTimeout(6000)
					.setCookieSpec(CookieSpecs.STANDARD_STRICT).build(); // 设置超时及cookie策略
			httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

		}
		return httpClient;
	}

	public <T extends BotResponse<?>> T get(T request) {
		long start = System.currentTimeMillis();
		HttpGet get = createGet(request.getRequest());
		try {
			CloseableHttpResponse response = getHttpClient().execute(get);
			long time = System.currentTimeMillis() - start;
			long stamp=System.currentTimeMillis();
			cookieStore.refresh(response.getHeaders("Set-Cookie"));
			request.parse(response);
			long parse = System.currentTimeMillis() - stamp;
			logger.trace(
					"POST " + request.getClass().getSimpleName() + " time: " + time + "(ms) Parse: " + parse + "(ms)");
			return request;
		} catch (IOException e) {
			throw new RuntimeException("Http Error", e);
		}
	}

	public <T extends BotResponse<?>> T post(T request) {
		long start = System.currentTimeMillis();
		HttpPost post = new HttpPost();
		// 合并header
		Headers headersTo = request.getRequest().getExternalHeaders();

		// 合并cookie
		List<Cookie> externalCookies = request.getRequest().getExternalCookies();
		if (externalCookies != null) {
			externalCookies.forEach(c -> cookieStore.addCookie(c));
		}
		// 检查post内容
		if (request instanceof PostJson) {
			PostJson pj = (PostJson) request;
			headersTo.add("ContentType", "application/json; charset=UTF-8");
			post.setEntity(new ByteArrayEntity(pj.getJson().getBytes()));
		}
		for (Header h : headersTo) {
			post.addHeader(h);
		}
		post.addHeader(cookieStore.toRequestHeader());

		post.setURI(request.getRequest().getUrl());

		try {
			CloseableHttpResponse response = getHttpClient().execute(post);
			long time = System.currentTimeMillis() - start;
			long stamp=System.currentTimeMillis();
			cookieStore.refresh(response.getHeaders("Set-Cookie"));
			request.parse(response);
			long parse = System.currentTimeMillis() - stamp;
			logger.trace(
					"POST " + request.getClass().getSimpleName() + " time: " + time + "(ms) Parse: " + parse + "(ms)");
			return request;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public CloseableHttpResponse uploadFile(String url, Headers headers, Map<String, String> textPart,
			String filenameOnly, byte[] buf) {
		HttpPost post = new HttpPost(url);

		// 合并header
		Headers headersTo = new Headers();
		if (headers != null) {
			headersTo.addAll(headers);
		}
		for (Header h : headersTo) {
			post.addHeader(h);
		}
		post.addHeader(cookieStore.toRequestHeader());

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		textPart.forEach((k, v) -> {
			builder.addTextBody(k, v);
		});

		builder.addBinaryBody("filename", buf, ContentType.DEFAULT_BINARY, filenameOnly);
		HttpEntity entity = builder.build();
		post.setEntity(entity);

		try {
			post.setURI(new URI(url));
			CloseableHttpResponse response = getHttpClient().execute(post);
			cookieStore.refresh(response.getHeaders("Set-Cookie"));
			return response;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 清空会话及Cookie等信息。重置为初始状态
	 */
	protected void clear() {
		this.httpClient = null;
		cookieStore = new WeChatCookieStore();
	}

	/**
	 * 根据request的设置创建httpGet，并合并cookie header等
	 * 
	 * @param get
	 * @param request
	 */
	private HttpGet createGet(BotRequest request) {
		HttpGet get = new HttpGet();

		// 合并header
		Headers headersTo = request.getExternalHeaders();
		for (Header h : headersTo) {
			get.addHeader(h);
		}

		// 合并cookie
		List<Cookie> externalCookies = request.getExternalCookies();
		if (externalCookies != null) {
			externalCookies.forEach(c -> cookieStore.addCookie(c));
		}
		get.addHeader(cookieStore.toRequestHeader());

		get.setURI(request.getUrl());
		return get;
	}
}
