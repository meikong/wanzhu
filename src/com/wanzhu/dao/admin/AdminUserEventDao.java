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
import com.wanzhu.entity.UserEvent;

@Repository
public class AdminUserEventDao extends BaseDao<UserEvent> {

	//1=签到 0=审核 


	public Page<UserEvent> queryUserEventPage(String id,int pageNo, int pageSize,Integer start) throws Exception
	{
		Criteria cri = this.createCriteria();
		cri.add(Restrictions.eq("event.eventid", id));
		if(start!=null)
		{
			if(start==1)
				cri.add(Restrictions.eq("signup", 1));
			else if(start==2)
				cri.add(Restrictions.eq("signup", 0));
			else if(start==3)
				cri.add(Restrictions.eq("audit", 1));
			else 
				cri.add(Restrictions.eq("audit", 0));
		}
		return this.pagedQuery(cri, pageNo, pageSize, Order.desc("assignid"));
	}
	
	public Page<UserEvent> queryUserEventPages(String name,String id,int pageNo, int pageSize,Integer start) throws Exception
	{
		String hql="from UserEvent as ue where ue.event.eventid=?";
		int state=0;
		Object[] obj=null;
		if(start!=null&&!"".equals(start)){
				if(start==1){
					state=1;
					hql+=" and ue.signup=? ";
				}else if(start==2){
					state=0;
					hql+=" and ue.signup=? ";
				}else if(start==3){
					state=1;
					hql+=" and ue.audit=? ";
				}else {
					state=0;
					hql+=" and ue.audit=? ";
				}
				obj=new Object[]{id,state};
		}else
		{
			obj=new Object[]{id};
		}
		
		if(name!=null&&start!=null&&!"".equals(start)){
			hql+=" and ue.user.name like ?";
			obj=new Object[]{id,state,"%"+name+"%"};
		}else if(start!=null&&!"".equals(start))
		{
			obj=new Object[]{id,state};
		}else if(name!=null)
		{
			hql+=" and (ue.user.name like ? or ue.user.namespy like ?)";
			obj=new Object[]{id,"%"+name+"%","%"+name+"%"};
		}else{
			obj=new Object[]{id};
		}
		hql+=" order by user.name desc";
		return this.pagedQuery(hql, pageNo, pageSize, obj);
	}
	
	public boolean upUserEvent(String UserEventId,String signup,String audit)
	{
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(signup!=null&&!"".equals(signup))
				map.put("signup", signup);
			if(audit!=null&&!"".equals(audit))
				map.put("audit", audit);
			Map<String,String> wheremap = new HashMap<String,String>();
			wheremap.put("assignid", UserEventId);
			this.update(map,wheremap);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean queryAudit(String id){
		try {
			String hql="from UserEvent where assignid=?";
			List<UserEvent> list=this.find(hql,new Object[]{id});
			if(list.get(0).getAudit()==0)
				return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}
	
	public UserEvent queryAudit2(String id){
		try {
			String hql="from UserEvent where assignid=?";
			return this.find(hql,new Object[]{id}).get(0);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
