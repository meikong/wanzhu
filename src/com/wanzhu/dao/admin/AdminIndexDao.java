package com.wanzhu.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.admin.Administrator;
import com.wanzhu.utils.MD5;

@Repository
public class AdminIndexDao extends BaseDao<Administrator>{

	public Administrator login(Administrator administrator)
	{
		String hql="from Administrator where administrator=?";
		List<Administrator> listAdministrator=this.find(hql, new Object[]{administrator.getAdministrator()});
		if(listAdministrator.size()!=0){
			System.out.println(MD5.convert("root"));
			if(listAdministrator.get(0).getPassword().equals(MD5.convert(administrator.getPassword())))
				return listAdministrator.get(0);
			else
				return null; //密码错误
		}
			
		else
			return null;//帐号不存在
	}
}
