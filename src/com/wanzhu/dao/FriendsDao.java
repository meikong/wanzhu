package com.wanzhu.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.BaseSQLDao;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Friends;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.FriendVO;

/**
 * 好友
 * @author ZOUSY
 */
@Repository("friends.dao")
public class FriendsDao extends  BaseDao<Friends>
{

    @SuppressWarnings("rawtypes")
    public Page<FriendVO> pageFriendsVo(User user,String condition,int pageNo,int pageSize)
    {
        List<FriendVO> list = queryFrindsVo(user, condition, pageNo, pageSize);
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        int totalCount = 0;
        StringBuffer sql = new StringBuffer("select count(*) from  t_user as u left join t_friends as f on u.userid=f.userid  where (f.userid=? or f.friendid=?)  and u.name like ? ");
        Query query =  this.getSession().createSQLQuery(sql.toString());
        query.setString(0, user.getUserid());
        query.setString(1, user.getUserid());
        if(null == condition)
            condition = "";
        query.setString(2, "%"+condition.trim()+"%");
        List counts = query.list();
        if(null != counts && counts.size()>0)
            totalCount = Integer.parseInt(counts.get(0).toString());
        return new Page<FriendVO>(startIndex, totalCount, pageSize, list);
    }
    
    /**
     * 统计某用户的好友个数
     * @param user
     *                      目标用户
     * @return     好友个数
     */
    public long friendsCount(User user)
    {
        String hql = "FROM Friends AS o WHERE o.userByUserid=? or o.userByFriendid=?";
        return count(hql, new Object[]{user,user});
    }
    
    
    
    /**
     * 解除好友关系
     * @param userId
     *                      当前用户
     * @param friendId
     *                      好友
     */
    public int deleteFriend(String userId,String friendId)
    {
        Map<String, String> conditions = new HashMap<String, String>(0);
        conditions.put("userByUserid.userid", userId);
        conditions.put("userByFriendid.userid", friendId);
        String hql = "DELETE FROM Friends  AS o WHERE (o.userByUserid.userid=? AND o.userByFriendid.userid=?) OR (o.userByFriendid.userid=? AND o.userByUserid.userid=?)";
        Query query = createQuery(hql, new Object[]{userId,friendId,userId,friendId});
       return  query.executeUpdate();
    }
    
    /**
     * 查询我的好友信息
     * 
     * @param user
     * @param pageNo
     * @param pageSize
     * @return
     */
    public  List<FriendVO> queryFrindsVo(User user,String condition,int pageNo,int pageSize)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.userid as userId, u.name as username, u.portrait as portrait,c.company as company,w.position as workPosition from  t_user as u  left join t_friends as f on (u.userid=f.friendid or u.userid=f.userid) "); 
        sql.append("left join t_workexperience as w on (u.userid=w.userid and w.current='1') left join t_company as c on c.companyid=w.companyid ");
        sql.append("where (f.userid=? or f.friendid=?) and u.userid !=?  ");
        
        List<String> fieldList = new ArrayList<String>();
        fieldList.add("userId");
        fieldList.add("username");
        fieldList.add("portrait");
        fieldList.add("company");
        fieldList.add("workPosition");
        
        List<Object> args = new ArrayList<Object>();
        args.add(user.getUserid());
        args.add(user.getUserid());
        args.add(user.getUserid());
        if(null != condition && !"".equals(condition.trim()))
        {
            sql.append(" and u.name like ?  ");
            args.add("%"+condition.trim()+"%");
        }
        sql.append(" order by username desc limit ?,?");
        args.add((pageNo-1)*pageSize); //起始数
        args.add(pageSize); //页大小
        return new BaseSQLDao<FriendVO>().list(sql.toString(), args.toArray(), -1, -1, FriendVO.class, fieldList);
    }
    /**
     * 判断是否为好友关系
     * @param userId
     * @param friendId
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean isFriend(String userId,String friendId)
    {
        String hql = "FROM Friends AS o WHERE (o.userByUserid.userid=? AND o.userByFriendid.userid=?) OR (o.userByFriendid.userid=? AND o.userByUserid.userid=?)";
        Query query = createQuery(hql, new Object[]{userId,friendId,userId,friendId});
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<Friends> list = query.list();
        return null != list && list.size()>0?true:false;
    }
    
    public FriendVO aboutUser(String friendId)
    {
        String sql = "select u.userid as userId,u.name as username,u.portrait as portrait ,u.summary as summary ,u.friendscount as friendsCount,u.eventscount as eventsCount,c.company as company,w.position as workPosition  from t_user u left join t_workexperience as w on (u.userid=w.userid and w.current='1') left join t_company as c on c.companyid=w.companyid where u.userid = ?";
        
        List<String> fieldList = new ArrayList<String>();
        fieldList.add("userId");
        fieldList.add("username");
        fieldList.add("summary");
        fieldList.add("portrait");
        fieldList.add("friendsCount");
        fieldList.add("eventsCount");
        fieldList.add("company");
        fieldList.add("workPosition");
        
        List<FriendVO> vos = new BaseSQLDao<FriendVO>().list(sql.toString(), new Object[]{friendId}, 0, 1, FriendVO.class, fieldList);
        
        return null == vos || vos.size()==0?new FriendVO():vos.get(0);
    }
}
