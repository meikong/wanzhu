 /**  
 *@Description:     
 */ 
package com.wanzhu.dao;  

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.RecommendFriends;
import com.wanzhu.utils.StringUtils;
  
@Repository
public class RecommendFriendDao extends BaseDao<RecommendFriends> {
	
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param isable 0--还不是好友  1--已经成为好友
	 * @return
	 * @throws Exception
	 * @Date:2013-5-27  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public Page<RecommendFriends> queryRecommendFriendsPage(int pageNo, int pageSize, String userid, String isable) throws Exception {
		Criteria cri = this.createCriteria();
		if(userid!=null){
			cri.add(Restrictions.eq("userid", userid));
		}
		if(isable!=null){
			Integer sta=Integer.parseInt(isable);
			cri.add(Restrictions.eq("isable", sta));
		}
		Page<RecommendFriends> result = this.pagedQuery(cri, pageNo, pageSize, Order.desc("hit"));
		return result;
	}
	
	/**
	 * 置为 可用/不可用
	 * @param userid
	 * @param friendid
	 * @param kind 置为 0-可用 1-不可用
	 * @Date:2013-5-28  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public void turnAble(String userid, String friendid, int kind){
		this.getJdbcTemplate().update("update t_recommend_friends set isable=" + kind + " where (userid='" + userid + "' and friendid='" + friendid + "') or (userid='" + friendid + "' and friendid='" + userid + "')");
	}
}
