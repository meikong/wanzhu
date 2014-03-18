package com.wanzhu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.dao.NotificationDao;
import com.wanzhu.entity.Notification;
import com.wanzhu.jsonvo.NotificationVO;
import com.wanzhu.utils.DateUtil;

/**
 * 通知
 * @author zhanglei
 *
 */
@Service
@Transactional(readOnly=true)
public class NotificationService {
	
	@Autowired
	private NotificationDao notificationDao;
	
	
	/**
	 * 查询通知列表
	 * notificationid
	 * content
	 * type
	 * createtime
	 * replied
	 * read
	 * @return json
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public List<NotificationVO> queryNotifications(String userId, int start, int size) {
		List<Notification> notificationList = this.notificationDao.queryNotifications(userId, start, size);
		List<String> notificationIds = new ArrayList<String>();
		List<NotificationVO> resultList = new ArrayList<NotificationVO>();
		for(Notification notification : notificationList) {
			NotificationVO result = new NotificationVO();
			result.setNotificationid(notification.getNotificationid());
			result.setContent(notification.getContent());
			result.setType(notification.getType() + "");
			result.setCreatetime(DateUtil.date2String(notification.getCreatetime()));
			result.setReplied(notification.getReplied() + "");
			result.setRead(notification.getRead() + "");
			if(notification.getInviter() != null)
				result.setInviter(notification.getInviter().getUserid());
			else
				result.setInviter("");
			resultList.add(result);
			if(0 == notification.getRead()) {
				notificationIds.add(notification.getNotificationid());
			}
		}
		this.notificationDao.setNotificationRead(notificationIds);
		return resultList;
	}
	
	/**
	 * 删除全部通知
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void deleteAllNotifications(String userId) {
		this.notificationDao.deleteAllNotifications(userId);
	}
	
	/**
	 * 删除通知
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void deleteNotification(String notificationid) {
		this.notificationDao.deleteNotification(notificationid);
	}
	
	/**
	 * 未读通知条数
	 * @return
	 */
	public int getUnreadNotificationCount(String userId) {
		return this.notificationDao.getUnreadNotificationCount(userId);
	}

}
