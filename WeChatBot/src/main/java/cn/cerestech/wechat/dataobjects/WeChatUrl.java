package cn.cerestech.wechat.dataobjects;

public class WeChatUrl {
	public static final String APPID = "wx782c26e4c19acffb";


	public static String uploadMedia() {
		return "https://file.wx2.qq.com/cgi-bin/mmwebwx-bin/webwxuploadmedia?f=json";
	}

	public static String sendVideoMsg(String lang, String passTicket) {
		return "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendvideomsg?fun=async&f=json&lang=" + lang
				+ "&pass_ticket=" + passTicket;
	}

}
