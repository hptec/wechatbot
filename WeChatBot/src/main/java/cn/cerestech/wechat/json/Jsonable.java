package cn.cerestech.wechat.json;

public interface Jsonable {

	public default String toJson() {
		return Jsons.from(this).toJson();
	}

}
