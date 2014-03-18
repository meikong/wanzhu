package com.wanzhu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * FriendActivity entity. 
 */
@Entity
@Table(name = "t_friend_activity")
public class FriendActivity implements java.io.Serializable {
	private static final long serialVersionUID = -3516834631546683416L;
	
	private String activityid;
	private User userByUserid;
	private String content;
	private Integer type;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "activityid", unique = true, nullable = false, length = 32)
	public String getActivityid() {
		return this.activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUserByUserid() {
		return this.userByUserid;
	}

	public void setUserByUserid(User userByUserid) {
		this.userByUserid = userByUserid;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "createtime", nullable = false, length = 29)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}