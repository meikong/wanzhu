package com.wanzhu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * OfflineUser entity. 
 */
@Entity
@Table(name = "t_offline_user")
public class OfflineUser implements java.io.Serializable {
	private static final long serialVersionUID = 5376450636677278988L;
	
	private String offlineuserid;
	private String name;
	private String email;
	private String company;
	private String position;
	private String phone;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "offlineuserid", unique = true, nullable = false, length = 32)
	public String getOfflineuserid() {
		return this.offlineuserid;
	}

	public void setOfflineuserid(String offlineuserid) {
		this.offlineuserid = offlineuserid;
	}

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", nullable = false, length = 128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "company", nullable = false, length = 64)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "position", nullable = false, length = 64)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "phone", nullable = false, length = 128)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}