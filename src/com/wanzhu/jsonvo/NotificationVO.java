package com.wanzhu.jsonvo;

import com.wanzhu.utils.DateUtil;

public class NotificationVO {

	private String notificationid;
	private String content;
	private String type;
	private String createtime;
	private String inviter;
	private String replied;
	private String read;
	
	public String getNotificationid() {
		return notificationid;
	}
	public void setNotificationid(String notificationid) {
		this.notificationid = notificationid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreatetime() {
		return DateUtil.getShowDatetimeFormat(createtime);
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getReplied() {
		return replied;
	}
	public void setReplied(String replied) {
		this.replied = replied;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getInviter() {
		return inviter;
	}
	public void setInviter(String inviter) {
		this.inviter = inviter;
	}
}
