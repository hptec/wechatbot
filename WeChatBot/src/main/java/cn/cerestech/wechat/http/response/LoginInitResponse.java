package cn.cerestech.wechat.http.response;

import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.common.collect.Maps;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.BaseResponse;
import cn.cerestech.wechat.dataobjects.SubscribeMsg;
import cn.cerestech.wechat.dataobjects.SyncKey;
import cn.cerestech.wechat.entity.WcContact;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.http.response.LoginInitResponse.InitMsg;
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

	public class InitMsg {

		private BaseResponse BaseResponse;
		private Integer Count;
		// 第一次进入的对话列表对应的信息
		private List<WcContact> ContactList;
		private SyncKey SyncKey;
		// 第一次进入的时候展开的对话列表
		private String ChatSet;
		private String SKey;
		private Long ClientVersion;
		private Long SystemTime;
		private Integer GrayScale;
		private Integer InviteStartCount;
		// 公众号订阅文章
		private Integer MPSubscribeMsgCount;
		private List<SubscribeMsg> MPSubscribeMsgList;

		private Integer ClickReportInterval;
		// 当前登录用户的信息
		private WcContact User;

		public BaseResponse getBaseResponse() {
			return BaseResponse;
		}

		public void setBaseResponse(BaseResponse baseResponse) {
			BaseResponse = baseResponse;
		}

		public Integer getCount() {
			return Count;
		}

		public void setCount(Integer count) {
			Count = count;
		}

		public List<WcContact> getContactList() {
			return ContactList;
		}

		public void setContactList(List<WcContact> contactList) {
			ContactList = contactList;
		}

		public SyncKey getSyncKey() {
			return SyncKey;
		}

		public void setSyncKey(SyncKey syncKey) {
			SyncKey = syncKey;
		}

		public String getChatSet() {
			return ChatSet;
		}

		public void setChatSet(String chatSet) {
			ChatSet = chatSet;
		}

		public String getSKey() {
			return SKey;
		}

		public void setSKey(String sKey) {
			SKey = sKey;
		}

		public Long getClientVersion() {
			return ClientVersion;
		}

		public void setClientVersion(Long clientVersion) {
			ClientVersion = clientVersion;
		}

		public Long getSystemTime() {
			return SystemTime;
		}

		public void setSystemTime(Long systemTime) {
			SystemTime = systemTime;
		}

		public Integer getGrayScale() {
			return GrayScale;
		}

		public void setGrayScale(Integer grayScale) {
			GrayScale = grayScale;
		}

		public Integer getInviteStartCount() {
			return InviteStartCount;
		}

		public void setInviteStartCount(Integer inviteStartCount) {
			InviteStartCount = inviteStartCount;
		}

		public Integer getClickReportInterval() {
			return ClickReportInterval;
		}

		public void setClickReportInterval(Integer clickReportInterval) {
			ClickReportInterval = clickReportInterval;
		}

		public Integer getMPSubscribeMsgCount() {
			return MPSubscribeMsgCount;
		}

		public void setMPSubscribeMsgCount(Integer mPSubscribeMsgCount) {
			MPSubscribeMsgCount = mPSubscribeMsgCount;
		}

		public List<SubscribeMsg> getMPSubscribeMsgList() {
			return MPSubscribeMsgList;
		}

		public void setMPSubscribeMsgList(List<SubscribeMsg> mPSubscribeMsgList) {
			MPSubscribeMsgList = mPSubscribeMsgList;
		}

		public WcContact getUser() {
			return User;
		}

		public void setUser(WcContact user) {
			User = user;
		}

	}

}
