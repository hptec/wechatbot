package cn.cerestech.wechat.http.response;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.common.collect.Maps;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.NewMsg;
import cn.cerestech.wechat.http.Headers;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.json.Jsons;

public class NewMsgResponse extends BotResponse<NewMsg> implements PostJson {

	public NewMsgResponse(BotContext context) {
		super(context);
		setRequest(new BotRequest(context,
				() -> "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid=" + context.getSid() + "&skey="
						+ context.getSkey() + "&lang=" + context.getLang() + "&pass_ticket="
						+ context.getPassTicket()) {
			@Override
			public Headers getExternalHeaders() {
				return new Headers().origin("https://wx2.qq.com");
			}
		});
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response, HttpClientSSLSession.ENCODING_ISO8859_1, HttpClientSSLSession.ENCODING_UTF8));

		setOriginal(Jsons.from(getText()).to(NewMsg.class));

		// 修改关联参数
		getContext().setSyncKey(getOriginal().getSyncKey());// 消息同步Key
	}

	@Override
	public String getJson() {
		Map<String, Object> postJson = Maps.newHashMap();
		postJson.put("BaseRequest", getContext().initBaseRequest());
		postJson.put("SyncKey", getContext().getSyncKey());
		return Jsons.from(postJson).toJson();
	}

}
