package com.wanzhu.dao.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.BaseSQLDao;
import com.wanzhu.base.Page;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.FriendVO;
import com.wanzhu.utils.StringUtils;
/**
 * xu add
 * @author xuguangyun
 *
 */
@Repository
public class AdminUserDao extends BaseDao<User>{

	public Page<User> ListUserPage(int pageNo, int pageSize,String content,Integer au,Integer ac) throws Exception {
		Criteria cri = this.createCriteria();
		if(!"".equals(content)&&!StringUtils.isEmpty(content))
		{
			cri.add(Restrictions.or(Restrictions.like("name", "%"+content+"%"), Restrictions.like("email", "%"+content+"%")));
		}
		if(au!=null&&au!=2)
		{
			cri.add(Restrictions.eq("state", au));
		}
		if(ac!=null&&ac!=2)
		{
			cri.add(Restrictions.eq("activated", ac));
		}
		
    	return this.pagedQuery(cri, pageNo, pageSize, Order.desc("createtime"));
	}
	
	/**
	 * 根据名字或简拼检索用户数据
	 */
	public List<FriendVO> queryUserByNamespy(String name,Integer start,String eventId){
	    
	    StringBuffer sql = new StringBuffer("select u.userid as userId ,u.name as username from t_user as u,t_user_event as e where  u.userid=e.userid and e.eventid =?  and  (u.name like ? or u.namespy like ?) ");
	    List<Object> args = new ArrayList<Object>();
	    args.add(eventId);
	    args.add(name.trim()+"%");
	    args.add(name.trim()+"%");
	    String temp = "";
	    if(null != start)
	    {
	        temp=" and ";
	         temp+= start==1 || start==2 ?" e.signup="+Math.abs(start-2):" e.audit="+Math.abs(start-4);
	    }
	    sql.append(temp).append(" group by u.userid ");
	    
	    List<String> fieldList = new ArrayList<String>();
	    fieldList.add("userId");
	    fieldList.add("username");
	    
	    return new BaseSQLDao<FriendVO>().list(sql.toString(), args.toArray(), -1, -1, FriendVO.class, fieldList);
	}
	
	public User queryUser(Integer userId){
		return this.get(userId);
	}

	public void auditUser(Integer userId,Integer audit){
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("state", audit+"");
			Map<String,String> wmap=new HashMap<String,String>();
			wmap.put("userid", userId.toString());
			this.update(map, wmap);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void disableUser(Integer userId,Integer disable){
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("activated", disable+"");
			Map<String,String> wmap=new HashMap<String,String>();
			wmap.put("userid", userId.toString());
			this.update(map, wmap);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
