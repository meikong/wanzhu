package com.wanzhu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Code entity. 
 */
@Entity
@Table(name = "t_code")
public class Code implements java.io.Serializable {

	private static final long serialVersionUID = 2251101174357062130L;

	private String codeId;
	private String code;
	private String value;
	private Integer type;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "codeid", unique = true, nullable = false, length = 32)
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	
	@Column(name = "code", nullable = false, length = 64)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "value", nullable = false, length = 64)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Code))
			return false;
		Code castOther = (Code) other;

		return ((this.getCode() == castOther.getCode()) || (this.getCode() != null
				&& castOther.getCode() != null && this.getCode().equals(
				castOther.getCode())))
				&& ((this.getValue() == castOther.getValue()) || (this
						.getValue() != null && castOther.getValue() != null && this
						.getValue().equals(castOther.getValue())))
				&& ((this.getType() == castOther.getType()) || (this.getType() != null
						&& castOther.getType() != null && this.getType()
						.equals(castOther.getType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCode() == null ? 0 : this.getCode().hashCode());
		result = 37 * result
				+ (getValue() == null ? 0 : this.getValue().hashCode());
		result = 37 * result
				+ (getType() == null ? 0 : this.getType().hashCode());
		return result;
	}

}