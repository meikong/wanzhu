package com.wanzhu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * UserEvent entity. 
 */
@Entity
@Table(name = "t_user_event", uniqueConstraints = @UniqueConstraint(columnNames = {
		"eventid", "userid" }))
public class UserEvent implements java.io.Serializable {
	private static final long serialVersionUID = -4362228767483141638L;
	
	private String assignid;
	private Event event;
	private User user;
	private Integer signup;//是否签到 0-否；1-是
	private Integer audit; //是否审核通过 0否; 1-是

	public UserEvent(String userId, String eventId) {
		this.user = new User();
		this.user.setUserid(userId);
		this.event = new Event();
		this.event.setEventid(eventId);
		this.signup = 0;
		this.audit = 0;
	}
	
	public UserEvent() {}
	
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

	@Column(name = "signup", nullable = false)
	public Integer getSignup() {
		return this.signup;
	}

	public void setSignup(Integer signup) {
		this.signup = signup;
	}

	@Column(name = "audit", nullable = false)
	public Integer getAudit() {
		return this.audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

}