package cn.cerestech.wechat.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class Jsons {

	private JsonElement root;
	private Boolean serializeNull = Boolean.FALSE;
	private Boolean toUnicode = Boolean.TRUE;
	private Boolean prettyPrint = Boolean.FALSE;
	private static Map<Class<?>, Object> adapters = Maps.newHashMap();
	private static List<TypeAdapterFactory> factories = Lists.newArrayList();

	/**
	 * 转换成为json时，表示不会将中文等符号转化成unicode 编码的转义字符
	 * 
	 * @return
	 */
	public Jsons disableUnicode() {
		toUnicode = Boolean.FALSE;
		return this;
	}

	/**
	 * 转化时是否序列化null 或者 empty str
	 * 
	 * @return
	 */
	public Jsons serializeNull() {
		serializeNull = Boolean.TRUE;
		return this;
	}

	/**
	 * 按照方便阅读的格式输出
	 * 
	 * @return
	 */
	public Jsons prettyPrint() {
		prettyPrint = Boolean.TRUE;
		return this;
	}

	public <T> T to(Class<T> clazz) {
		return getGson().fromJson(toJson(), clazz);
	}

	public <T> T to(TypeToken<T> typeToken) {
		return getGson().fromJson(toJson(), typeToken.getType());
	}

	/**
	 * 判断是否有这个属性
	 * 
	 * @param propName
	 * @return
	 */
	public Boolean has(String propName) {
		return root.getAsJsonObject().has(propName);
	}

	public static Jsons from(Object obj) {
		Jsons me = new Jsons();
		if (obj == null) {
			me.root = null;
			return me;
		}
		if (obj instanceof String) {
			// 传入的字符串
			me.root = me.getGson().fromJson(obj.toString(), JsonElement.class);
			return me;
		}

		if (obj instanceof JsonElement) {
			me.root = (JsonElement) obj;
			return me;
		}

		if (obj instanceof JsonObject) {
			me.root = (JsonObject) obj;
			return me;
		}

		if (obj instanceof Jsons) {
			me.root = ((Jsons) obj).getRoot();
			return me;
		}

		me.root = me.getGson().toJsonTree(obj);

		return me;
	}

	/**
	 * 返回一个空的Jsons对象
	 * 
	 * @return
	 */
	public static Jsons empty() {
		return Jsons.from("{}");
	}

	/**
	 * 获取Gson对象
	 * 
	 * @return
	 */
	private Gson getGson() {

		GsonBuilder builder = new GsonBuilder();
		builder = serializeNull ? builder.serializeNulls() : builder;
		builder = toUnicode ? builder : builder.disableHtmlEscaping();
		builder = prettyPrint ? builder.setPrettyPrinting() : builder;

		// 追加外部Adapter
		if (adapters != null && !adapters.isEmpty()) {

			Set<Entry<Class<?>, Object>> sets = adapters.entrySet();
			for (Entry<Class<?>, Object> entry : sets) {
				builder.registerTypeHierarchyAdapter(entry.getKey(), entry.getValue());
			}
		}
		// 追加外部factory
		if (factories != null && !factories.isEmpty()) {
			for (TypeAdapterFactory f : factories) {
				builder.registerTypeAdapterFactory(f);
			}
		}

		return builder.create();
	}

	public String toJson() {
		return getGson().toJson(root);
	}

	public String toPrettyJson() {
		Jsons me = new Jsons();
		me.serializeNull = this.serializeNull;
		me.toUnicode = this.toUnicode;
		me.root = root;
		return me.prettyPrint().toJson();
	}

	public Jsons printPrettyJson() {
		System.out.println(this.toPrettyJson());
		return this;
	}

	public Jsons get(String... nodeName) {
		JsonElement root = this.root;
		if (root == null)
			return new Jsons();
		for (String node : nodeName) {
			if (root.isJsonObject()) {
				if (root.getAsJsonObject().has(node)) {
					// 拥有属性
					root = root.getAsJsonObject().get(node);
				} else {
					// 没有这个属性,返回空属性
					return Jsons.from(null);
				}
			} else if (root.isJsonNull()) {
				// 空则返回空属性
				return Jsons.from(null);
			} else if (root.isJsonPrimitive()) {
				// 基本类型则抛出错误
				throw new IllegalArgumentException("property [" + node + "] is primitive");
			} else if (root.isJsonArray()) {
				// 数组类型则抛出错误
				throw new IllegalArgumentException("property [" + node + "] is array");
			}
		}
		Jsons ret = new Jsons();
		ret.root = root;
		return ret;
	}

	public Jsons get(int i) {
		if (root.isJsonArray()) {
			Jsons ret = new Jsons();
			ret.root = root.getAsJsonArray().get(i);
			return ret;
		} else {
			// 不是数组类型则抛出错误
			throw new IllegalArgumentException("Is not a array");
		}
	}

	public String asString() {
		return asString(null);
	}

	public String asString(String defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsString();
	}

	public BigDecimal asBigDecimal() {
		return asBigDecimal(null);
	}

	public BigDecimal asBigDecimal(BigDecimal defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsBigDecimal();
	}

	public BigInteger asBigInteger() {
		return asBigInteger(null);
	}

	public BigInteger asBigInteger(BigInteger defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsBigInteger();
	}

	public Boolean asBoolean() {
		return asBoolean(null);
	}

	public Boolean asBoolean(Boolean defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsBoolean();
	}

	public Byte asByte() {
		return asByte(null);
	}

	public Byte asByte(Byte defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsByte();
	}

	public Character asCharacter() {
		return asCharacter(null);
	}

	public Character asCharacter(Character defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsCharacter();
	}

	public Double asDouble() {
		return asDouble(null);
	}

	public Double asDouble(Double defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsDouble();
	}

	public Float asFloat() {
		return asFloat(null);
	}

	public Float asFloat(Float defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsFloat();
	}

	public Integer asInt() {
		return asInt(null);
	}

	public Integer asInt(Integer defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsInt();
	}

	public Long asLong() {
		return asLong(null);
	}

	public Long asLong(Long defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsLong();
	}

	public Number asNumber() {
		return asNumber(null);
	}

	public Number asNumber(Number defaultValue) {
		return root == null || root.isJsonNull() ? defaultValue : root.getAsNumber();
	}

	public List<Jsons> asList() {
		List<Jsons> list = Lists.newArrayList();
		if (root != null && root.isJsonArray()) {
			root.getAsJsonArray().forEach(ele -> {
				Jsons j = new Jsons();
				j.root = ele;
				list.add(j);
			});
		}
		return list;
	}

	public JsonElement getRoot() {
		return root;
	}

	public Boolean isNull() {
		if (root == null) {
			return Boolean.TRUE;
		} else if (root.isJsonNull()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public Boolean isArray() {
		if (isNull()) {
			return Boolean.FALSE;
		} else if (root.isJsonArray()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public Boolean isObject() {
		if (isNull()) {
			return Boolean.FALSE;
		} else if (root.isJsonObject()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public String toString() {
		return this.toPrettyJson();
	}

	/**
	 * 添加值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Jsons put(String key, Object value) {
		if (value == null) {
			return this;
		} else if (value instanceof Jsons) {
			root.getAsJsonObject().add(key, ((Jsons) value).getRoot());
		} else if (value instanceof String) {
			root.getAsJsonObject().addProperty(key, (String) value);
		} else {
			put(key, Jsons.from(value));
		}
		return this;
	}

	public Jsons remove(String key) {
		root.getAsJsonObject().remove(key);
		return this;
	}

	public static void registerAdapter(Class<?> type, Object adapter) {
		if (type != null && adapter != null) {
			adapters.put(type, adapter);
		}
	}

	public static void registerAdapterFactory(TypeAdapterFactory factory) {
		if (factory != null) {
			factories.add(factory);
		}
	}

}
