package cn.cerestech.wechat.http.response;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.http.Headers;

public class QrCodeResponse extends BotResponse<byte[]> {

	public QrCodeResponse(BotContext context) {
		super(context);
		setRequest(new BotRequest(context, () -> "https://login.weixin.qq.com/qrcode/" + context.getUuid()) {
			@Override
			public Headers getExternalHeaders() {
				return new Headers().accept("image/webp,image/*,*/*;q=0.8");
			}

		});
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		// 保留原始信息
		setHttpResponse(response);
		try {
			setOriginal(EntityUtils.toByteArray(response.getEntity()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
