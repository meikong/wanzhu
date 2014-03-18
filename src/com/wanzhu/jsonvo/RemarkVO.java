package com.wanzhu.jsonvo;

import com.wanzhu.utils.DateUtil;

public class RemarkVO {
	private String remarkid;
	private String userid;
	private String username;
	private String createtime;
	private String content;
	private String portrait;
	//顶
	private Integer agreecount;
	//踩
	private Integer disagreecount;	
	public String getRemarkid() {
		return remarkid;
	}
	public void setRemarkid(String remarkid) {
		this.remarkid = remarkid;
	}
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
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = DateUtil.getShowDatetimeFormat(createtime);;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public Integer getAgreecount() {
		return agreecount;
	}
	public void setAgreecount(Integer agreecount) {
		this.agreecount = agreecount;
	}
	public Integer getDisagreecount() {
		return disagreecount;
	}
	public void setDisagreecount(Integer disagreecount) {
		this.disagreecount = disagreecount;
	}
}
