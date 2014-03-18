package com.wanzhu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.CommonConstant;
import com.wanzhu.dao.DeclareLogDao;
import com.wanzhu.dao.FriendActivityDao;
import com.wanzhu.dao.NotificationDao;
import com.wanzhu.dao.PersonalActivityDao;
import com.wanzhu.dao.RemarkDao;
import com.wanzhu.dao.TopicDao;
import com.wanzhu.entity.DeclareLog;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.Remark;
import com.wanzhu.entity.Topic;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.RemarkVO;
import com.wanzhu.jsonvo.TopicVO;
import com.wanzhu.utils.DateUtil;

/**
 * 讨论
 * @author zhanglei
 *
 */
@Service
@Transactional(readOnly=true)
public class DiscuzService {
	
	@Autowired
	private DeclareLogDao declareLogDao;
	@Autowired
	private TopicDao topicDao;
	@Autowired
	private RemarkDao remarkDao;
	@Autowired
	private PersonalActivityDao personalActivityDao;
	@Autowired
	private FriendActivityDao friendActivityDao;
	@Autowired
	private NotificationDao notificationDao;
	
	/**
	 * 根据活动ID查询话题
	 * @return
	 */
	public List<TopicVO> queryTopics(String eventId, int start, int size) {
		String[][] results = this.topicDao.queryTopics(eventId, start, size);
		List<TopicVO> resultList = new ArrayList<TopicVO>();
		for(int i = 0; i < results.length; i++) {
			TopicVO result = new TopicVO();
			result.setTopicid(results[i][0]);
			result.setUserid(results[i][1]);
			result.setPortrait(results[i][2] == null ? "" : results[i][2]);
			result.setUsername(results[i][3]);
			result.setCreatetime(results[i][4]);
			result.setContent(results[i][5]);
			result.setAgreecount(results[i][6]);
			result.setDisagreecount(results[i][7]);
			result.setRemarkcount(results[i][8]);
			resultList.add(result);
		}
		return resultList;
	}

