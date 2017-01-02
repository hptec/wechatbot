package cn.cerestech.wechat.bot.entity;

import java.util.List;

import cn.cerestech.wechat.bot.enums.ContactType;
import cn.cerestech.wechat.bot.enums.Sex;
import cn.cerestech.wechat.entity.WcContact;

public class Contact {

	private String userName;// 账户ID
	private String alias;// 帐号别名
	private String nickName;// 昵称
	private String signature;// 签名
	private Integer verifyFlag;// 校验位
	private ContactType type;// 账户类型
	private WcContact original;// 原始数据
	private Sex sex;// 性别

	private List<Contact> members;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(Integer verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public WcContact getOriginal() {
		return original;
	}

	public void setOriginal(WcContact original) {
		this.original = original;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public List<Contact> getMembers() {
		return members;
	}

	public void setMembers(List<Contact> members) {
		this.members = members;
	}

}
