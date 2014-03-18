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
 * Notification entity. 
 */
@Entity
@Table(name = "t_notification")
public class Notification implements java.io.Serializable {
	private static final long serialVersionUID = 730728083704461980L;
	
	private String notificationid;
	private User user;
	private User inviter;
	private String content;
	private Integer type;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	private Integer read;
	private Integer replied;
	private Topic topic;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "notificationid", unique = true, nullable = false, length = 32)
	public String getNotificationid() {
		return this.notificationid;
	}

	public void setNotificationid(String notificationid) {
		this.notificationid = notificationid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "typess", nullable = false)
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

	@Column(name = "readss", nullable = false)
	public Integer getRead() {
		return this.read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	@Column(name = "replied")
	public Integer getReplied() {
		return this.replied;
	}

	public void setReplied(Integer replied) {
		this.replied = replied;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter")
    public User getInviter()
    {
        return inviter;
    }

    public void setInviter(User inviter)
    {
        this.inviter = inviter;
    }

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topicid")
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
    
    

}