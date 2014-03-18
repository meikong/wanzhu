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
 * EducationExperience entity. 
 */
@Entity
@Table(name = "t_educationexperience")
public class EducationExperience implements java.io.Serializable {
	private static final long serialVersionUID = 2591871014507631433L;
	
	private String educationexperienceid;
	private School school;
	private User user;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "educationexperienceid", unique = true, nullable = false, length = 32)
	public String getEducationexperienceid() {
		return this.educationexperienceid;
	}

	public void setEducationexperienceid(String educationexperienceid) {
		this.educationexperienceid = educationexperienceid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolid")
	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
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