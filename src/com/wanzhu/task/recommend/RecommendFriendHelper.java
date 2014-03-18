 /**  
 *@Description:     
 */ 
package com.wanzhu.task.recommend;  

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.wanzhu.base.AppliactionContextHelper;
import com.wanzhu.dao.RecommendFriendDao;
import com.wanzhu.entity.RecommendFriends;
import com.wanzhu.entity.User;
import com.wanzhu.service.admin.AdminEventService;
  
public class RecommendFriendHelper {
	
	private static final String FRIEND_ID = "friendid";
	@Autowired
	private static RecommendFriendDao recommendFriendDao;
	@Autowired
	private static HibernateTemplate hdao;
	@Autowired
	private static JdbcTemplate jdao;
	
	
	public RecommendFriendHelper(HibernateTemplate hdao, JdbcTemplate jdao){
		RecommendFriendHelper.hdao = hdao;
		RecommendFriendHelper.jdao = jdao;
	}
	
	/**
	 * 执行总入口
	 * @Date:2013-5-27  
	 * @Author:Guibin Zhang  
	 * @Description:
	 * 对每个用户建立推荐好友，并持久化
	 * 在循环里删除该好友的上次的推荐信息是为了尽可能让用户减少不可用时间
	 */
	public static void execute() {
		if(hdao == null){
			hdao = AppliactionContextHelper.getBean("hibernateTemplate", HibernateTemplate.class);
			System.out.println("hdao " + (hdao == null));
		}
		if(jdao == null){
			jdao = AppliactionContextHelper.getBean("jdbcTemplate", JdbcTemplate.class);
			System.out.println("jdao " + (jdao == null));
		}
		System.out.println("================== 重建推荐好友库开始  =======================");
		List<User> users = hdao.find("from User");
		jdao.update("delete from t_recommend_friends");
		for (User user : users) {
			//1.删除该用户的所有好友信息
			
			//2.查找各种推荐好友，并持久化
			List<Map<String, Object>> oldFriends = jdao.queryForList("select friendid from t_friends where userid='" + user.getUserid() + "'");
			List<Map<String, Object>> oldFriends2 = jdao.queryForList("select userid as friendid from t_friends where friendid='" + user.getUserid() + "'");
			oldFriends.addAll(oldFriends2);
			findBySchool(user, oldFriends);//校友
			findByCompany(user, oldFriends);//就职同一公司
			findByPosition(user, oldFriends);//从事同一职位
			findByEvent(user, oldFriends);//参加同一活动
		} 
		System.out.println("================== 重建推荐好友库结束 =======================");
	}
	
	private static void findBySchool(User user, List<Map<String, Object>> oldFriends){
		String reason = "我们是校友";
		List<String> friendids = new ArrayList<String>();
		//2.查询学校里的好友,排除自身
		List<Map<String, Object>> schoolFriends = jdao.queryForList("select userid as friendid from t_educationexperience where userid<>'" + user.getUserid() + "' and schoolid in(select schoolid from t_educationexperience where userid='" + user.getUserid() + "')");
		boolean flag = false;//存在的标记
		for (Map<String, Object> school : schoolFriends) {//遍历校友id
			String friendid = school.get(FRIEND_ID).toString();
			for (Map<String, Object> old : oldFriends) {
				String oldFriendId = old.get(FRIEND_ID).toString();
				if(friendid.equals(oldFriendId)){
					flag = true;//如果存在就为true
					break;
				}
			}
			if(!flag){//是校友且不是好友
				friendids.add(friendid);
			}
			flag = false;
		}
		//持久化信息
		persist(user, friendids, reason);
	}
	
	private static void findByCompany(User user, List<Map<String, Object>> oldFriends){
		String reason = "我们是同事";
		List<String> friendids = new ArrayList<String>();
		//2.查询所有同一公司的人,排除自身
		List<Map<String, Object>> eventFriends = jdao.queryForList("select userid as friendid from t_workexperience where userid<>'" + user.getUserid() + "' and companyid in(select companyid from t_workexperience where userid='" + user.getUserid() + "')");
		boolean flag = false;//存在的标记
		for (Map<String, Object> school : eventFriends) {//遍历同一公司的人
			String friendid = school.get(FRIEND_ID).toString();
			for (Map<String, Object> old : oldFriends) {
				String oldFriendId = old.get(FRIEND_ID).toString();
				if(friendid.equals(oldFriendId)){
					flag = true;//如果存在就为true
					break;
				}
			}
			if(!flag){//是统一送公司的人且不是好友
				friendids.add(friendid);
			}
			flag = false;
		}
		//持久化信息
		persist(user, friendids, reason);
	}
	
