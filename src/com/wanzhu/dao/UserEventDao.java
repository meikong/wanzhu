package com.wanzhu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.UserEvent;

@Repository
public class UserEventDao extends BaseDao<UserEvent> {
	
	public boolean hasSignUped(String userId, String eventId) {
		List<UserEvent> ues = this.find("from UserEvent where user.userid=? and event.eventid=? ", new Object[]{userId, eventId});
		if(ues.size()>0) {
			return true;
		}
		return false;
	}

	public void unsign(String userId, String eventId) {
		String hql = "delete from UserEvent where user.userid=:userId and event.eventid=:eventId ";
		this.getSession().createQuery(hql).setString("userId", userId).setString("eventId", eventId).executeUpdate();
	}

	public String finId(String userId, String eventId) {
		List<UserEvent> list = this.find("from UserEvent ue where ue.event.eventid=? and ue.user.userid=?",new Object[]{eventId, userId});
		if(list == null || list.size() == 0)
			return null;
		else
			return list.get(0).getAssignid();
	}
	
}
