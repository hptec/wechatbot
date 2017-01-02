package cn.cerestech.wechat.bot;

import java.util.Map;

import com.google.common.collect.Maps;

public class BotManager {

	private static Map<String, Bot> botPool = Maps.newHashMap();

	public static Bot add(Bot bot) {
		if (bot == null) {
			return bot;
		}
		String name = bot.getName();
		if (botPool.containsKey(name)) {
			throw new IllegalArgumentException("Bot [" + name + "] already exists in BotManager");
		}
		botPool.put(name, bot);
		return bot;
	}

	public static void start() {
		botPool.values().forEach(bot -> {
			bot.run();
		});
	}
}
