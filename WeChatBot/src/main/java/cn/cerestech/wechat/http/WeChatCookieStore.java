package cn.cerestech.wechat.http;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

import com.google.common.base.Splitter;

@SuppressWarnings("serial")
public class WeChatCookieStore extends HashMap<String, Cookie> implements CookieStore {

	public void refresh(Header[] headers) {
		if (headers != null) {
			for (Header h : headers) {
				if (h.getName().equals("Set-Cookie")) {
					String str = Splitter.on(";").trimResults().splitToList(h.getValue()).get(0);
					List<String> kvList = Splitter.on("=").trimResults().splitToList(str);
					String k = kvList.get(0);
					String v = kvList.get(1);
					Cookie c = new BasicClientCookie(k, v);
					addCookie(c);
				}
			}
		}
	}

	public Header toRequestHeader() {
		String v = getCookies().stream().map(c -> {
			return c.getName() + "=" + c.getValue();
		}).collect(Collectors.joining(";"));
		return new BasicHeader("Cookie", v);
	}

	public void addCookie(String name, String value) {
		this.put(name, new BasicClientCookie(name, value));
	}

	@Override
	public void addCookie(Cookie cookie) {
		this.put(cookie.getName(), cookie);
	}

	@Override
	public List<Cookie> getCookies() {
		return this.values().stream().collect(Collectors.toList());
	}

	@Override
	public boolean clearExpired(Date date) {
		if (date == null) {
			return false;
		}
		boolean removed = false;
		for (final Iterator<Cookie> it = this.values().iterator(); it.hasNext();) {
			if (it.next().isExpired(date)) {
				it.remove();
				removed = true;
			}
		}
		return removed;
	}

	@Override
	public void clear() {
		super.clear();
	}

}
