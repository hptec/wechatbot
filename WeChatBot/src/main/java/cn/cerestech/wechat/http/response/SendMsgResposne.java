package cn.cerestech.wechat.http.response;

import org.apache.http.client.methods.CloseableHttpResponse;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.SendMsg;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.json.Jsons;
import cn.cerestech.wechat.msg.Msg;

public class SendMsgResposne extends BotResponse<SendMsg> implements PostJson {
	private Msg msg;

	public SendMsgResposne(BotContext context, Msg msg) {
		super(context);
		this.msg = msg;
		setRequest(new BotRequest(context, () -> "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?lang="
				+ context.getLang() + "&pass_ticket=" + context.getPassTicket()));
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response, HttpClientSSLSession.ENCODING_ISO8859_1, HttpClientSSLSession.ENCODING_UTF8));
		setOriginal(Jsons.from(getText()).to(SendMsg.class));
		// 解析返回参数
	}

	@Override
	public String getJson() {
		return Jsons.from(msg).toJson();
	}

	public Msg getMsg() {
		return msg;
	}

	public void setMsg(Msg msg) {
		this.msg = msg;
	}

}
