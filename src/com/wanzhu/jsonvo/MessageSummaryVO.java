package com.wanzhu.jsonvo;

import com.wanzhu.utils.DateUtil;

public class MessageSummaryVO {
	private String userid;
	private String username;
	private String portrait;
	private String messageid;
	private String createtime;
	private String content;
	private String count;
	private String unreadcount;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	public String getCreatetime() {
		return DateUtil.getShowDatetimeFormat(createtime);
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getUnreadcount() {
		return unreadcount;
	}
	public void setUnreadcount(String unreadcount) {
		this.unreadcount = unreadcount;
	}
}
