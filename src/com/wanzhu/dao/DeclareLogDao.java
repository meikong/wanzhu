package com.wanzhu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.DeclareLog;

/**
 * 赞和踩的操作日志表
 * @author zhanglei
 *
 */
@Repository
public class DeclareLogDao extends BaseDao<DeclareLog> {

	/**
	 * 根据主题和用户获取赞和踩的日志
	 * @return
	 */
	public List<DeclareLog> getDeclareLogListByTopicAndUser(String topicId, String userId) {
		String hql = "from DeclareLog d where d.topic.topicid = ? and d.user.userid = ?";
		return this.getSession().createQuery(hql).setParameter(0, topicId).setParameter(1, userId).list();
	}
}
