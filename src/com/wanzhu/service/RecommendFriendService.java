 /**  
 *@Description:     
 */ 
package com.wanzhu.service;  

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.Page;
import com.wanzhu.dao.RecommendFriendDao;
import com.wanzhu.entity.RecommendFriends;
import com.wanzhu.entity.User;
import com.wanzhu.jsonvo.EventVO;
import com.wanzhu.jsonvo.RecommendFriendVO;
 
@Service
@Transactional(readOnly=true)
public class RecommendFriendService {
	private static final String ENABLE = "0"; //可用
	@Autowired
	RecommendFriendDao recommendFriendDao;
	
	public Page<RecommendFriendVO> queryRecommendFriendsPage(int pageNo, int pageSize, String userid) throws Exception {
		Page<RecommendFriends> page = recommendFriendDao.queryRecommendFriendsPage(pageNo, pageSize, userid, ENABLE);
		Page<RecommendFriendVO> result = new Page<RecommendFriendVO>(page.getStart(), page.getTotalCount(), page.getPageSize(), null);
		List<RecommendFriendVO> data = new ArrayList<RecommendFriendVO>();
		for (RecommendFriends recommendFriends : page.getResult()) {
			RecommendFriendVO vo = new RecommendFriendVO();
			vo.setUserId(recommendFriends.getFriendid());
			vo.setReason(recommendFriends.getReason());
			User user = recommendFriendDao.getHibernateTemplate().get(User.class, recommendFriends.getFriendid());
			vo.setUsername(user.getName());
			vo.setPortrait(user.getPortrait());
			vo.setCompany(null);///这个用设置么
			vo.setWorkPosition(null);//还有这个
			data.add(vo);
		}
		result.setResult(data);
		return result; 
	}

}
