package cn.cerestech.wechat.http.response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import cn.cerestech.wechat.bot.BotContext;

public abstract class BotResponse<T> {

	public static final String APPID = "wx782c26e4c19acffb";

	public BotResponse(BotContext context) {
		setContext(context);
	}

	// 使用的上下文信息
	private BotContext context;
	// 使用的请求信息
	private BotRequest request;

	// 返回的文本信息
	private String text;
	// 返回的原始对象信息
	private T original;
	// 返回的原始http响应
	private CloseableHttpResponse httpResponse;

	/**
	 * 解析返回的结果
	 * 
	 * @param response
	 */
	public abstract void parse(CloseableHttpResponse response);

	protected String toString(CloseableHttpResponse response) {
		try {
			return EntityUtils.toString(response.getEntity());
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected String toString(CloseableHttpResponse response, String fromEncoding, String toEncoding) {
		String str = toString(response);
		try {
			return new String(str.getBytes(fromEncoding), toEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BotContext getContext() {
		return context;
	}

	public void setContext(BotContext context) {
		this.context = context;
	}

	public BotRequest getRequest() {
		return request;
	}

	public void setRequest(BotRequest request) {
		this.request = request;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public T getOriginal() {
		return original;
	}

	public void setOriginal(T original) {
		this.original = original;
	}

	public CloseableHttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(CloseableHttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	class JsKeyPair {
		private ScriptEngine engine;

		public JsKeyPair(String content) {

			ScriptEngineManager factory = new ScriptEngineManager();
			engine = factory.getEngineByName("JavaScript");
			try {
				engine.eval(" var window={QRLogin:{},synccheck:{}}");
				engine.eval(content);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}

		public String get(String key) {
			try {
				return engine.eval(key).toString();
			} catch (ScriptException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@SuppressWarnings("serial")
	class QueryStrings extends HashMap<String, String> {

		public QueryStrings(String url) {
			if (!Strings.isNullOrEmpty(url)) {
				String queryString = Splitter.on("?").trimResults().splitToList(url).get(1);
				if (!Strings.isNullOrEmpty(queryString)) {
					Splitter.on("&").trimResults().splitToList(queryString).forEach(pair -> {
						int pos = pair.indexOf("=");
						String k = pair.substring(0, pos);
						String v = pair.substring(pos + 1, pair.length());
						put(k, v);
					});
				}
			}
		}
	}

	class XmlKeyPair {

		private Document document;
		private Element root;

		public XmlKeyPair(String xml) {
			document = Jsoup.parse(xml);
		}

		public String selectText(String cssQuery) {
			return document.select(cssQuery).text();
		}

		public Document getDocument() {
			return document;
		}

		public void setDocument(Document document) {
			this.document = document;
		}

		public Element getRoot() {
			return root;
		}

		public void setRoot(Element root) {
			this.root = root;
		}

	}
}
