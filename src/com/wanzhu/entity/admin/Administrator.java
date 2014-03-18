package com.wanzhu.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Administrator entity. 
 */
@Entity
@Table(name = "t_administrator")
public class Administrator implements java.io.Serializable {
	private static final long serialVersionUID = -4829040279842604278L;

	private String administratorid;
	private String administrator;
	private String password;
	private Integer role;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "administratorid", unique = true, nullable = false, length = 32)
	public String getAdministratorid() {
		return this.administratorid;
	}

	public void setAdministratorid(String administratorid) {
		this.administratorid = administratorid;
	}

	@Column(name = "administrator", nullable = false, length = 64)
	public String getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	@Column(name = "password", nullable = false, length = 128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "role", nullable = false)
	public Integer getRole() {
		return this.role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}