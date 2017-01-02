package cn.cerestech.wechat.bot.enums;

import cn.cerestech.wechat.enums.DescribableEnum;

/**
 * 性别
 * 
 * @author harryhe
 *
 */
public enum Sex implements DescribableEnum {
	UNKNOWN("UNKNOWN", "未设置"), //
	MALE("MALE", "男"), //
	FEMALE("FEMALE", "女"), //
	;
	private String key, desc;

	private Sex(String key, String desc) {
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
