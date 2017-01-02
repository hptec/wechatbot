package cn.cerestech.wechat.dataobjects;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import cn.cerestech.wechat.http.HttpClientSSLSession;

public class SyncKey {
	private Integer Count;
	private List<NumbericKeyValue> List;

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	public List<NumbericKeyValue> getList() {
		return List;
	}

	public void setList(List<NumbericKeyValue> list) {
		List = list;
	}

	public String toString() {
		if (List == null) {
			return "";
		}
		String str = List.stream().map(kv -> kv.getKey() + "_" + kv.getVal()).collect(Collectors.joining("|"));
		try {
			str = URLEncoder.encode(str, HttpClientSSLSession.ENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return str;

	}
}
