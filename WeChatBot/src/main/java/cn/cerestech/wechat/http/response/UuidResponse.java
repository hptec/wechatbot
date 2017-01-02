package cn.cerestech.wechat.http.response;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.http.Headers;
import cn.cerestech.wechat.http.response.BotResponse.JsKeyPair;

@SuppressWarnings("rawtypes")
public class UuidResponse extends BotResponse<JsKeyPair> {

	public UuidResponse(BotContext context) {
		super(context);
		setRequest(new BotRequest(context,
				() -> "https://login.wx.qq.com/jslogin?appid=" + APPID
						+ "&redirect_uri=https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage&fun=new&lang=zh_CN&_="
						+ System.currentTimeMillis()) {

			@Override
			public Headers getExternalHeaders() {
				return new Headers().add("Upgrade-Insecure-Requests", "1");
			}

			@Override
			public List<Cookie> getExternalCookies() {
				List<Cookie> cookies = Lists.newArrayList();
				cookies.add(new BasicClientCookie("MM_WX_NOTIFY_STATE", "1"));
				cookies.add(new BasicClientCookie("MM_WX_SOUND_STATE", "1"));
				return cookies;
			}
		});
	}

	private Integer code;
	private String uuid;

	@Override
	public void parse(CloseableHttpResponse response) {
		// 保留原始信息
		setHttpResponse(response);
		setText(toString(response));
		setOriginal(new JsKeyPair(getText()));

		// 解析返回参数
		setCode(Ints.tryParse(getOriginal().get("window.QRLogin.code")));
		setUuid(getOriginal().get("window.QRLogin.uuid"));

		// 设置关联影响参数
		getContext().setUuid(getUuid());
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
