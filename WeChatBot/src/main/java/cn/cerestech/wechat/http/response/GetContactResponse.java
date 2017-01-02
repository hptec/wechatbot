package cn.cerestech.wechat.http.response;

import org.apache.http.client.methods.CloseableHttpResponse;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.GetContact;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.json.Jsons;

public class GetContactResponse extends BotResponse<GetContact> {

	public GetContactResponse(BotContext context) {
		super(context);
		setRequest(new BotRequest(context,
				() -> "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?lang=" + context.getLang()
						+ "&pass_ticket=" + context.getPassTicket() + "&r=" + System.currentTimeMillis()
						+ "&seq=0&skey=" + context.getSkey()));
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response, HttpClientSSLSession.ENCODING_ISO8859_1, HttpClientSSLSession.ENCODING_UTF8));
		setOriginal(Jsons.from(getText()).to(GetContact.class));
	}

}
