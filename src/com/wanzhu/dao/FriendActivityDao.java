package com.wanzhu.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.BaseSQLDao;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.entity.FriendActivity;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.ActivityVO;
import com.wanzhu.utils.StringUtils;

/**
 * 
 * @author ZOUSY
 */
@Repository
public class FriendActivityDao extends BaseDao<FriendActivity> {
  
    @Value("${activityofFriend.type.event}")
    private String event;
    @Value("${activityofFriend.type.participate-topic}")
    private String participateTopic;
    @Value("${activityofFriend.type.pubilished-topic}")
    private String pubilishedTopic;
    @Value("${activityofFriend.length}")
    private int activityofFriendLength;
    
    /**
     * 添加一条好友动态
     * 
     * @param userId
     *                      目标用户
     * @param contentTxt
     *                      动态内容
     * @param type
     *                      动态类型
     * @param targetId
     *                      某个活动/用户的id，依type而异。注意话题类型，targetId传活动id！
     */
    public void addFriendActivity(String userId,String contentTxt,int type,String targetId)
    {
        FriendActivity friendActivity = new FriendActivity();
        User user = new User();
        user.setUserid(userId);
        friendActivity.setUserByUserid(user);
        friendActivity.setCreatetime(new Date());
        friendActivity.setType(type);
        
        String content = "";
        
        if(contentTxt.length()>activityofFriendLength)
            contentTxt = contentTxt.substring(0, activityofFriendLength)+"...";
                
        Object[] args = new Object[]{targetId,contentTxt,CommonConstant.CONTEXT_PATH.endsWith("/")?CommonConstant.CONTEXT_PATH.substring(0, CommonConstant.CONTEXT_PATH.length()-1):CommonConstant.CONTEXT_PATH};
        
        //报名参加了[活动?]
        if(type==CommonConstant.FRIENDACTIVITY_EVENT_TYPE)
        {
            content = StringUtils.getText(event, args);
        }
        //参与了话题[?]
        else if(type==CommonConstant.FRIENDACTIVITY_PARTICIPATE_TOPIC_TYPE)
        {
            content = StringUtils.getText(participateTopic, args);
        }
        //发表了话题[?]
        else if(type==CommonConstant.FRIENDACTIVITY_PUBlLISHIED_TOPIC_TYPE)
        {
            content = StringUtils.getText(pubilishedTopic, args);
        }
        friendActivity.setContent(content);
        save(friendActivity);
    }
    
    
    /**
     * 滚动加载  好友动态
     */
    public List<ActivityVO> queryFriendActivitys(User user,int start,int size)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.userid as userId,u.name as username,u.portrait as portrait,activityid,act.content as content,act.createtime as time from t_user as u,( ");
        sql.append("select a.userid, a.content, a.createtime,a.activityid from t_friend_activity a, t_friends b  where b.userid=a.userid and b.friendid=? or b.userid=? and b.friendid=a.userid order by a.createtime desc limit ?,?) as act  ");
        sql.append("where u.userid = act.userid order by act.createtime desc");
        
        List<String> fieldList = new ArrayList<String>();
        fieldList.add("userId");
        fieldList.add("username");
        fieldList.add("portrait");
        fieldList.add("activityId");
        fieldList.add("content");
        fieldList.add("time");
        
        return new BaseSQLDao<ActivityVO>().list(sql.toString(), new Object[] {user.getUserid(),user.getUserid(),start,size}, -1, -1, ActivityVO.class, fieldList);
    }
    
    
    public Page<ActivityVO> pagedFriendActivityVos(User user,int start,int size)
    {
        StringBuffer sql = new StringBuffer("select count(0) from t_friend_activity a, t_friends b  where (b.userid=a.userid and b.friendid='");
        sql.append(user.getUserid()).append("'").append(") or (b.userid='").append(user.getUserid()).append("' and b.friendid=a.userid)");
        long totalCount = countVos(sql.toString());
        if (totalCount < 1)
            return new Page<ActivityVO>();
        return new Page<ActivityVO>(start, totalCount, size, queryFriendActivitys( user, start, size));
    }
    
    @SuppressWarnings("rawtypes")
    public long countVos(String sql)
    {
        List list = getSession().createSQLQuery(sql).list();
        return null == list?0:Long.parseLong(list.get(0).toString() );
    }
}
