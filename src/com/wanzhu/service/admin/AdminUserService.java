package com.wanzhu.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.Page;
import com.wanzhu.dao.admin.AdminCompanyDao;
import com.wanzhu.dao.admin.AdminUserDao;
import com.wanzhu.entity.Company;
import com.wanzhu.entity.User;
import com.wanzhu.entity.WorkExperience;
import com.wanzhu.jsonvo.FriendVO;
import com.wanzhu.utils.StringUtils;

@Service
@Transactional(readOnly=true)
public class AdminUserService {
	
	@Autowired
	private AdminUserDao adminUserDao;
	@Autowired
	private AdminCompanyDao adminCompanyDao;
	
	public Page<User> ListUserPage(int pageNo, int pageSize,String content,Integer au,Integer ac) throws Exception {
		return adminUserDao.ListUserPage(pageNo, pageSize, content,au,ac);
	}
	
	public Company listCompany(String id)
	{
		return adminCompanyDao.listCompany(id);
	}
	
	public User queryUser(Integer userId){
		return adminUserDao.queryUser(userId);
	}
	
	@Transactional(readOnly=false)
	public void auditUser(Integer userId,Integer audit){
		adminUserDao.auditUser(userId, audit);
	}
	
	@Transactional(readOnly=false)
	public void disableUser(Integer userId,Integer disable){
		adminUserDao.disableUser(userId, disable);
	}
	
	   /**
     * 根据名字或简拼检索用户数据
     */
    public List<FriendVO> queryUserByNamespy(String name,Integer start,String eventId){
        if(StringUtils.isEmpty(name))
            return new ArrayList<FriendVO>();
        return adminUserDao.queryUserByNamespy(name,start,eventId);
    }
}
