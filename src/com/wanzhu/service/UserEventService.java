package com.wanzhu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.dao.UserEventDao;
import com.wanzhu.entity.UserEvent;

@Service
@Transactional(readOnly=true)
public class UserEventService {

	@Autowired
	private UserEventDao userEventDao;
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public boolean signUp(String userId, String eventId) {
		boolean signed = false;
		if(!this.userEventDao.hasSignUped(userId, eventId)) {
			this.userEventDao.save(new UserEvent(userId, eventId));
			signed = true;
		}
		return signed;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void unsign(String userId, String eventId) {
		this.userEventDao.unsign(userId, eventId);
	}
	
	@Transactional 
	public String findId(String userId, String eventId) {
		return this.userEventDao.finId(userId, eventId);
	}
	
}
