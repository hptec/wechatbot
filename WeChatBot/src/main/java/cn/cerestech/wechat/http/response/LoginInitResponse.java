package cn.cerestech.wechat.http.response;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.common.collect.Maps;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.InitMsg;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.json.Jsons;

public class LoginInitResponse extends BotResponse<InitMsg> implements PostJson {

	public LoginInitResponse(BotContext context) {
		super(context);
		setRequest(new BotRequest(context, () -> "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=-884115785&lang="
				+ context.getLang() + "&pass_ticket=" + context.getPassTicket()));
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response, HttpClientSSLSession.ENCODING_ISO8859_1, HttpClientSSLSession.ENCODING_UTF8));
		setOriginal(Jsons.from(getText()).to(InitMsg.class));

		// 修改关联参数
		getContext().setSyncKey(getOriginal().getSyncKey());// 消息同步Key
		getContext().setCurrentUser(getOriginal().getUser());// 当前登录用户信息
		getContext().setChatSet(getOriginal().getChatSet());// 进入时展开的对话框任务列表清单
	}

	@Override
	public String getJson() {
		Map<String, Object> postJson = Maps.newHashMap();
		postJson.put("BaseRequest", getContext().initBaseRequest());
		return Jsons.from(postJson).toJson();
	}

}
