package cn.cerestech.wechat.enums;

public enum BotLifeCyclePhase implements DescribableEnum {
	STOP("STOP", "停止"), //
	RUNNING("RUNNING", "运行中"), //
	;
	private String key, desc;

	private BotLifeCyclePhase(String key, String desc) {
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
