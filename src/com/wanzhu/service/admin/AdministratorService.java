package com.wanzhu.service.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.Page;
import com.wanzhu.dao.admin.AdministratorDao;
import com.wanzhu.entity.admin.Administrator;
import com.wanzhu.utils.MD5;

@Service
@Transactional(readOnly=true)
public class AdministratorService {

	@Autowired
	private AdministratorDao administratorDao;
	
	public Page<Administrator> listAdministrator(int pageNo, int pageSize,Integer role) throws Exception
	{
		return administratorDao.listAdministrator(pageNo, pageSize, role);
	}
	
	public Administrator queryAdministrator(String administratorid)
	{
		return administratorDao.queryAdministrator(administratorid);
	}
	
	@Transactional(readOnly=false)
	public int saveOrUpdateAdministrator(Administrator administrator,String SessionId)
	{
		if(administrator.getAdministratorid()==null||administrator.getAdministratorid()==""){
			administrator.setAdministratorid(null);
			administrator.setPassword(administrator.getPassword().trim());
			administrator.setPassword(MD5.convert(administrator.getPassword())); 
			int adminnum=administratorDao.findByAdministrator(administrator.getAdministrator().toLowerCase());
			if(adminnum!=0){
			return 2;
			}
		}else
		{
			Administrator admin=administratorDao.queryAdministrator(administrator.getAdministratorid());
			if(!admin.getAdministrator().equals(administrator.getAdministrator()))
			{
				int adminnum=administratorDao.findByAdministrator(administrator.getAdministrator());
				if(adminnum!=0){
					return 2;
				}
			}
				if(administrator.getPassword()==null||administrator.getPassword()=="")
					//修改 如密码为空 则重置原来的密码
					administrator.setPassword(admin.getPassword());
				else{
					//如果密码不为空 则重设新密码
					administrator.setPassword(administrator.getPassword().trim());
					administrator.setPassword(MD5.convert(administrator.getPassword())); 
				}
				 	
		}
		administratorDao.saveOrUpdateAdministrator(administrator);
		return 0;
	}
	
	
	@Transactional(readOnly=false)
	public int upPassword(String administratorid,String password ,String ypassword,String qpassowrd)
	{
		Administrator administrator=administratorDao.queryAdministrator(administratorid);
		if(administrator.getPassword().equals(MD5.convert(password))){
			if(ypassword.equals(qpassowrd)){
				ypassword=ypassword.trim();
				administrator.setPassword(MD5.convert(ypassword));
				administratorDao.upPassword(administrator);
				return 0;
			}else
			{
				return 1;
			}
		}else{
			return 2;
		}
	}
	@Transactional(readOnly=false)
	public void deleteAdministrator(String[] administratorId)
	{
			administratorDao.deleteAdministrator(administratorId);
	}
}
