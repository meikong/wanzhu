package com.wanzhu.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * Topic entity. 
 */
@Entity
@Table(name = "t_topic")
public class Topic implements java.io.Serializable {
	private static final long serialVersionUID = 7331678013525318823L;
	
	private String topicid;
	private Event event;
	private User user;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	private String content;
	private Integer agreecount;
	private Integer disagreecount;
	private Integer remarkcount;
	private Set<DeclareLog> declareLogs = new HashSet<DeclareLog>(0);
	private Set<Remark> remarks = new HashSet<Remark>(0);
	
	
	public Topic() {
	}
	
	
	public Topic(String topicid) {
		this.topicid = topicid;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "topicid", unique = true, nullable = false, length = 32)
	public String getTopicid() {
		return this.topicid;
	}

	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventid")
	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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

	@Column(name = "remarkcount", nullable = false)
	public Integer getRemarkcount() {
		return this.remarkcount;
	}

	public void setRemarkcount(Integer remarkcount) {
		this.remarkcount = remarkcount;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topic")
	public Set<DeclareLog> getDeclareLogs() {
		return this.declareLogs;
	}

	public void setDeclareLogs(Set<DeclareLog> declareLogs) {
		this.declareLogs = declareLogs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topic")
	public Set<Remark> getRemarks() {
		return this.remarks;
	}

	public void setRemarks(Set<Remark> remarks) {
		this.remarks = remarks;
	}

}