	private static void findByPosition(User user, List<Map<String, Object>> oldFriends){
		String reason = "我们职务相同";
		List<String> friendids = new ArrayList<String>();
		//2.查询所有同一职位的人,排除自身
		List<Map<String, Object>> eventFriends = jdao.queryForList("select userid as friendid from t_workexperience where userid<>'" + user.getUserid() + "' and position in (select position from t_workexperience where userid='" + user.getUserid() + "')");
		boolean flag = false;//存在的标记
		for (Map<String, Object> school : eventFriends) {//遍历同一职位的人
			String friendid = school.get(FRIEND_ID).toString();
			for (Map<String, Object> old : oldFriends) {
				String oldFriendId = old.get(FRIEND_ID).toString();
				if(friendid.equals(oldFriendId)){
					flag = true;//如果存在就为true
					break;
				}
			}
			if(!flag){//是同职位的人且不是好友
				friendids.add(friendid);
			}
			flag = false;
		}
		//持久化信息
		persist(user, friendids, reason);
	}
	
	private static void findByEvent(User user, List<Map<String, Object>> oldFriends){
		String reason = "我们参加过同一个活动";
		List<String> friendids = new ArrayList<String>();
		//2.查询参加了同一活动的人,排除自身
		List<Map<String, Object>> eventFriends = jdao.queryForList("select userid as friendid from t_user_event where userid<>'" + user.getUserid() + "' and eventid in(select eventid from t_user_event where userid='" + user.getUserid() + "')");
		boolean flag = false;//存在的标记
		for (Map<String, Object> school : eventFriends) {//遍历参加同一活动的人
			String friendid = school.get(FRIEND_ID).toString();
			for (Map<String, Object> old : oldFriends) {
				String oldFriendId = old.get(FRIEND_ID).toString();
				if(friendid.equals(oldFriendId)){
					flag = true;//如果存在就为true
					break;
				}
			}
			if(!flag){//是参加同一活动的人且不是好友
				friendids.add(friendid);
			}
			flag = false;
		}
		//持久化信息
		persist(user, friendids, reason);
	}
	
	/**
	 * 持久化推荐好友信息
	 * 1.如果不存在的，创建
	 * 2.存在的审查一下是否含有本元素，不含---创建，含有---修改
	 * @param user
	 * @param friends
	 * @param reason
	 * @Date:2013-5-27  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	private static void persist(User user, List<String> friends, String reason){
		String userId = user.getUserid();
		for (String friendid : friends) {
			//1.查询
			List<RecommendFriends> oldFriendList = hdao.find("from RecommendFriends where userid='" + userId + "' and friendid='" + friendid + "'");
			if(oldFriendList == null || oldFriendList.size() == 0){//不存在此记录
				RecommendFriends recommendFriends = new RecommendFriends(userId, friendid, reason);
				hdao.merge(recommendFriends);
			}else if(oldFriendList.size() == 1){//已经存在这条记录
				RecommendFriends recommendFriends = oldFriendList.get(0);
				if(recommendFriends.getReason() == null || recommendFriends.getReason().length() == 0){//原因为空
					recommendFriends.setReason(reason);
					recommendFriends.setHit(recommendFriends.getHit() + 1);
				}else{
					if(!reason.contains(recommendFriends.getReason())){//不存在这个理由
						recommendFriends.setReason(recommendFriends.getReason() + "," + reason);
						recommendFriends.setHit(recommendFriends.getHit() + 1);
					}else{//已经存在这个理由
						recommendFriends.setHit(recommendFriends.getHit() + 1);
					}
				}
				hdao.merge(recommendFriends);
			}else{//存在过多记录
				jdao.update("delete from t_recommend_friends where userid='" + userId + "' and friendid='" + friendid + "'");
				RecommendFriends recommendFriends = new RecommendFriends(userId, friendid, reason);
				hdao.merge(recommendFriends);
			}
		}
	}
	
	
	
}
