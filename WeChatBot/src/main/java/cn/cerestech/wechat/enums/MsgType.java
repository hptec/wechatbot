package cn.cerestech.wechat.enums;

import com.google.common.primitives.Ints;

public enum MsgType implements DescribableEnum {
	TEXT("1", "文本"), //
	VIDEO("43", "视频"), //
	;
	private String key, desc;

	private MsgType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public Integer typeCode() {
		return Ints.tryParse(key);
	}

	public String desc() {
		return this.desc;
	}
}
