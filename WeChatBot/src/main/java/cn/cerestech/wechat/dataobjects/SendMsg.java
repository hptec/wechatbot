package cn.cerestech.wechat.dataobjects;

public class SendMsg {
	private BaseResponse BaseResponse;
	private String LocalID;
	private String MsgID;

	public BaseResponse getBaseResponse() {
		return BaseResponse;
	}

	public void setBaseResponse(BaseResponse baseResponse) {
		BaseResponse = baseResponse;
	}

	public String getLocalID() {
		return LocalID;
	}

	public void setLocalID(String localID) {
		LocalID = localID;
	}

	public String getMsgID() {
		return MsgID;
	}

	public void setMsgID(String msgID) {
		MsgID = msgID;
	}

}
