package cn.cerestech.wechat.dataobjects;

public class UploadMediaResponse {

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
