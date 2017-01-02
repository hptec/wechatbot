package cn.cerestech.wechat.bot;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import cn.cerestech.wechat.Encrypts;
import cn.cerestech.wechat.bot.entity.Contact;
import cn.cerestech.wechat.bot.enums.ContactType;
import cn.cerestech.wechat.bot.enums.Sex;
import cn.cerestech.wechat.dataobjects.AddMsg;
import cn.cerestech.wechat.entity.WcContact;
import cn.cerestech.wechat.enums.BotState;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.http.response.BatchGetContactResponse;
import cn.cerestech.wechat.http.response.BatchGetContactResponse.BatchGetContact;
import cn.cerestech.wechat.http.response.CheckMsgResponse;
import cn.cerestech.wechat.http.response.GetContactResponse;
import cn.cerestech.wechat.http.response.LoginInitResponse;
import cn.cerestech.wechat.http.response.NewMsgResponse;
import cn.cerestech.wechat.http.response.PassTicketResponse;
import cn.cerestech.wechat.http.response.QrCodeResponse;
import cn.cerestech.wechat.http.response.ScanListenResponse;
import cn.cerestech.wechat.http.response.SendTextMsgResponse;
import cn.cerestech.wechat.http.response.SendVideoMsgResponse;
import cn.cerestech.wechat.http.response.UploadMediaResponse;
import cn.cerestech.wechat.http.response.UuidResponse;
import cn.cerestech.wechat.json.Jsons;
import cn.cerestech.wechat.msg.Msg;
import cn.cerestech.wechat.msg.MsgFactory;
import cn.cerestech.wechat.util.ByteArraySplitter;
import cn.cerestech.wechat.util.StringArraySplitter;

public abstract class Bot extends HttpClientSSLSession implements Runnable {

	private BotContext context = new BotContext();

	private Map<String, Contact> contacts = Maps.newHashMap();

	// 机器人运行状态
	private BotState state = BotState.STOP;

	// 需要外部实现的方法
	/**
	 * 得到名称
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * 得到介绍文字
	 * 
	 * @return
	 */
	public abstract String getIntro();

	public abstract void onQrCode(byte[] bytes, BotContext context);

	/**
	 * 用户微信扫码事件（已扫码但未登录）
	 * 
	 * @param avatarImageData
	 *            获取ImageData类型的头像图片
	 */
	public abstract void onScaned(String avatarImageData, BotContext context);

	/**
	 * 用户确认登录，完成初始化事件
	 * 
	 * @param ticket
	 * @param context
	 */
	public abstract void onLoginInitialized(LoginInitResponse initResp);

	/**
	 * 处理消息接收事件
	 * 
	 * @param msg
	 * @param resp
	 * @param context
	 */
	public abstract void onMsg(AddMsg msg, NewMsgResponse resp, BotContext context);

	/**
	 * 开始运行微信机器人并返回二维码图片流用于扫码
	 * 
	 * @return
	 */
	public void run() {

		// 循环每次对话信息，每次均为一个新的机器人
		while (true) {
			// 清空之前的会话信息,开始一个新的会话.
			clear();

			// 获取UUID
			get(new UuidResponse(context));

			// 获取二维码
			QrCodeResponse qrResponse = get(new QrCodeResponse(context));
			onQrCode(qrResponse.getOriginal(), context);
			// 监听登录事件
			listenQrScan();

		}

		// 获取会话UUID

	}

