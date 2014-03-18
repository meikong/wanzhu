package com.wanzhu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.CommonConstant;
import com.wanzhu.dao.FriendsDao;
import com.wanzhu.dao.MessageDao;
import com.wanzhu.entity.Message;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.MessageSummaryVO;
import com.wanzhu.jsonvo.MessageVO;
import com.wanzhu.task.MailTask;
import com.wanzhu.utils.SmtpClient;
import com.wanzhu.utils.TemplateHelper;

/**
 * 私信
 * @author zhanglei
 *
 */
@Service
@Transactional(readOnly=true)
public class MessageService {
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private FriendsDao friendsDao;
	
	/**
	 * 查询我的私信
	 * @return 
	 */
	public List<MessageSummaryVO> queryMessages(String userId, int start, int size) {
		String[][] results = this.messageDao.queryMessages(userId, start, size);
		List<MessageSummaryVO> resultList = new ArrayList<MessageSummaryVO>();
		for(int i = 0; i < results.length; i++) {
			MessageSummaryVO result = new MessageSummaryVO();
			result.setUserid(results[i][0]);
			result.setUsername(results[i][1]);
			result.setPortrait(results[i][2] == null ? "" : results[i][2]);
			result.setMessageid(results[i][3]);
			result.setCreatetime(results[i][4]);
			result.setContent(results[i][5]);
			result.setCount(results[i][6]);
			result.setUnreadcount(results[i][7]);
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * 查询与某人的私信
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public List<MessageVO> queryMessagesWithAFriend(String userId, String friendId, int start, int size) {
		String[][] results = this.messageDao.queryMessagesWithAFriend(userId, friendId, start, size);
		List<String> messageIds = new ArrayList<String>();
		List<MessageVO> resultList = new ArrayList<MessageVO>();
		for(int i = 0; i < results.length; i++) {
			MessageVO result = new MessageVO();
			result.setUserid(results[i][0]);
			result.setUsername(results[i][1]);
			result.setPortrait(results[i][2] == null ? "" : results[i][2]);
			result.setCreatetime(results[i][3]);
			result.setContent(results[i][4]);
			result.setMessageid(results[i][5]);
			result.setRead(results[i][6]);
			resultList.add(result);
			if("0".equals(results[i][6])) {
				messageIds.add(results[i][5]);
			}
		}
		this.messageDao.setMessageRead(messageIds);
		return resultList;
	}
	
	/**
	 * 发送私信
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void sendMessage(String userId, String userName, String friendId, String friendName, String content, String friendEmail) {
		if(userId.equals(friendId)) {
			throw new RuntimeException("30004");
		}
		boolean isFriend = this.friendsDao.isFriend(userId, friendId);
		if(!isFriend) { //不是好友
			throw new RuntimeException("30002");
		}
		Message message = new Message();
		message.setUserByUserid(new User(userId));
		message.setUserByFriendid(new User(friendId));
		message.setContent(content);
		message.setCreatetime(new Date());
		message.setRead(0);
		message.setDeletebyuser(0);
		message.setDeletebyfriend(0);
		this.messageDao.save(message);
//		if(false) {//发送邮件
//			try {
//				Map<String, String> pairs=new HashMap<String, String>();
//				pairs.put("name", friendName);
//				pairs.put("sender", userName);
//				StringBuffer sb = TemplateHelper.merge("messagenotify.tpl", pairs);
//				SmtpClient.sendMail( CommonConstant.recommendation_mail_from_account, 
//									 CommonConstant.recommendation_mail_from_account,
//									 CommonConstant.recommendation_mail_from_name,
//									 CommonConstant.recommendation_mail_from_account_password,
//									 friendEmail,
//									 CommonConstant.smtp_server,
//									 CommonConstant.message_mail_subject,
//									 sb.toString(),
//						             true );
//				LogFactory.getLog(MailTask.class).info("发送私信通知邮件至用户[" + friendId + "]的邮箱["+ friendEmail +"]成功。");
//			}
//			catch(Exception ex) {
//				LogFactory.getLog(MailTask.class).error("发送私信通知邮件至用户[" + friendId + "]的邮箱[" + friendEmail + "]失败。", ex);
//			}
//		}

	}
	
	/**
	 * 删除私信
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void deleteMessage(String messageId, boolean isSelf) {
		Message message = this.messageDao.get(messageId);
		if(isSelf)
			message.setDeletebyuser(1);
		else
			message.setDeletebyfriend(1);
		this.messageDao.update(message);
	}
	
	/**
	 * 与某人私信条数
	 * @return
	 */
	public int getMessagesCountWithAFriend(String userId, String friendId) {
		return this.messageDao.getMessagesCountWithAFriend(userId, friendId);
	}
	
	/**
	 * 未读私信条数
	 * @return
	 */
	public int getUnreadMessageCount(String userId) {
		return this.messageDao.getUnreadMessageCount(userId);
	}
}
