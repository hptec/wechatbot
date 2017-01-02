package cn.cerestech.wechat.dataobjects;

import java.util.List;

import cn.cerestech.wechat.entity.WcContact;

public class GetContact {
	private BaseResponse BaseResponse;
	private Integer MemberCount;
	private List<WcContact> MemberList;
	private Integer seq;

	public BaseResponse getBaseResponse() {
		return BaseResponse;
	}

	public void setBaseResponse(BaseResponse baseResponse) {
		BaseResponse = baseResponse;
	}

	public Integer getMemberCount() {
		return MemberCount;
	}

	public void setMemberCount(Integer memberCount) {
		MemberCount = memberCount;
	}

	public List<WcContact> getMemberList() {
		return MemberList;
	}

	public void setMemberList(List<WcContact> memberList) {
		MemberList = memberList;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
