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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * Friends entity. 
 */
@Entity
@Table(name = "t_friends", uniqueConstraints = @UniqueConstraint(columnNames = {
		"userid", "friendid" }))
public class Friends implements java.io.Serializable {
	private static final long serialVersionUID = -6740039501096657090L;
	
	private String assignid;
	private User userByFriendid;
	private User userByUserid;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "assignid", unique = true, nullable = false, length = 32)
	public String getAssignid() {
		return this.assignid;
	}

	public void setAssignid(String assignid) {
		this.assignid = assignid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "friendid")
	public User getUserByFriendid() {
		return this.userByFriendid;
	}

	public void setUserByFriendid(User userByFriendid) {
		this.userByFriendid = userByFriendid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUserByUserid() {
		return this.userByUserid;
	}

	public void setUserByUserid(User userByUserid) {
		this.userByUserid = userByUserid;
	}

	@Column(name = "createtime", nullable = false, length = 29)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}