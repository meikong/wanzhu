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
 * WorkExperience entity. 
 */
@Entity
@Table(name = "t_workexperience")
public class WorkExperience implements java.io.Serializable {
	private static final long serialVersionUID = 3909874742080514252L;
	
	private String workexperienceid;
	private Company company;
	private Integer current;
	private User user;
	private String position;	

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "workexperienceid", unique = true, nullable = false, length = 32)
	public String getWorkexperienceid() {
		return this.workexperienceid;
	}

	public void setWorkexperienceid(String workexperienceid) {
		this.workexperienceid = workexperienceid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyid")
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "position", nullable = false, length = 64)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Column(name = "current", nullable = false)
	public Integer getCurrent() {
		return this.current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

}