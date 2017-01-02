package cn.cerestech.wechat.dataobjects;

import java.util.List;

import cn.cerestech.wechat.entity.WcContact;

public class BatchGetContact {
	private BaseResponse BaseResponse;
	private Integer Count;
	private List<WcContact> ContactList;

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

}
