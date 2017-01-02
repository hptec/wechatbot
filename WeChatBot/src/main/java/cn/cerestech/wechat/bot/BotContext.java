package cn.cerestech.wechat.bot;

import java.util.List;

import com.google.common.base.Splitter;

import cn.cerestech.wechat.dataobjects.BaseRequest;
import cn.cerestech.wechat.dataobjects.SyncKey;
import cn.cerestech.wechat.entity.WcContact;

public class BotContext {

	// 会话ID
	private String uuid;
	// 登录Ticket
	private String ticket;
	// 语言
	private String lang;
	//
	private String scan;
	// 头像ImageData 数据
	private String avatar;
	//
	private String skey;
	//
	private String sid;
	//
	private String uin;
	//
	private String passTicket;
	//
	private String isGrayScale;
	// 设备ID(登录成功以后自动生成)
	private String deviceId;

	// 微信消息检查的同步key，每次更新消息以后刷新
	private SyncKey syncKey;

	private WcContact currentUser;

	private List<String> chatSet;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getScan() {
		return scan;
	}

	public void setScan(String scan) {
		this.scan = scan;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public void setIsGrayScale(String isgrayscale) {
		this.isGrayScale = isgrayscale;
	}

	public String getPassTicket() {
		return passTicket;
	}

	public void setPassTicket(String passTicket) {
		this.passTicket = passTicket;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * 初始化并返回一个BaseRequest
	 * 
	 * @return
	 */
	public BaseRequest initBaseRequest() {
		return new BaseRequest(getUin(), getSid(), getSkey(), getDeviceId());
	}

	public SyncKey getSyncKey() {
		return syncKey;
	}

	public void setSyncKey(SyncKey syncKey) {
		this.syncKey = syncKey;
	}

	public WcContact getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(WcContact currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * 用户初始化登录以后看到的对话任务清单
	 * 
	 * @return
	 */
	public List<String> getChatSet() {
		return chatSet;
	}

	public void setChatSet(String chatSet) {
		this.chatSet = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(chatSet);
	}

}
