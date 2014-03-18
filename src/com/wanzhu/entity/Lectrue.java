package com.wanzhu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Lectrue entity. 
 */
@Entity
@Table(name = "t_lecture")
public class Lectrue implements java.io.Serializable {
	private static final long serialVersionUID = 7838646375194237972L;
	
	private String lectureid;
	private Event event;
	private User user;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "lectureid", unique = true, nullable = false, length = 32)
	public String getLectureid() {
		return this.lectureid;
	}

	public void setLectureid(String lectureid) {
		this.lectureid = lectureid;
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

}