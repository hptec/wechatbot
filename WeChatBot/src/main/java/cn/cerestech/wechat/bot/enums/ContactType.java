package cn.cerestech.wechat.bot.enums;

import cn.cerestech.wechat.enums.DescribableEnum;

/**
 * 帐号类型
 * 
 * @author harryhe
 *
 */
public enum ContactType implements DescribableEnum {
	PERSON("PERSON", "个人"), //
	GROUP("GROUP", "群聊"), //
	MP("MP", "公众号"),//
	;
	private String key, desc;

	private ContactType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}
}
