package cn.cerestech.wechat.http.response;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.common.primitives.Ints;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.http.response.BotResponse.JsKeyPair;

@SuppressWarnings("rawtypes")
public class ScanListenResponse extends BotResponse<JsKeyPair> {

	private Integer code;
	private String userAvatar;
	private String redirectUri;

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public ScanListenResponse(BotContext context) {
		super(context);
		setRequest(
				new BotRequest(context, () -> "https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid="
						+ context.getUuid() + "&tip=0&r=-883167434&_=" + System.currentTimeMillis()));
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response));
		setOriginal(new JsKeyPair(getText()));
		// 解析返回参数
		setCode(Ints.tryParse(getOriginal().get("window.code")));
		switch (getCode()) {
		case 201:
			// 扫码成功获取头像ImageData
			setUserAvatar(getOriginal().get("window.userAvatar"));
			break;
		case 200:
			// 登录成功
			setRedirectUri(getOriginal().get("window.redirect_uri"));
			QueryStrings qs = new QueryStrings(getRedirectUri());

			// 设置关联影响参数
			getContext().setTicket(qs.get("ticket"));
			getContext().setLang(qs.get("lang"));
			getContext().setScan(qs.get("scan"));
		case 408:
			// 请求超时
			break;
		case 400:
			// 重刷页面
			break;
		}

	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

}
