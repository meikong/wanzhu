package com.wanzhu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.base.SystemBuffer;
import com.wanzhu.dao.FriendLogDao;
import com.wanzhu.dao.FriendsDao;
import com.wanzhu.dao.NotificationDao;
import com.wanzhu.dao.PersonalActivityDao;
import com.wanzhu.dao.RecommendFriendDao;
import com.wanzhu.dao.UserDao;
import com.wanzhu.entity.FriendLog;
import com.wanzhu.entity.Friends;
import com.wanzhu.entity.User;
import com.wanzhu.entity.WorkExperience;
import com.wanzhu.jsonvo.FriendVO;
import com.wanzhu.utils.StringUtils;

/**
 * 
 * @author ZOUSY
 */
@Service("friends.service")
@Transactional(readOnly=true)
public class FriendsService
{

    @Autowired
    private FriendsDao friendsDao;
    @Autowired
    private FriendLogDao friendLogDao;
    @Autowired
    private PersonalActivityDao personalActivityDao;
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RecommendFriendDao recommendFriendDao;
    
    public Page<FriendVO> queryFriendsByName(User user,String condition,int pageNo,int pageSize)
    {
        Page<FriendVO> friends = friendsDao.pageFriendsVo(user, condition, pageNo, pageSize);
        if(null == friends)
            return friends;
        for(FriendVO vo : friends.getResult())
        {
            vo.setFriendsCount(friendsCount(new User(vo.getUserId())));
        }
       return friends;
    }
    
    public Page<FriendVO> queryFrindsVo(User user,int pageNo,int pageSize)
    {
        return friendsDao.pageFriendsVo(user, null,pageNo, pageSize);
    }
    
    /**
     * 某用户的好友个数
     * @param user
     * @return
     */
    public long friendsCount(User user)
    {
        if(null == user)
            return 0;
        //从缓存中拿好友数
        Long friendCount = SystemBuffer.friendsCount.get(user.getUserid());
        if(null == friendCount)
        {
            friendCount = friendsDao.friendsCount(user);
            SystemBuffer.friendsCount.put(user.getUserid(), friendCount);
        }
        return friendCount;
    }
    
    /**
     * 添加好友请求
     * @param friendLog
     *                      请求日志
     * @param notification
     *                      发送通知
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addFriend(User user,FriendLog friendLog)
    {
                friendLog.setAccept(0);
                friendLog.setInvitetime(new Date());
                friendLog.setReplytime(new Date());
                friendLog.setUserByUserid(user);
                friendLogDao.save(friendLog);
                
                //加好友通知
                notificationDao.addNotification(3, friendLog.getUserByFriendid().getUserid(), user.getUserid(), null, new String[]{user.getUserid(),user.getName()});
    }
    
    /**
     * 批量添加好友
     * 
     * @param user
     *                      目标用户
     * @param userEvents
     *                      参加了活动的用户集
     */
    @SuppressWarnings("rawtypes")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addFriends(User user,List<Map> userEvents)
    {
        FriendLog friendLog  = null;
        for(Map userEventMap : userEvents)
        {
            String friendId = userEventMap.get("userid").toString();
            //自己不能加自己
            if(user.getUserid().equals(friendId))
                continue;
            //已是好友,不重复加
            if(friendsDao.isFriend(user.getUserid(), friendId))
                continue;
            friendLog = new FriendLog();
            friendLog.setUserByUserid(user);
            friendLog.setUserByFriendid(userDao.get(friendId));
            friendLog.setInvitation(user.getName()+"请求添加你为好友。");
            friendLog.setInvitetime(new Date());
            friendLog.setAccept(0);
            friendLog.setReplytime(new Date());
            friendLogDao.save(friendLog);
            //加好友通知
            notificationDao.addNotification(3, friendLog.getUserByFriendid().getUserid(),user.getUserid(), null, new String[]{user.getUserid(),user.getName()});
        }
    }
    
    /**
     *  建立好友关系:两人正式成为好友
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void agreeToAdd(User user,User userByFriendId, String notificationId)
    {
        //已是好友,不重复加
        if(friendsDao.isFriend(user.getUserid(), userByFriendId.getUserid()))
            return;
        // 更新  加好友日志表
        friendLogDao.updateFriendLog(user, userByFriendId.getUserid());
        
        //建立好友关系
        Friends friends = new Friends();
        friends.setUserByUserid(user);
        friends.setUserByFriendid(userByFriendId);
        friends.setCreatetime(new Date());
        friendsDao.save(friends);
        
      //移除 SystemBuffer.friendsCount
       SystemBuffer.friendsCount.remove(user.getUserid());
        
        //好友双方的friendsCount加1
        userDao.addFriendsCount(user.getUserid(), 1);
        userDao.addFriendsCount(userByFriendId.getUserid(), 1);
        
        //在推荐列表里删除推荐项 1-不可用 0-可用
        recommendFriendDao.turnAble(user.getUserid(), userByFriendId.getUserid(), 1);
        
    	//个人动态
        personalActivityDao.addPersonActivity(user.getUserid(), userByFriendId.getName(), CommonConstant.PERSONALACTIVITY_FRIENDRELATIONSHIP_TYPE, userByFriendId.getUserid(), null);
        
        //同意加好友通知
        notificationDao.addNotification(4, userByFriendId.getUserid(),null, null, new String[]{user.getUserid(),user.getName()});
    	 
        // 修改通知
        notificationDao.updateNotificationReplied(user.getUserid(), userByFriendId.getUserid());
        
        
    }
    
    /**
     *   删除好友
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteFriend(User user,String friendId)
    {
        friendsDao.deleteFriend(user.getUserid(), friendId);
        
        //好友双方的friendsCount减1
        userDao.deleteFriendsCount(user.getUserid(), 1);
        userDao.deleteFriendsCount(friendId,1);
        
        //在推荐列表里删除推荐项（如果存在的话） 1-不可用 0-可用
        recommendFriendDao.turnAble(user.getUserid(),friendId, 0);
        
        //移除 SystemBuffer.friendsCount
        SystemBuffer.friendsCount.remove(user.getUserid());
    }
    
    /**
     * 拒绝加好友请求
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void disagreeToAdd(User user,String friendId, String notificationId)
    {
        // 1、更新加好友日志表
        FriendLog friendLog = friendLogDao.queryFriendLogByUser(friendId,user.getUserid());
        friendLog.setAccept(0);
        friendLog.setReplytime(new Date());
        friendLogDao.update(friendLog);
        // 2、产生通知，被拒绝
        notificationDao.addNotification(5, friendId, null, null, new String[]{user.getUserid(),user.getName()});
        // 3、修改通知
        notificationDao.updateNotificationReplied(user.getUserid(), friendId);
    }
    
    /**
     * 判断是否为好友关系
     */
    public boolean isFriend(String userId,String friendId)
    {
        return friendsDao.isFriend(userId, friendId); 
    }
    
    public FriendVO abountUser(String currentUserId,String friendId)
    {
        FriendVO friendVO = friendsDao.aboutUser(friendId);
        if(!StringUtils.isEmpty(currentUserId))
            friendVO.setIsFriend(friendsDao.isFriend(currentUserId, friendId));
        return friendVO;
    }
}
