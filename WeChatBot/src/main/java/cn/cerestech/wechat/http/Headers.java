package cn.cerestech.wechat.http;

import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

@SuppressWarnings("serial")
public class Headers extends ArrayList<Header> {

	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String CACHE_CONTROL = "Cache-Control";
	public static final String CONNECTION = "Connection";
	public static final String PRAGMA = "Pragma";
	public static final String USER_AGENT = "User-Agent";
	public static final String REFERER = "Referer";
	public static final String ORIGIN = "Origin";

	public Headers() {
		accept("*/*");
		acceptEncoding("gzip, deflate, sdch, br");
		acceptLanguage("zh-CN,zh;q=0.8,en;q=0.6");
		cacheControl("no-cache");
		connection("keep-alive");
		pragma("no-cache");
		userAgent(
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
	}

	public static Headers add(Header... h) {
		Headers hs = new Headers();
		for (Header header : h) {
			hs.add(header);
		}
		return hs;
	}

	public Headers add(String name, String value) {
		BasicHeader bh = new BasicHeader(name, value);
		add(bh);
		return this;
	}

	public Header[] toArray() {
		Header[] h = new Header[size()];
		toArray(h);
		return h;
	}

	public Headers addAll(Headers headers) {
		if (headers != null) {
			headers.forEach(h -> {
				this.add(h);
			});
		}
		return this;
	}

	public Headers accept(String accept) {
		return add(ACCEPT, accept);
	}

	public Headers acceptEncoding(String encoding) {
		return add(ACCEPT_ENCODING, encoding);
	}

	public Headers acceptLanguage(String language) {
		return add(ACCEPT_LANGUAGE, language);
	}

	public Headers cacheControl(String cacheControl) {
		return add(CACHE_CONTROL, cacheControl);
	}

	public Headers connection(String connection) {
		return add(CONNECTION, connection);
	}

	public Headers pragma(String pragma) {
		return add(PRAGMA, pragma);
	}

	public Headers userAgent(String agent) {
		return add(USER_AGENT, agent);
	}

	public Headers referer(String referer) {
		return add(REFERER, referer);
	}

	public Headers origin(String origin) {
		return add(ORIGIN, origin);
	}
}
