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
import com.wanzhu.entity.PersonalActivity;
import com.wanzhu.entity.Topic;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.ActivityVO;
import com.wanzhu.utils.StringUtils;

/**
 * 
 * @author ZOUSY
 */
@Repository
public class PersonalActivityDao extends BaseDao<PersonalActivity> {

    @Value("${activityofMine.type.agree-topic}")
    private String agreeTopic;
    @Value("${activityofMine.type.event}")
    private String event;
    @Value("${activityofMine.type.agreeAddFriend}")
    private String agreeAddFriend;
     @Value("${activityofMine.type.participate-topic}")
     private String participateTopic;
    @Value("${activityofMine.type.pubilishied-topic}")
    private String pubilishiedTopic;
    @Value("${activityofMine.length}")
    private int activityofMineLength;
    
    /**
     * 添加一条个人动态
     * 
     * @param userId
     *                      目标用户
     * @param contentTxt
     *                      动态内容
     * @param type
     *                      个人动态类型
     * @param targetId
     *                      某个活动/用户的id，依type而异。注意话题类型，targetId传活动id！
     */
    public void addPersonActivity(String userId,String contentTxt,int type,String targetId, String topicId)
    {
        PersonalActivity personalActivity = new PersonalActivity();
        User user = new User();
        user.setUserid(userId);
        personalActivity.setUser(user);
        personalActivity.setCreatetime(new Date());
        personalActivity.setType(type);
        String content = "";
        if(contentTxt.length() > activityofMineLength)
            contentTxt = contentTxt.substring(0, activityofMineLength)+"...";
        
        Object[] args = new Object[]{targetId,contentTxt,CommonConstant.CONTEXT_PATH.endsWith("/")?CommonConstant.CONTEXT_PATH.substring(0, CommonConstant.CONTEXT_PATH.length()-1):CommonConstant.CONTEXT_PATH};
        
      //赞同了话题[?]
        if(type==CommonConstant.PERSONALACTIVITY_AGREE_TOPIC_TYPE)
        {
        	personalActivity.setTopic(new Topic(topicId));
            content = StringUtils.getText(agreeTopic, args);
        }
        //报名了[活动?]
        else if(type==CommonConstant.PERSONALACTIVITY_EVENT_TYPE)
        {
            content = StringUtils.getText(event, args);
        }
        //和?成为好友
        else  if(type==CommonConstant.PERSONALACTIVITY_FRIENDRELATIONSHIP_TYPE)
        {
            content = StringUtils.getText(agreeAddFriend, args);
        }
        //参与了话题[?]
        else  if(type==CommonConstant.PERSONALACTIVITY_PARTICIPATE_TOPIC_TYPE)
        {
            content = StringUtils.getText(participateTopic, args);
        }
        //发表了话题[?]
        else if(type==CommonConstant.PERSONALACTIVITY_PUBlLISHIED_TOPIC_TYPE)
        {
            content = StringUtils.getText(pubilishiedTopic, args);
        }
        
        personalActivity.setContent(content);
        
        save(personalActivity);
    }
    
    /**
     * 滚动加载  个人动态
     */
    public List<ActivityVO> queryPersonActivitys(User user,int start,int size)
    {
        String sql = "SELECT activity.activityid AS activityId,activity.content AS content,activity.createtime AS time  FROM t_personal_activity AS activity WHERE activity.userid=? ORDER BY activity.createtime DESC LIMIT ?,? ";
        List<String> fieldList = new ArrayList<String>();
        fieldList.add("activityId");
        fieldList.add("content");
        fieldList.add("time");
        return new BaseSQLDao<ActivityVO>().list(sql, new Object[] {user.getUserid(),start, size}, -1,-1, ActivityVO.class, fieldList);
    }
    
    public Page<ActivityVO> pagedPersionActivityVos(User user,int start,int size)
    {
        StringBuffer sql = new StringBuffer("SELECT count(0)  FROM t_personal_activity AS activity WHERE activity.userid='").append(user.getUserid()).append("'");
        long totalCount = countVos(sql.toString());
        if (totalCount < 1)
            return new Page<ActivityVO>();
        return new Page<ActivityVO>(start, totalCount, size, queryPersonActivitys( user, start, size));
    }
    
    @SuppressWarnings("rawtypes")
    public long countVos(String sql)
    {
        List list = getSession().createSQLQuery(sql).list();
        return null == list?0:Long.parseLong(list.get(0).toString() );
    }
    
    public void deletepersonalActivityByTopicAndUser(String topicId, String userId) {
    	String hql = "delete from PersonalActivity a where a.user.userid = ? and a.topic.topicid = ?";
    	this.getSession().createQuery(hql).setString(0, userId).setString(1, topicId).executeUpdate();
    }
}
