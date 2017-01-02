package cn.cerestech.wechat.http.response;

import org.apache.http.client.methods.CloseableHttpResponse;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.Random;
import cn.cerestech.wechat.http.response.BotResponse.XmlKeyPair;

@SuppressWarnings("rawtypes")
public class PassTicketResponse extends BotResponse<XmlKeyPair> {

	private String passTicket;
	private String skey;
	private String sid;
	private String uin;
	private String isGrayScale;
	private String deviceId;

	public PassTicketResponse(BotContext context) {
		super(context);
		setRequest(new BotRequest(context,
				() -> "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=" + context.getTicket()
						+ "&uuid=" + context.getUuid() + "&lang=" + context.getLang() + "&scan=" + context.getScan()
						+ "&fun=new&version=v2&lang=" + context.getLang()));
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response));
		setOriginal(new XmlKeyPair(getText()));
		// 解析返回参数
		setSkey(getOriginal().selectText("skey"));
		setSid(getOriginal().selectText("wxsid"));
		setUin(getOriginal().selectText("wxuin"));
		setPassTicket(getOriginal().selectText("pass_ticket"));
		setIsGrayScale(getOriginal().selectText("isgrayscale"));
		setDeviceId("e" + Random.number(15));

		// 修改关联参数
		getContext().setSkey(getSkey());
		getContext().setSid(getSid());
		getContext().setUin(getUin());
		getContext().setPassTicket(getPassTicket());
		getContext().setIsGrayScale(getIsGrayScale());
		getContext().setDeviceId(getDeviceId());

	}

	public String getPassTicket() {
		return passTicket;
	}

	public void setPassTicket(String passTicket) {
		this.passTicket = passTicket;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getIsGrayScale() {
		return isGrayScale;
	}

	public void setIsGrayScale(String isGrayScale) {
		this.isGrayScale = isGrayScale;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
