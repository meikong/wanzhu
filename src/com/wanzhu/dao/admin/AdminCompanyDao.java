package com.wanzhu.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Company;
import com.wanzhu.entity.WorkExperience;

@Repository
public class AdminCompanyDao extends BaseDao<Company> {

	public Company listCompany(String id)
	{
		String hql="from Company where companyid=?";
		return this.find(hql,new Object[]{id}).get(0);
	}
}
