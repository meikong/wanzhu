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
 * FrientLog entity. 
 */
@Entity
@Table(name = "t_friend_log")
public class FriendLog implements java.io.Serializable {
	private static final long serialVersionUID = -7773491351142410848L;
	
	private String logid;
	private User userByFriendid;
	private User userByUserid;
	private String invitation;
	@Temporal(TemporalType.TIMESTAMP)
	private Date invitetime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date replytime;
	private Integer accept;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "logid", unique = true, nullable = false, length = 32)
	public String getLogid() {
		return this.logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
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

	@Column(name = "invitation")
	public String getInvitation() {
		return this.invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}

	@Column(name = "invitetime", nullable = false, length = 29)
	public Date getInvitetime() {
		return this.invitetime;
	}

	public void setInvitetime(Date invitetime) {
		this.invitetime = invitetime;
	}

	@Column(name = "replytime", nullable = false, length = 29)
	public Date getReplytime() {
		return this.replytime;
	}

	public void setReplytime(Date replytime) {
		this.replytime = replytime;
	}

	@Column(name = "accept", nullable = false)
	public Integer getAccept() {
		return this.accept;
	}

	public void setAccept(Integer accept) {
		this.accept = accept;
	}

}