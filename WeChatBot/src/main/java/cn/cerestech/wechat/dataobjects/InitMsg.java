package cn.cerestech.wechat.dataobjects;

import java.util.List;

import cn.cerestech.wechat.entity.WcContact;

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
