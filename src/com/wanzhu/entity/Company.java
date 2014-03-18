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
 * Company entity. 
 */
@Entity
@Table(name = "t_company")
public class Company implements java.io.Serializable {
	private static final long serialVersionUID = 7660102013010775600L;
	
	private String companyid;
	private String company;
	private String logo;
	
	private Set<WorkExperience> workExperiences = new HashSet<WorkExperience>(0);

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "companyid", unique = true, nullable = false, length = 32)
	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	@Column(name = "company", nullable = false, length = 64)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "logo", length = 256)
	public String getLogo() {
		return this.logo;
		/*if(StringUtils.isEmpty(this.logo)){
			return CommonConstant.Default_COMPANY_LOGO;
		}else{
			
		}		*/
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	public Set<WorkExperience> getWorkExperiences() {
		return this.workExperiences;
	}

	public void setWorkExperiences(Set<WorkExperience> workExperiences) {
		this.workExperiences = workExperiences;
	}

}