	/**
	 * @param topic
	 * @return
	 * @Date:2013-5-8  
	 * @Author:xuguangyun  
	 * @Description:根据话题ID查询话题
	 */
	public List<TopicVO> getTopicById(String topic) {
		String[][] results = this.topicDao.getTopicById(topic);
		List<TopicVO> resultList = new ArrayList<TopicVO>();
		for(int i = 0; i < results.length; i++) {
			TopicVO result = new TopicVO();
			result.setTopicid(results[i][0]);
			result.setUserid(results[i][1]);
			result.setPortrait(results[i][2] == null ? "" : results[i][2]);
			result.setUsername(results[i][3]);
			result.setCreatetime(results[i][4]);
			result.setContent(results[i][5]);
			result.setAgreecount(results[i][6]);
			result.setDisagreecount(results[i][7]);
			result.setRemarkcount(results[i][8]);
			result.setEventid(results[i][9] == null ? "" : results[i][9]);
			resultList.add(result);
		}
		return resultList;
	}
	/**
	 * 查询评论
	 * @return
	 */
	public List<RemarkVO> queryRemarks(String topicId, int start, int size) {
		String[][] results = this.remarkDao.queryRemarks(topicId, start, size);
		List<RemarkVO> resultList = new ArrayList<RemarkVO>();
		for(int i = 0; i < results.length; i++) {
			RemarkVO result = new RemarkVO();
			result.setRemarkid(results[i][0]);
			result.setUserid(results[i][1]);
			result.setUsername(results[i][2]);
			result.setCreatetime(results[i][3]);
			result.setContent(results[i][4]);
			result.setPortrait(results[i][5] == null ? "" : results[i][5]);
			result.setAgreecount(Integer.parseInt(results[i][6]));
			result.setDisagreecount(Integer.parseInt(results[i][7]));
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * 赞一个
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public int agree(String topicId, User user) {
		//查询“赞和踩一个日志表”，是否可以赞
		List<DeclareLog> declareLogList =  declareLogDao.getDeclareLogListByTopicAndUser(topicId, user.getUserid());
		if(declareLogList.size() > 0) { //有赞过或踩过
			DeclareLog declareLog = declareLogList.get(0);
			if(declareLog.getAttitude() == 1) { //有赞过
				this.declareLogDao.remove(declareLog);
				//将“话题表”的赞次数减1
				Topic topic = this.topicDao.get(topicId);
				topic.setAgreecount(topic.getAgreecount() - 1);
				this.topicDao.update(topic);
				//删除个人动态表
				this.personalActivityDao.deletepersonalActivityByTopicAndUser(topic.getTopicid(), user.getUserid());
				//删除"通知表"
				this.notificationDao.deleteNotificationByTopicAndUser(topic.getTopicid(), user.getUserid());
				return topic.getAgreecount();
			} else { //有踩过
				throw new RuntimeException("30005");
			}
		} else { //没有赞过或踩过
			//插入“赞和踩一个日志表”
			DeclareLog declareLog = new DeclareLog();
			declareLog.setTopic(new Topic(topicId));
			declareLog.setUser(user);
			declareLog.setDeclaretime(new Date());
			declareLog.setAttitude(1);
			this.declareLogDao.save(declareLog);
			//将“话题表”的赞次数加1
			Topic topic = this.topicDao.get(topicId);
			topic.setAgreecount(topic.getAgreecount() + 1);
			this.topicDao.update(topic);
			//插入个人动态表
			this.personalActivityDao.addPersonActivity(user.getUserid(), topic.getContent(), CommonConstant.PERSONALACTIVITY_AGREE_TOPIC_TYPE, topic.getEvent().getEventid(), topic.getTopicid());
			//插入“通知表”
			this.notificationDao.addNotification(7, topic.getUser().getUserid(), user.getUserid(), topic.getTopicid(), new String[]{user.getUserid(), user.getName(), topic.getEvent().getEventid(), topic.getContent()});
			return topic.getAgreecount(); 
		}
	}
	
	/**
	 * 踩一个
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public int disagree(String topicId, String userId) {
		//查询“赞和踩一个日志表”，是否可以赞
		List<DeclareLog> declareLogList =  declareLogDao.getDeclareLogListByTopicAndUser(topicId, userId);
		
		
		if(declareLogList.size() > 0) { //有赞过或者有踩过
			DeclareLog declareLog = declareLogList.get(0);
			if(declareLog.getAttitude() == 0) { //有踩过
				this.declareLogDao.remove(declareLog);
				//将“话题表”的踩次数减1
				Topic topic = this.topicDao.get(topicId);
				topic.setDisagreecount(topic.getDisagreecount() - 1);
				this.topicDao.update(topic);
				return topic.getDisagreecount();
			} else {
				throw new RuntimeException("30001");
			}
		} else { //没有赞过或踩过
			//插入“赞和踩一个日志表”
			DeclareLog declareLog = new DeclareLog();
			declareLog.setTopic(new Topic(topicId));
			declareLog.setUser(new User(userId));
			declareLog.setDeclaretime(new Date());
			declareLog.setAttitude(0);
			this.declareLogDao.save(declareLog);
			
			//将“话题表”的踩次数加1
			Topic topic = this.topicDao.get(topicId);
			topic.setDisagreecount(topic.getDisagreecount() + 1);
			this.topicDao.update(topic);
			
			return topic.getDisagreecount();
		}
	}
	
	/**
	 * 发表话题、评论话题、回复评论
	 * 1-话题
	 * 2-评论
	 * 3-回复
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Object publish(int operateType, User user, String eventId, String topicId, String remarkId, String content) {
		Topic topic = null;
		Remark remark = null;
		switch(operateType) {
		case 1://发表话题
			topic = new Topic();
			topic.setUser(user);
			topic.setContent(content);
			topic.setCreatetime(new Date());
			topic.setAgreecount(0);
			topic.setDisagreecount(0);
			topic.setRemarkcount(0);
			topic.setEvent(new Event(eventId));
			this.topicDao.save(topic);
			//插入“好友动态表”
			this.friendActivityDao.addFriendActivity(user.getUserid(), content, CommonConstant.FRIENDACTIVITY_PUBlLISHIED_TOPIC_TYPE, eventId);
			//插入个人动态表
			this.personalActivityDao.addPersonActivity(user.getUserid(), content, CommonConstant.PERSONALACTIVITY_PUBlLISHIED_TOPIC_TYPE, eventId, null);
			break;
		case 2://评论话题
			remark = new Remark();
			remark.setUser(user);
			remark.setTopic(new Topic(topicId));
			remark.setParentremarkid(null);
			remark.setCreatetime(new Date());
			remark.setContent(content);
			remark.setAgreecount(0);
			remark.setDisagreecount(0);
			this.remarkDao.save(remark);
			//修改“话题表”的“评论次数”
			topic = this.topicDao.get(topicId);
			topic.setRemarkcount(topic.getRemarkcount() + 1);
			this.topicDao.update(topic);
			//插入“通知表”
			if(!topic.getUser().getUserid().equals(user.getUserid())) {
				this.notificationDao.addNotification(6, topic.getUser().getUserid(), null, null, new String[]{user.getUserid(), user.getName(), topic.getEvent().getEventid(), topic.getContent()});
			}
			//插入“好友动态表”
			this.friendActivityDao.addFriendActivity(user.getUserid(), this.topicDao.get(topicId).getContent(), CommonConstant.FRIENDACTIVITY_PARTICIPATE_TOPIC_TYPE, eventId);
			//插入个人动态表
			this.personalActivityDao.addPersonActivity(user.getUserid(), this.topicDao.get(topicId).getContent(), CommonConstant.PERSONALACTIVITY_PARTICIPATE_TOPIC_TYPE, eventId, null);
			break;
		case 3://回复评论
			remark = new Remark();
			remark.setUser(user);
			remark.setTopic(new Topic(topicId));
			remark.setParentremarkid(remarkId);
			remark.setCreatetime(new Date());
			remark.setContent(content);
			remark.setAgreecount(0);
			remark.setDisagreecount(0);
			this.remarkDao.save(remark);
			
			//修改“话题表”的“评论次数”
			topic = this.topicDao.get(topicId);
			System.out.println("this.topicDao.get(topicId)::::::::::::::::::::::::"+topicId);
			System.out.println("this.topicDao.get(topicId)::::::::::::::::::::::::"+this.topicDao.get(topicId));
			System.out.println("this.topicDao.get(topicId)::::::::::::::::::::::::"+topic.getRemarkcount());
			
			topic.setRemarkcount(topic.getRemarkcount() + 1);
			this.topicDao.update(topic);
			//xu
			if(StringUtils.isBlank(remarkId)){
				remarkId=remark.getRemarkid();
			}
			//xu end
			//插入“通知表”
		
			Topic topics=this.remarkDao.get(remarkId).getTopic();
		   //xu
			User userNew=this.remarkDao.get(remarkId).getTopic().getUser();
			if((userNew!=null)&&(!userNew.getUserid().equals(user.getUserid()))) {
				this.notificationDao.addNotification(6, this.remarkDao.get(remarkId).getTopic().getUser().getUserid(), null, null,
						new String[]{user.getUserid(), user.getName(), topic.getEvent().getEventid(), topic.getContent()});
			}
			if((userNew!=null)&&(!userNew.getUserid().equals(user.getUserid()))) {
				this.notificationDao.addNotification(6, this.remarkDao.get(remarkId).getUser().getUserid(), null, null,
						new String[]{user.getUserid(), user.getName(), topic.getEvent().getEventid(), topic.getContent()});
			}
			//xu end 
			//插入“好友动态表”
			this.friendActivityDao.addFriendActivity(user.getUserid(), this.topicDao.get(topicId).getContent(), CommonConstant.FRIENDACTIVITY_PARTICIPATE_TOPIC_TYPE, eventId);
			//插入个人动态表
			this.personalActivityDao.addPersonActivity(user.getUserid(), this.topicDao.get(topicId).getContent(), CommonConstant.PERSONALACTIVITY_PARTICIPATE_TOPIC_TYPE, eventId, null);
			break;
		}

		if(operateType == 1) {
			TopicVO result = new TopicVO();
			result.setAgreecount("0");
			result.setContent(topic.getContent());
			result.setCreatetime(DateUtil.date2String(topic.getCreatetime()));
			result.setDisagreecount("0");
			result.setPortrait(user.getPortrait() == null ? "" : user.getPortrait());
			result.setRemarkcount("0");
			result.setTopicid(topic.getTopicid());
			result.setUserid(user.getUserid());
			result.setUsername(user.getName());
			return result;
		}
		else {
			RemarkVO result = new RemarkVO();
			result.setContent(remark.getContent());
			result.setCreatetime(DateUtil.date2String(remark.getCreatetime()));
			result.setPortrait(user.getPortrait() == null ? "" : user.getPortrait());
			result.setRemarkid(remark.getRemarkid());
			result.setUserid(user.getUserid());
			result.setUsername(user.getName());
			return result;
		} 
		
	}
	/**
	 * @param remarkId
	 * @param agreecount
	 * @param disagreecount
	 * @return
	 * @Date:2013-5-16  
	 * @Author:xuguangyun  
	 * @Description:更新回复的顶踩数
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public int updateAgreecount(String remarkId,Integer agreecount,Integer disagreecount){
		return this.remarkDao.updateRemark(remarkId, agreecount, disagreecount);
	}

}
