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
 * DeclareLog entity. 
 */
@Entity
@Table(name = "t_declare_log", uniqueConstraints = @UniqueConstraint(columnNames = {
		"topicid", "userid" }))
public class DeclareLog implements java.io.Serializable {
	private static final long serialVersionUID = 3077170338357439441L;
	
	private String logid;
	private Topic topic;
	private User user;
	@Temporal(TemporalType.TIMESTAMP)
	private Date declaretime;
	private Integer attitude;
	
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

	@Column(name = "declaretime", nullable = false, length = 29)
	public Date getDeclaretime() {
		return this.declaretime;
	}

	public void setDeclaretime(Date declaretime) {
		this.declaretime = declaretime;
	}

	@Column(name = "attitude", nullable = false)
	public Integer getAttitude() {
		return this.attitude;
	}

	public void setAttitude(Integer attitude) {
		this.attitude = attitude;
	}

}