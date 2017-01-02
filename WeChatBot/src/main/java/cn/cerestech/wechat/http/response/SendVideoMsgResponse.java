package cn.cerestech.wechat.http.response;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.msg.Msg;

public class SendVideoMsgResponse extends SendMsgResponse implements PostJson {

	public SendVideoMsgResponse(BotContext context, Msg msg) {
		super(context, msg);
		setRequest(new BotRequest(context,
				() -> "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendvideomsg?fun=async&f=json&lang="
						+ context.getLang() + "&pass_ticket=" + context.getPassTicket()));
	}

}
