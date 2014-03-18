package com.wanzhu.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.wanzhu.base.CommonConstant;

/**
 * School entity. 
 */
@Entity
@Table(name = "t_school")
public class School implements java.io.Serializable {
	private static final long serialVersionUID = 52743398626330792L;
	
	private String schoolid;
	private String school;
	private String logo;
	private Set<EducationExperience> educationExperiences = new HashSet<EducationExperience>(
			0);

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "schoolid", unique = true, nullable = false, length = 32)
	public String getSchoolid() {
		return this.schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	@Column(name = "school", nullable = false, length = 64)
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "logo", length = 256)
	public String getLogo() {
		return this.logo;
		/*if(StringUtils.isEmpty(this.logo)){
			return CommonConstant.Default_SCHOOL_LOGO;
		}else{
			
		}	*/	
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "school")
	public Set<EducationExperience> getEducationExperiences() {
		return this.educationExperiences;
	}

	public void setEducationExperiences(
			Set<EducationExperience> educationExperiences) {
		this.educationExperiences = educationExperiences;
	}

}