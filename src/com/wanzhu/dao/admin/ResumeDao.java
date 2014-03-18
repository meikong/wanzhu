package com.wanzhu.dao.admin;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wanzhu.base.BaseDao;
import com.wanzhu.base.Page;
import com.wanzhu.entity.admin.Resume;
import com.wanzhu.utils.StringUtils;
@Repository
public class ResumeDao extends BaseDao<Resume>{
    /*
     * 分页查询简历信息
     */
	public Page<Resume> queryResumePage(int pageNo, int pageSize,String name,String company,String position,String address,String orther) throws Exception
	{
		Criteria cri = this.createCriteria();
		if(!StringUtils.isEmpty(name)&&!"".equals(name)) {
			cri.add(Restrictions.eq("name",name));
		}
		if(!StringUtils.isEmpty(company)&&!"".equals(company)) {
			cri.add(Restrictions.like("company",company+"%"));
		}
		if(!StringUtils.isEmpty(address)&&!"".equals(address)) {
			cri.add(Restrictions.like("address",address+"%"));
		}
		if(!StringUtils.isEmpty(position)&&!"".equals(position)) {
			cri.add(Restrictions.eq("position",position));
		}
		if(!StringUtils.isEmpty(address)&&!"".equals(address)) {
			cri.add(Restrictions.like("address",address+"%"));
		}
		if(!StringUtils.isEmpty(orther)&&!"".equals(orther)&&!orther.equals("请输入查询条件")) {
			cri.add(Restrictions.like("resumes","%"+orther+"%"));
		}
		
		
		
		
		return this.pagedQuery(cri, pageNo, pageSize);
	}
	/*
     * 分页查询简历信息
     */
	public Page<Resume> queryResumePages(String name,int pageNo, int pageSize) throws Exception
	{
		String hql="from Resume as ue where ue.event.eventid=?";
		Object[] obj=null;
		hql+=" order by user.name desc";
		return this.pagedQuery(hql, pageNo, pageSize, obj);
	}
	/**
	 * 查询建立详情
	 * @param resumeId
	 * @return
	 */
	public Resume queryResumeById(Integer resumeId)
	{
		return this.get(resumeId);
	}
	/**
	 * 保存简历
	 * @param id
	 * @return
	 */
	public boolean   saveResume(Resume resume){
		try {
			 this.save(resume);
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 保存或更新简历
	 * @param resume
	 * @return
	 */
	public boolean updateResume(Resume resume) {
		try {
				this.update(resume);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
