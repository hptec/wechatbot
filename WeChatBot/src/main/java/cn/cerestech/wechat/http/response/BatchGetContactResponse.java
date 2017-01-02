package cn.cerestech.wechat.http.response;

import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.BatchGetContact;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.json.Jsons;

public class BatchGetContactResponse extends BotResponse<BatchGetContact> implements PostJson {

	private String[] userNameList;

	public BatchGetContactResponse(BotContext context, String[] userNameList) {
		super(context);
		setRequest(new BotRequest(context,
				() -> "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxbatchgetcontact?type=ex&r="
						+ System.currentTimeMillis() + "&lang=" + context.getLang() + "&pass_ticket="
						+ context.getPassTicket()));
		this.userNameList = userNameList;
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response, HttpClientSSLSession.ENCODING_ISO8859_1, HttpClientSSLSession.ENCODING_UTF8));
		setOriginal(Jsons.from(getText()).to(BatchGetContact.class));
	}

	@Override
	public String getJson() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("BaseRequest", getContext().initBaseRequest());
		map.put("Count", getUserNameList().length);
		List<Map<String, String>> list = Lists.newArrayList();
		for (String name : userNameList) {
			Map<String, String> person = Maps.newHashMap();
			person.put("UserName", name);
			person.put("EncryChatRoomId", "");
			list.add(person);
		}
		map.put("List", list);
		return Jsons.from(map).toJson();
	}

	public String[] getUserNameList() {
		return userNameList;
	}

	public void setUserNameList(String[] userNameList) {
		this.userNameList = userNameList;
	}

}
