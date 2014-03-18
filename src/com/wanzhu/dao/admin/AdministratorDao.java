package com.wanzhu.dao.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.Page;
import com.wanzhu.entity.admin.Administrator;
/**
 * 后台管理平台 管理员操作
 * @author Lambo
 *
 */
@Repository
public class AdministratorDao extends BaseDao<Administrator>{
	
	/**
	 * 查询管理员列表
	 * @param pageNo 
	 * @param pageSize
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public Page<Administrator> listAdministrator(int pageNo, int pageSize,Integer role) throws Exception
	{
		Criteria cri = this.createCriteria();
		if(role!=null)
		cri.add(Restrictions.eq("role", role));
		return this.pagedQuery(cri, pageNo, pageSize, Order.desc("administrator"));
	}
	
	/**
	 * 查询管理员
	 * @param administratorid
	 * @return
	 */
	public Administrator queryAdministrator(String administratorid)
	{
		if(administratorid==null)
			return null;
		
		return this.get(administratorid);
	}
	
	/**
	 * 添加修改管理员
	 * @param Administrator
	 */
	public void saveOrUpdateAdministrator(Administrator administrator)
	{
		if(administrator.getAdministratorid()!=null){
			Map<String,String> cmap = new HashMap<String,String>();
			cmap.put("administratorid", administrator.getAdministratorid());
			Map<String,String> map = new HashMap<String,String>();
			map.put("password", administrator.getPassword());
			map.put("administrator", administrator.getAdministrator());
			map.put("role", administrator.getRole()+"");
			this.update(map,cmap);
		}else
			this.save(administrator);
	}
	
	public void upPassword(Administrator administrator)
	{
		if(administrator.getAdministratorid()!=null){
			Map<String,String> cmap = new HashMap<String,String>();
			cmap.put("administratorid", administrator.getAdministratorid());
			Map<String,String> map = new HashMap<String,String>();
			map.put("password", administrator.getPassword());
			this.update(map,cmap);
		}
	}
	
	
	/**
	 * 删除管理员
	 * @param administratorId
	 */
	public void deleteAdministrator(String[] administratorId)
	{
		for (String string : administratorId) {
			Administrator admin=this.queryAdministrator(string);
			if(admin!=null)
			this.remove(admin);
		}
	}
	
	public int findByAdministrator(String administrator)
	{
		String hql="from Administrator where administrator=?";
		List<Administrator> list=this.find(hql, new Object[]{administrator});
		return list.size();
	}
}
