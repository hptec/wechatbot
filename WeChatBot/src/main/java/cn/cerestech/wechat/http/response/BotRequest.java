package cn.cerestech.wechat.http.response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Supplier;

import org.apache.http.cookie.Cookie;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.http.Headers;

public class BotRequest {

	@SuppressWarnings("unused")
	private BotContext context;

	public BotRequest(BotContext context, Supplier<String> urlBuilder) {
		this.context = context;
		setUrl(urlBuilder.get());
	}

	private String url;

	public URI getUrl() {
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			throw new RuntimeException("URI Error", e);
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 发送请求时需要额外增加的头信息(默认不增加Header)
	 * 
	 * @return
	 */
	public Headers getExternalHeaders() {
		return new Headers();
	}

	/**
	 * 发送请求时需要额外增加的Cookie信息(默认不增加Cookie)
	 * 
	 * @return
	 */
	public List<Cookie> getExternalCookies() {
		return null;
	}

}
