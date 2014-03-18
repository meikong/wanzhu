package com.wanzhu.jsonvo;

import com.wanzhu.utils.DateUtil;

public class TopicVO {
	private String topicid;
	private String userid;
	private String portrait;
	private String username;
	private String createtime;
	private String content;
	private String agreecount;
	private String disagreecount;
	private String remarkcount;
	private String eventid;
	
	public String getTopicid() {
		return topicid;
	}
	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPortrait() {
		if(portrait==null){
			portrait="";
		}
		else{
			portrait=portrait.trim();
		}
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
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
		this.createtime = DateUtil.getShowDatetimeFormat(createtime);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAgreecount() {
		return agreecount;
	}
	public void setAgreecount(String agreecount) {
		this.agreecount = agreecount;
	}
	public String getDisagreecount() {
		return disagreecount;
	}
	public void setDisagreecount(String disagreecount) {
		this.disagreecount = disagreecount;
	}
	public String getRemarkcount() {
		return remarkcount;
	}
	public void setRemarkcount(String remarkcount) {
		this.remarkcount = remarkcount;
	}
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	
}
