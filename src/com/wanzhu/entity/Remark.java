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
 * Remark entity. 
 */
@Entity
@Table(name = "t_remark")
public class Remark implements java.io.Serializable {
	private static final long serialVersionUID = 4032739565642997874L;
	
	private String remarkid;
	private Topic topic;
	private User user;
	private String parentremarkid;
	private Integer agreecount;
	private Integer disagreecount;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	private String content;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "remarkid", unique = true, nullable = false, length = 32)
	public String getRemarkid() {
		return this.remarkid;
	}

	public void setRemarkid(String remarkid) {
		this.remarkid = remarkid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topicid")
	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "parentremarkid", length = 32)
	public String getParentremarkid() {
		return this.parentremarkid;
	}

	public void setParentremarkid(String parentremarkid) {
		this.parentremarkid = parentremarkid;
	}

	@Column(name = "createtime", nullable = false, length = 29)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "agreecount", nullable = false)
	public Integer getAgreecount() {
		return this.agreecount;
	}

	public void setAgreecount(Integer agreecount) {
		this.agreecount = agreecount;
	}

	@Column(name = "disagreecount", nullable = false)
	public Integer getDisagreecount() {
		return this.disagreecount;
	}

	public void setDisagreecount(Integer disagreecount) {
		this.disagreecount = disagreecount;
	}

}