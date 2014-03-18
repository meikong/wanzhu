package com.wanzhu.dao.admin;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Notification;

@Repository
public class AdminNotificationDao extends BaseDao<Notification> {

	public boolean SendNotification(Notification notification,String audit)
	{
		try {
			if(audit.equals("1"))
				notification.setContent("恭喜你,报名审核通过!你准时参加活动!");
			else
				notification.setContent("对不起,报名审核不通过!请保证会员资料正确!");
			notification.setType(1);
			notification.setRead(0);
			notification.setReplied(0);
			notification.setCreatetime(new Date());
			this.save(notification);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
