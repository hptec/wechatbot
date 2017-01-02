package cn.cerestech.wechat.http.response;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.common.primitives.Ints;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.http.response.BotResponse.JsKeyPair;

@SuppressWarnings("rawtypes")
public class CheckMsgResponse extends BotResponse<JsKeyPair> {

	private Integer code;
	private Integer selector;

	public CheckMsgResponse(BotContext context) {
		super(context);
		setRequest(new BotRequest(context,
				() -> "https://webpush.wx2.qq.com/cgi-bin/mmwebwx-bin/synccheck?r=" + System.currentTimeMillis()
						+ "&skey=" + context.getSkey() + "&sid=" + context.getSid() + "&uin=" + context.getUin()
						+ "&deviceid=" + context.getDeviceId() + "&synckey=" + context.getSyncKey() + "&_="
						+ System.currentTimeMillis()));

	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response));
		setOriginal(new JsKeyPair(getText()));
		// 解析返回参数
		setCode(Ints.tryParse(getOriginal().get("window.synccheck.retcode")));
		setSelector(Ints.tryParse(getOriginal().get("window.synccheck.selector")));

	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getSelector() {
		return selector;
	}

	public void setSelector(Integer selector) {
		this.selector = selector;
	}

}
