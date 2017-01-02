package cn.cerestech.wechat.http.response;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.BaseResponse;
import cn.cerestech.wechat.entity.WcContact;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.http.response.GetContactResponse.GetContact;
import cn.cerestech.wechat.json.Jsons;

public class GetContactResponse extends BotResponse<GetContact> {

	public GetContactResponse(BotContext context) {
		super(context);
		setRequest(new BotRequest(context,
				() -> "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?lang=" + context.getLang()
						+ "&pass_ticket=" + context.getPassTicket() + "&r=" + System.currentTimeMillis()
						+ "&seq=0&skey=" + context.getSkey()));
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response, HttpClientSSLSession.ENCODING_ISO8859_1, HttpClientSSLSession.ENCODING_UTF8));
		setOriginal(Jsons.from(getText()).to(GetContact.class));
	}
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
}
