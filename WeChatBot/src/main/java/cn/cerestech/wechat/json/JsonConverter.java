package cn.cerestech.wechat.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface JsonConverter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

}
