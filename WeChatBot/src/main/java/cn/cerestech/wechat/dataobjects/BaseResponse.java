package cn.cerestech.wechat.dataobjects;

public class BaseResponse {
	private String ErrMsg;
	private Long Ret;

	public BaseResponse(Long ret, String msg) {
		this.Ret = ret;
		this.ErrMsg = msg;
	}

	public String getErrMsg() {
		return ErrMsg;
	}

	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}

	public Long getRet() {
		return Ret;
	}

	public void setRet(Long ret) {
		Ret = ret;
	}

}