package cn.cerestech.wechat.dataobjects;

import java.util.HashMap;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

@SuppressWarnings("serial")
public class QueryStrings extends HashMap<String, String> {

	public QueryStrings(String url) {
		if (!Strings.isNullOrEmpty(url)) {
			String queryString = Splitter.on("?").trimResults().splitToList(url).get(1);
			if (!Strings.isNullOrEmpty(queryString)) {
				Splitter.on("&").trimResults().splitToList(queryString).forEach(pair -> {
					int pos = pair.indexOf("=");
					String k = pair.substring(0, pos);
					String v = pair.substring(pos + 1, pair.length());
					put(k, v);
				});
			}
		}
	}
}
