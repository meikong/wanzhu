package com.wanzhu.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.rmi.runtime.Log;

import com.wanzhu.dao.admin.AdminIndexDao;
import com.wanzhu.entity.admin.Administrator;

@Service
@Transactional(readOnly=true)
public class AdminIndexService {

	@Autowired
	private AdminIndexDao adminIndexDao;
	
	public Administrator login(Administrator administrator)
	{
		try {
			
			return adminIndexDao.login(administrator);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("300003");
		}
	}
	
}
