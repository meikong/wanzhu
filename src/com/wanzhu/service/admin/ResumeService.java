package com.wanzhu.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wanzhu.base.Page;
import com.wanzhu.dao.admin.ResumeDao;
import com.wanzhu.entity.admin.Resume;

@Service
@Transactional(readOnly=true)
public class ResumeService {

	@Autowired
	private ResumeDao ResumeDao;

	
	
	public Page<Resume> queryResumePage(int pageNo, int pageSize,String name,String company,String position,String address,String orther) throws Exception {
		
		return ResumeDao.queryResumePage(pageNo, pageSize,  name, company, position, address, orther);
	}
	
	public Resume queryResumeById(Integer id){
		return ResumeDao.queryResumeById(id);
	}
	
	
	@Transactional(readOnly=false)
	public boolean updateResume(Resume Resume,String[] biaoqianList,	String[] jiabinList) {
		boolean bool= ResumeDao.updateResume(Resume);
	
		
			return bool;
		
	}
	
	@Transactional(readOnly=false)
	public boolean saveResume(Resume Resume)
	{
		return ResumeDao.saveResume(Resume);
	}
	@Transactional(readOnly=false)
	public boolean updateResume(Resume Resume)
	{
		return ResumeDao.updateResume(Resume);
	}
	
	/**
	 * 模糊检索：支持拼音简拼
	 */
	public Page<Resume> queryUserResumePages(String name,int pageNo, int pageSize,String start) throws Exception
	{
		return ResumeDao.queryResumePages(name,pageNo, pageSize);
	}
	
}
