
package com.wanzhu.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.Page;
import com.wanzhu.entity.User;

@Repository
public class UserDao extends BaseDao<User> {	
	
	public Page<User> listByPage(int pageNo, int pageSize) throws Exception {
		Criteria cri = this.createCriteria();
    	return this.pagedQuery(cri, pageNo, pageSize, Order.desc("createtime"));
	}
	
	public void updateUserPassword(){
		
		
	}
	
	/**
	 * 获取全部处于激活状态的会员
	 */
	public List<User> queryAllActivatedUsers()
	{
	    String hql = "FROM User AS o where o.activated=?";
	    return find(hql, new Object[]{1});
	}
	
	/**
	 * 更新某人参加的活动总数
	 * @param userId
	 */
	public void updateSignedEventscount(String userEventid) {
		String hql = "select ue.user.userid from UserEvent ue where ue.assignid=:userEventid";
		List list = this.getSession().createQuery(hql).setString("userEventid", userEventid).list();
		String userid = (String)(list.get(0));
		hql = "update User set eventscount=(eventscount+1) where userid=:userId ";
		this.getSession().createQuery(hql).setString("userId", userid).executeUpdate();
	}
	
	/**
	 * 增加好友个数
	 * @param userId
	 * @param count
	 */
	public int addFriendsCount(String userId,int count)
	{
	    String hql="update User set friendscount=friendscount+"+count+" where userid='"+userId+"'";
	    return this.execute(hql);
	}
	
	/**
	 * 减少好友个数
	 * @param userId
	 * @param count
	 * @return
	 */
	public int deleteFriendsCount(String userId,int count)
	{
	    String hql="update User set friendscount=friendscount-"+count+" where userid='"+userId+"' and friendscount>0";
        return this.execute(hql);
	}

	public boolean isLimitedUser(String userId) {
		String hql = "select state from User where userid=:userid ";
		Integer state = (Integer) this.getSession().createQuery(hql).setString("userid", userId).list().get(0);
		return state == 0 ? true : false;
	}
}