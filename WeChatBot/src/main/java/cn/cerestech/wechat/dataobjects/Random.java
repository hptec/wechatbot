package cn.cerestech.wechat.dataobjects;

import java.util.UUID;

public class Random {
	private static java.util.Random random = new java.util.Random(System.currentTimeMillis());
	private static char[] alphas = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private static char[] numbers = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static char[] all;

	public static String number(Integer len) {
		StringBuffer buf = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			buf.append(random.nextInt(10));
		}
		return buf.toString();
	}

	public static String chars(Integer len) {
		StringBuffer buf = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			char c = alphas[random.nextInt(alphas.length)];
			if (random.nextBoolean()) {
				// 随机决定大小写
				c = Character.toLowerCase(c);
			}
			buf.append(c);
		}
		return buf.toString();
	}

	public static String all(Integer len) {
		if (all == null) {
			all = new char[alphas.length + numbers.length];
			System.arraycopy(alphas, 0, all, 0, alphas.length);
			System.arraycopy(numbers, 0, all, alphas.length, numbers.length);
		}
		StringBuffer buf = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			char c = all[random.nextInt(all.length)];
			if (random.nextBoolean()) {
				// 随机决定大小写
				c = Character.toLowerCase(c);
			}
			buf.append(c);
		}
		return buf.toString();
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
