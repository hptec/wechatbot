package cn.cerestech.wechat.http.response;

import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.common.collect.Maps;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.AddMsg;
import cn.cerestech.wechat.dataobjects.BaseResponse;
import cn.cerestech.wechat.dataobjects.Profile;
import cn.cerestech.wechat.dataobjects.SyncKey;
import cn.cerestech.wechat.http.Headers;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.http.response.NewMsgResponse.NewMsg;
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

	public class NewMsg {

		private Integer AddMsgCount;
		private List<AddMsg> AddMsgList;
		private BaseResponse BaseResponse;
		private Integer ContinueFlag;
		private Integer DelContactCount;
		// private List<String> DelContactList;// TODO 暂时不清楚格式
		private Integer ModChatRoomMemberCount;
		// private List<String> ModChatRoomMemberList;//TODO 暂时不清楚格式
		private Integer ModContactCount;
		// private List<String> ModContactList;// TODO 暂时不清楚格式
		private Profile Profile;
		private String SKey;
		private SyncKey SyncCheckKey;
		private SyncKey SyncKey;

		public Integer getAddMsgCount() {
			return AddMsgCount;
		}

		public void setAddMsgCount(Integer addMsgCount) {
			AddMsgCount = addMsgCount;
		}

		public List<AddMsg> getAddMsgList() {
			return AddMsgList;
		}

		public void setAddMsgList(List<AddMsg> addMsgList) {
			AddMsgList = addMsgList;
		}

		public BaseResponse getBaseResponse() {
			return BaseResponse;
		}

		public void setBaseResponse(BaseResponse baseResponse) {
			BaseResponse = baseResponse;
		}

		public Integer getContinueFlag() {
			return ContinueFlag;
		}

		public void setContinueFlag(Integer continueFlag) {
			ContinueFlag = continueFlag;
		}

		public Integer getDelContactCount() {
			return DelContactCount;
		}

		public void setDelContactCount(Integer delContactCount) {
			DelContactCount = delContactCount;
		}

		public Integer getModChatRoomMemberCount() {
			return ModChatRoomMemberCount;
		}

		public void setModChatRoomMemberCount(Integer modChatRoomMemberCount) {
			ModChatRoomMemberCount = modChatRoomMemberCount;
		}

		public Integer getModContactCount() {
			return ModContactCount;
		}

		public void setModContactCount(Integer modContactCount) {
			ModContactCount = modContactCount;
		}

		public Profile getProfile() {
			return Profile;
		}

		public void setProfile(Profile profile) {
			Profile = profile;
		}

		public String getSKey() {
			return SKey;
		}

		public void setSKey(String sKey) {
			SKey = sKey;
		}

		public SyncKey getSyncCheckKey() {
			return SyncCheckKey;
		}

		public void setSyncCheckKey(SyncKey syncCheckKey) {
			SyncCheckKey = syncCheckKey;
		}

		public SyncKey getSyncKey() {
			return SyncKey;
		}

		public void setSyncKey(SyncKey syncKey) {
			SyncKey = syncKey;
		}

	}

}
