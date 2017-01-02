package cn.cerestech.wechat.msg;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.BaseRequest;
import cn.cerestech.wechat.dataobjects.Random;
import cn.cerestech.wechat.enums.MsgType;

public class MsgFactory {

	private BotContext botContext;
	private MsgType type = null;
	private String toWhom;
	private String content;
	private String mediaId;

	private MsgFactory(BotContext botContext) {
		this.botContext = botContext;
	}

	public static MsgFactory on(BotContext botContext) {
		return new MsgFactory(botContext);
	}

	public MsgFactory to(String toWhom) {
		this.toWhom = toWhom;
		return this;
	}

	public MsgFactory text(String content) {
		this.type = MsgType.TEXT;
		this.content = content;
		return this;
	}

	public MsgFactory video(String mediaId) {
		this.type = MsgType.VIDEO;
		this.mediaId = mediaId;
		return this;
	}

	public Msg build() {
		BaseRequest br = botContext.initBaseRequest();
		Msg msg = new Msg();
		msg.put("BaseRequest", br);
		Map<String, Object> m = Maps.newHashMap();
		String id = System.currentTimeMillis() + Random.number(4);
		m.put("ClientMsgId", id);
		m.put("LocalID", id);
		m.put("FromUserName", botContext.getCurrentUser().getUserName());
		if (Strings.isNullOrEmpty(toWhom)) {
			// 必须有发送者
			throw new IllegalArgumentException("ToUserName is required");
		} else {
			m.put("ToUserName", toWhom);
		}

		if (type == null) {
			throw new IllegalArgumentException("MsgType is required");
		}
		switch (type) {
		case TEXT:
			m.put("Type", MsgType.TEXT.typeCode());
			if (Strings.isNullOrEmpty(content)) {
				throw new IllegalArgumentException("Content is required");
			}
			m.put("Content", content);
			break;
		case VIDEO:
			m.put("Type", MsgType.VIDEO.typeCode());
			if (Strings.isNullOrEmpty(mediaId)) {
				throw new IllegalArgumentException("MediaId is MediaId");
			}
			m.put("MediaId", mediaId);
			break;
		default:
			throw new RuntimeException("This type is not implemented yet");
		}
		msg.put("Msg", m);
		msg.put("Scene", 0);

		return msg;
	}

}
