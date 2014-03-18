 /**  
 *@Description:     
 */ 
package com.wanzhu.jsonvo;  
  
public class RecommendFriendVO {
	private String userId;
    private String username;
    private String portrait;//头像
    private String summary;//一句话介绍
    private String company;//在职公司
    private String workPosition;//在职职位
    private String reason;//推荐原因
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getWorkPosition() {
		return workPosition;
	}
	public void setWorkPosition(String workPosition) {
		this.workPosition = workPosition;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "RecommendFriendVO [userId=" + userId + ", username=" + username
				+ ", portrait=" + portrait + ", reason=" + reason + "]";
	}
	
	
    
}
