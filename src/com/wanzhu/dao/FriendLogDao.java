
package com.wanzhu.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.FriendLog;
import com.wanzhu.entity.User;
/**
 * 
 * @author ZOUSY
 */
@Repository("friendlog.dao")
public class FriendLogDao extends BaseDao<FriendLog>
{
    /**
     * 根据当前用户id和好友id查询一条加好友的记录
     * @param userId
     * @param friendId
     * @return
     */
    public FriendLog queryFriendLogByUser(String userId,String friendId)
    {
        String hql = "FROM FriendLog AS o WHERE (o.userByUserid.userid=? AND o.userByFriendid.userid=?) OR  (o.userByFriendid.userid=? AND o.userByUserid.userid=?) ORDER BY invitetime DESC";
        List<FriendLog> list = find(hql, new Object[]{userId,friendId,userId,friendId});
        if(null != list && list.size()>0)
            return list.get(0);
        return null;
    }
    
    public void updateFriendLog(User user,String friendId)
    {
            FriendLog friendLog = queryFriendLogByUser(friendId,user.getUserid());
            friendLog.setReplytime(new Date());
            friendLog.setAccept(1);
            update(friendLog);
    }
}
