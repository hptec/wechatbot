package cn.cerestech.wechat.dataobjects;

import java.util.List;

/**
 * 订阅消息
 * 
 * @author harryhe
 *
 */
public class SubscribeMsg {
	private Integer MPArticleCount;
	private String NickName;
	private Long Time;
	private String UserName;
	private List<MpArticle> MPArticleList;

	public Integer getMPArticleCount() {
		return MPArticleCount;
	}

	public void setMPArticleCount(Integer mPArticleCount) {
		MPArticleCount = mPArticleCount;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public Long getTime() {
		return Time;
	}

	public void setTime(Long time) {
		Time = time;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public List<MpArticle> getMPArticleList() {
		return MPArticleList;
	}

	public void setMPArticleList(List<MpArticle> mPArticleList) {
		MPArticleList = mPArticleList;
	}

}
