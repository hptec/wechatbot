package cn.cerestech.wechat.http.response;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;

import cn.cerestech.wechat.bot.BotContext;
import cn.cerestech.wechat.dataobjects.BaseResponse;
import cn.cerestech.wechat.http.HttpClientSSLSession;
import cn.cerestech.wechat.http.response.UploadMediaResponse.UploadMedia;
import cn.cerestech.wechat.json.Jsons;

public class UploadMediaResponse extends BotResponse<UploadMedia> {

	// other informations to upload
	private Map<String, String> textPart;
	private String filename;
	private byte[] bytes;

	public UploadMediaResponse(BotContext context, Map<String, String> textPart, String filename, byte[] bytes) {
		super(context);
		setRequest(
				new BotRequest(context, () -> "https://file.wx2.qq.com/cgi-bin/mmwebwx-bin/webwxuploadmedia?f=json"));
		this.textPart = textPart;
		this.filename = filename;
		this.bytes = bytes;
	}

	@Override
	public void parse(CloseableHttpResponse response) {
		setHttpResponse(response);
		setText(toString(response, HttpClientSSLSession.ENCODING_ISO8859_1, HttpClientSSLSession.ENCODING_UTF8));
		setOriginal(Jsons.from(getText()).to(UploadMedia.class));

	}

	public Map<String, String> getTextPart() {
		return textPart;
	}

	public void setTextPart(Map<String, String> textPart) {
		this.textPart = textPart;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public class UploadMedia {

		private BaseResponse BaseResponse;
		private Integer CDNThumbImgHeight;
		private Integer CDNThumbImgWidth;
		private String MediaId;
		private Long StartPos;

		public BaseResponse getBaseResponse() {
			return BaseResponse;
		}

		public void setBaseResponse(BaseResponse baseResponse) {
			BaseResponse = baseResponse;
		}

		public Integer getCDNThumbImgHeight() {
			return CDNThumbImgHeight;
		}

		public void setCDNThumbImgHeight(Integer cDNThumbImgHeight) {
			CDNThumbImgHeight = cDNThumbImgHeight;
		}

		public Integer getCDNThumbImgWidth() {
			return CDNThumbImgWidth;
		}

		public void setCDNThumbImgWidth(Integer cDNThumbImgWidth) {
			CDNThumbImgWidth = cDNThumbImgWidth;
		}

		public String getMediaId() {
			return MediaId;
		}

		public void setMediaId(String mediaId) {
			MediaId = mediaId;
		}

		public Long getStartPos() {
			return StartPos;
		}

		public void setStartPos(Long startPos) {
			StartPos = startPos;
		}

	}

}
