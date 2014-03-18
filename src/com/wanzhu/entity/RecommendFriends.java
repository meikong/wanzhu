 /**  
 *@Description:     
 */ 
package com.wanzhu.entity;  

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
  
@Entity
@Table(name = "t_recommend_friends")
public class RecommendFriends {
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "recommendid", unique = true, nullable = false, length = 32)
	private String recommendid;
	@Column(name = "userid", length = 32)
	private String userid;
	@Column(name = "friendid", length = 32)
	private String friendid;
	@Column(name = "reason", length = 128)
	private String reason;
	@Column(name = "hit")
	private Integer hit;
	@Column(name = "isable")
	private Integer isable; /*0-有效 1-无效*/
	
	
	
	public RecommendFriends() {
	}
	
	
	
	public RecommendFriends(String userid, String friendid, String reason) {
		this.recommendid = null;
		this.userid = userid;
		this.friendid = friendid;
		this.reason = reason;
		this.hit = 1;
		this.isable = 0;
	}



	public RecommendFriends(String recommendid, String userid, String friendid,
			String reason, Integer hit, Integer isable) {
		super();
		this.recommendid = recommendid;
		this.userid = userid;
		this.friendid = friendid;
		this.reason = reason;
		this.hit = hit;
		this.isable = isable;
	}


	public String getRecommendid() {
		return recommendid;
	}
	public void setRecommendid(String recommendid) {
		this.recommendid = recommendid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public Integer getIsable() {
		return isable;
	}
	public void setIsable(Integer isable) {
		this.isable = isable;
	}
	
}
