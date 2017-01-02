package cn.cerestech.wechat.dataobjects;

import java.util.List;

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
