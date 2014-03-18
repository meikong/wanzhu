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
 * Message entity. 
 */
@Entity
@Table(name = "t_message")
public class Message implements java.io.Serializable {
	private static final long serialVersionUID = -8888962996965525137L;
	
	private String messageid;
	private User userByFriendid;
	private User userByUserid;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	private Integer deletebyuser;
	private Integer deletebyfriend;
	private Integer read;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "messageid", unique = true, nullable = false, length = 32)
	public String getMessageid() {
		return this.messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
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

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "createtime", nullable = false, length = 29)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "deletebyuser", nullable = false)
	public Integer getDeletebyuser() {
		return this.deletebyuser;
	}

	public void setDeletebyuser(Integer deletebyuser) {
		this.deletebyuser = deletebyuser;
	}
	
	@Column(name = "deletebyfriend", nullable = false)
	public Integer getDeletebyfriend() {
		return this.deletebyfriend;
	}

	public void setDeletebyfriend(Integer deletebyfriend) {
		this.deletebyfriend = deletebyfriend;
	}

	@Column(name = "readss", nullable = false)
	public Integer getRead() {
		return this.read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

}