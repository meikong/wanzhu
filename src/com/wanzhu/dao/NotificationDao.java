package com.wanzhu.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.entity.Notification;
import com.wanzhu.entity.Topic;
import com.wanzhu.entity.User;
import com.wanzhu.utils.StringUtils;

/**
 * 通知
 * @author zhanglei
 *
 */
@Repository
public class NotificationDao extends BaseDao<Notification> {
	
	@Value("${notification.type.event-pass}")
	private String eventPass;   //1
	@Value("${notification.type.event-nopass}")
	private String eventNoPass; //2
	@Value("${notification.type.add-friend}")
	private String addFriend;  //3
	@Value("${notificaton.type.agree-add-friend}")
	private String agreeAddFriend; //4
	@Value("${notificaton.type.disagree-add-friend}")
	private String disagreeAddFriend; //5
	@Value("${notification.type.reply-topic}")
	private String replyTopic;  //6
	@Value("${notification.type.agree-topic}")
	private String agreeTopic; //7
	
	/**
	 * 查询通知列表
	 * @return
	 */
	public List<Notification> queryNotifications(String userId, int start, int size) {
		String hql = "from Notification n where n.user.userid = ? order by n.createtime desc";
		return this.searchMore(hql, new String[]{userId}, start, size);
	}
	
	/**
	 * 删除全部通知
	 */
	public void deleteAllNotifications(String userId) {
		String hql = "delete from Notification n where n.user.userid = '" + userId + "'";
		this.execute(hql);
	}
	
	/**
	 * 删除通知
	 * @return
	 */
	public void deleteNotification(String notificationid) {
		String hql = "delete from Notification n where n.notificationid = '" + notificationid + "'";
		this.execute(hql);
	}
	
	/**
	 * 设置通知已读
	 */
	public void setNotificationRead(List<String> notificationIds) {
		if(notificationIds == null || notificationIds.size() == 0)
			return;
		StringBuilder sql = new StringBuilder("update t_notification set readss = 1 where notificationid in (");
		for(int i = 0; i < notificationIds.size(); i++) {
			if(i > 0) {
				sql.append(",");
			}
			sql.append("'" + notificationIds.get(i) + "'");
		}
		sql.append(")");
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**
	 * 未读通知条数
	 * @return
	 */
	public int getUnreadNotificationCount(String userId) {
		String hql = "select count(*) from Notification n where n.user.userid = ? and n.read = 0";
		Query query = this.getSession().createQuery(hql).setParameter(0, userId);
		return ((Number)query.iterate().next()).intValue(); 
	}
	
	/**
	 * 添加通知
	 * @param type 1-活动报名通过;2-活动报名不通过;3-添加好友;4-同意加为好友;5-拒绝加为好友;6-回复话题;7-赞了话题;
	 * @param userId
	 * @param params
	 * @return
	 */
	public void addNotification(int type, String userId, String friendId, String topicId, String[] params) {
		Notification notification = new Notification();
		notification.setCreatetime(new Date());
		notification.setUser(new User(userId));
		
		List<Object> list = new ArrayList<Object>();
		Object[]  args = convertArgs(list, type, params).toArray();
		
		if(type == 1) {
			notification.setContent(StringUtils.getText(eventPass, args));
			notification.setType(CommonConstant.NOTIFICATION_EVENT_TYPE);
		}
		if(type == 2) {
			notification.setContent(StringUtils.getText(eventNoPass, args));
			notification.setType(CommonConstant.NOTIFICATION_EVENT_TYPE);
		}
		if(type == 3) {
			notification.setContent(StringUtils.getText(addFriend, args));
			notification.setType(CommonConstant.NOTIFICATION_ADDFRIEND_TYPE);
			notification.setInviter(new User(friendId));
		}
		if(type == 4) {
			notification.setContent(StringUtils.getText(agreeAddFriend, args));
			notification.setType(CommonConstant.NOTIFICATION_DISORAGREE_FRIENDRELATIONSHIP_TYPE);
		}
		if(type == 5) {
			notification.setContent(StringUtils.getText(disagreeAddFriend, args));
			notification.setType(CommonConstant.NOTIFICATION_DISORAGREE_FRIENDRELATIONSHIP_TYPE);
		}
		if(type == 6) {
			notification.setContent(StringUtils.getText(replyTopic, args));
			notification.setType(CommonConstant.NOTIFICATION__REPLY_TOPIC_TYPE);
		}
		if(type == 7) {
			notification.setInviter(new User(friendId));
			notification.setTopic(new Topic(topicId));
			notification.setContent(StringUtils.getText(agreeTopic, args));
			notification.setType(CommonConstant.NOTIFICATION_AGREE_TOPIC_TYPE);
		}
		notification.setRead(0);
		notification.setReplied(0);
		this.save(notification);
	}
	
	private List<Object> convertArgs(List<Object> args,int type,String[] params)
	{
	    if(null != params && params.length>0){
            for(String param : params){
                args.add(param);
            }
        }
	    String path = CommonConstant.CONTEXT_PATH.endsWith("/")?CommonConstant.CONTEXT_PATH.substring(0, CommonConstant.CONTEXT_PATH.length()-1):CommonConstant.CONTEXT_PATH;
	    if(type == 1 || type == 2 || type == 3 || type == 4 || type == 5)
	    {
	        args.add(path);
	    }
	    if(type == 6 || type == 7)
	    {
	        args.add(path);
	        args.add(path);
	    }
	    return args;
	}
	
	/**
	 * 对于多条请求好友的通知，会一并处理掉。
	 * @param userId    接受通知的人
	 * @param inviter   发起好友申请的人
	 * @param replied   是否同意
	 * @return          受影响的条数
	 */
	public int updateNotificationReplied(String userId, String inviter) {
		String hql = "delete from Notification n where n.user.userid = ? and n.inviter.userid = ? and n.type = 2";
		return this.getSession().createQuery(hql).setString(0, userId).setString(1, inviter).executeUpdate();
	}
	
	public void deleteNotificationByTopicAndUser(String topicId, String userId) {
		String hql = "delete from  Notification n where n.topic.topicid = ? and n.inviter.userid = ?";
		this.getSession().createQuery(hql).setString(0, topicId).setString(1, userId).executeUpdate();
	}
	
}