	/**
	 * 开始监听二维码扫码事件
	 */
	@SuppressWarnings("static-access")
	private void listenQrScan() {
		while (true) {
			ScanListenResponse scanListenResp = get(new ScanListenResponse(context));
			switch (scanListenResp.getCode()) {
			case 408:
				// 监听请求超时，继续等待
				break;
			case 201:
				// 用户已经扫码
				onScaned(context.getAvatar(), context);
				break;
			case 200:
				// 扫码登录成功

				// 换取登录令牌
				get(new PassTicketResponse(context));

				// 初始化微信
				LoginInitResponse initResponse = post(new LoginInitResponse(context));

				// 读取通讯录列表
				readContact();
				// 更新Chat列表里面的联系人清单
				batchReadContact();

				onLoginInitialized(initResponse);

				// 循环监听消息接收
				while (true) {
					CheckMsgResponse checkResp = get(new CheckMsgResponse(context));
					switch (checkResp.getCode()) {
					case 0:
						// 返回正常
						switch (checkResp.getSelector()) {
						case 0:
							// 没有新的消息
							break;
						case 2:
							// 有新的消息
							NewMsgResponse msgResp = post(new NewMsgResponse(context));
							msgResp.getOriginal().getAddMsgList().forEach(msg -> {
								onMsg(msg, msgResp, context);
							});
						case 7:
							// 进入聊天界面
							break;
						}
						break;
					case 1100:
					case 1101:
						// 失败或要求重新登录
						return;
					default:
						// 显示其他未能识别的代码
						System.out.println(checkResp.getText());
					}

					// 休息一秒
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			case 400:
				logger.trace("会话超时,重启线程");
				return;
			default:
				logger.trace(Jsons.from(scanListenResp).toPrettyJson());
			}
		}
	}

	/**
	 * 发送文本（表情）消息
	 * 
	 * @param text
	 */
	protected SendTextMsgResponse sendText(String toWhom, String text) {
		Msg msg = MsgFactory.on(context).to(toWhom).text(text).build();
		return post(new SendTextMsgResponse(context, msg));
	}

	protected SendVideoMsgResponse sendVideo(String toWhom, File file) {
		UploadMediaResponse mediaResponse = uploadMedia(toWhom, file);
		String mediaId = mediaResponse.getOriginal().getMediaId();
		if (!Strings.isNullOrEmpty(mediaId)) {
			Msg msg = MsgFactory.on(context).to(toWhom).video(mediaId).build();
			return post(new SendVideoMsgResponse(context, msg));
		} else {
			return null;
		}

	}

	// 更新联系人列表
	private void readContact() {
		GetContactResponse getContact = get(new GetContactResponse(context));
		getContact.getOriginal().getMemberList().forEach(c -> {
			Contact contact = transform(c);
			contacts.put(c.getUserName(), contact);
		});
	}

	private void batchReadContact() {
		List<String> batchNames = context.getChatSet().stream().//
				filter(u -> u != null).// 不为空
				filter(u -> u.startsWith("@")).// 内置公众号不提取
				filter(u -> !contacts.containsKey(u)).// 已经获取了的不获取
				collect(Collectors.toList());

		String[][] batchs = StringArraySplitter.on(50).split(batchNames.toArray(new String[batchNames.size()]));

		for (String[] batch : batchs) {
			BatchGetContact contact = post(new BatchGetContactResponse(context, batch)).getOriginal();
			contact.getContactList().forEach(c -> {
				Contact con = transform(c);
				contacts.put(con.getUserName(), con);
			});
		}
	}

	private Contact transform(WcContact c) {
		Contact contact = new Contact();
		contact.setAlias(c.getAlias());
		contact.setNickName(c.getNickName());
		contact.setSignature(c.getSignature());
		contact.setUserName(c.getUserName());
		contact.setVerifyFlag(c.getVerifyFlag());
		contact.setOriginal(c);
		// 判断类型
		if (c.getVerifyFlag() == null) {
			// no verify flag is member
			contact.setType(ContactType.PERSON);
		} else if ((c.getVerifyFlag() & 8) != 0) {
			// 公众号
			contact.setType(ContactType.MP);
		} else if (c.getUserName().startsWith("@@")) {
			// 群聊
			contact.setType(ContactType.GROUP);
		} else {
			contact.setType(ContactType.PERSON);
		}
		// 判断性别
		if (c.getSex() == null || c.getSex() == 0) {
			contact.setSex(Sex.UNKNOWN);
		} else if (c.getSex() == 1) {
			contact.setSex(Sex.MALE);
		} else {
			contact.setSex(Sex.FEMALE);
		}
		// 是否有成员
		List<Contact> members = Lists.newArrayList();
		if (c.getMemberList() != null) {
			c.getMemberList().stream().filter(cm -> cm != null).forEach(cm -> {//
				Contact contactMember = transform(cm);
				members.add(contactMember);
			});
		}
		contact.setMembers(members);
		return contact;
	}

	@SuppressWarnings("deprecation")
	protected UploadMediaResponse uploadMedia(String toWhom, File file) {

		String filename = Files.getNameWithoutExtension(file.getAbsolutePath());
		String ext = Files.getFileExtension(file.getAbsolutePath());
		if (!Strings.isNullOrEmpty(ext)) {
			filename = filename + "." + ext;
		}

		byte[] bytes = new byte[0];
		try {
			bytes = Files.toByteArray(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		int size = bytes.length;
		String md5 = Encrypts.md5(bytes);

		Map<String, String> textPart = Maps.newHashMap();

		textPart.put("id", "WU_FILE_0");
		textPart.put("name", filename);
		textPart.put("type", "video/mp4");
		textPart.put("lastModifiedDate", new Date(file.lastModified()).toGMTString());
		textPart.put("size", bytes.length + "");

		textPart.put("mediatype", "video");
		textPart.put("uploadmediarequest", new Supplier<String>() {

			@Override
			public String get() {
				Long ClientMediaId = System.currentTimeMillis();

				Map<String, Object> request = Maps.newHashMap();
				request.put("UploadType", 2);
				request.put("BaseRequest", context.initBaseRequest());
				request.put("ClientMediaId", ClientMediaId);
				request.put("TotalLen", size);
				request.put("StartPos", 0);
				request.put("DataLen", size);
				request.put("MediaType", 4);
				request.put("FromUserName", context.getCurrentUser().getUserName());
				request.put("ToUserName", toWhom);
				request.put("FileMd5", md5);
				return Jsons.from(request).toJson();
			}
		}.get());
		String webwxDataTicket = cookieStore.get("webwx_data_ticket").getValue();
		textPart.put("webwx_data_ticket", webwxDataTicket);
		textPart.put("pass_ticket", context.getPassTicket());

		// 分拆成多个部分上传
		UploadMediaResponse response = null;
		int WX_UPLOAD_BLOCK_SIZE = 524288;
		byte[][] buf = ByteArraySplitter.on(WX_UPLOAD_BLOCK_SIZE).split(bytes);
		for (int i = 0; i < buf.length; i++) {
			byte[] part = buf[i];
			textPart.put("chunks", buf.length + "");
			textPart.put("chunk", i + "");
			logger.trace("开始上传文件[" + filename + "]：总 " + buf.length + " 第 " + i + " Size " + part.length);
			response = uploadMediaPart(new UploadMediaResponse(context, textPart, filename, part));
		}

		return response;
	}
	//
	// protected SendMsgResposne sendVideoMsg(String toWhom, String mediaId) {
	// Msg videoMsg = MsgFactory.on(context).to(toWhom).video(mediaId).build();
	// return sendMsg(WeChatUrl.sendVideoMsg(context.getLang(),
	// context.getPassTicket()), videoMsg);
	// }
	//
	// private SendMsgResposne sendMsg(String url, Msg msg) {
	// CloseableHttpResponse response = postJson(url, null, msg.toJson());
	// logger.trace("Msg Send: " + msg.toJson());
	// SendMsgResposne msgResp =
	// Jsons.from(toString(response)).to(SendMsgResposne.class);
	// logger.trace("Msg Response:" + msgResp.toJson());
	// return msgResp;
	// }

	protected Contact getContact(String username) {
		return contacts.get(username);
	}

	/**
	 * 设置机器人当前的运行状态
	 * 
	 * @param state
	 * @return
	 */
	protected Bot state(BotState state) {
		this.state = state;
		return this;
	}

	/**
	 * 返回机器人当前的运行状态
	 */
	protected BotState state() {
		return state;
	}

}
