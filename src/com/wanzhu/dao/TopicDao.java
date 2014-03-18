package com.wanzhu.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Topic;
import com.wanzhu.utils.DateUtil;

/**
 * 话题
 * @author zhanglei
 *
 */
@Repository
public class TopicDao extends BaseDao<Topic> {

	/**
	 * 查询话题
	 * @return
	 */
	public String[][] queryTopics(String eventId, int start, int size) {
		String[][] result = null;
		String sql = "select cast(t.topicid as char(32)), cast(u.userid as char(32)), u.portrait, u.name, t.createtime, t.content, t.agreecount, t.disagreecount, t.remarkcount " +
			     "from t_topic t, t_user u " +
			     "where t.eventid = '" + eventId + "' and t.userid = u.userid " +
			     "order by t.agreecount desc, t.disagreecount asc, t.createtime desc";
		List<Object[]> list = this.getSession().createSQLQuery(sql).setFirstResult(start).setMaxResults(size).list();
		result = new String[list.size()][9];
		for(int i = 0; i < list.size(); i++) {
			result[i][0] = (String)list.get(i)[0];
			result[i][1] = (String)list.get(i)[1];
			result[i][2] = (String)list.get(i)[2];
			result[i][3] = (String)list.get(i)[3];
			result[i][4] = DateUtil.date2String((Date)list.get(i)[4]);
			result[i][5] = (String)list.get(i)[5];
			result[i][6] = String.valueOf(list.get(i)[6]);
			result[i][7] = String.valueOf(list.get(i)[7]);
			result[i][8] = String.valueOf(list.get(i)[8]);
		}
		return result;
	}
 /**
  * @param id
  * @return
  * @Date:2013-5-8  
  * @Author:xuguangyun  
  * @Description:通过话题的ID查询话题
  */
    public String[][] getTopicById(String id){
    	
    	String[][] result = null;
		String sql = "select cast(t.topicid as char(32)), cast(u.userid as char(32)), u.portrait, u.name, t.createtime, t.content, t.agreecount, t.disagreecount, t.remarkcount,cast(t.eventid as char(32))  " +
			     "from t_topic t, t_user u " +
			     "where t.topicid = '" +id+ "' and t.userid = u.userid " +
			     "order by t.agreecount desc, t.disagreecount asc, t.createtime desc";
		List<Object[]> list = this.getSession().createSQLQuery(sql).setFirstResult(0).setMaxResults(5).list();
		result = new String[list.size()][10];
		for(int i = 0; i < list.size(); i++) {
			result[i][0] = (String)list.get(i)[0];
			result[i][1] = (String)list.get(i)[1];
			result[i][2] = (String)list.get(i)[2];
			result[i][3] = (String)list.get(i)[3];
			result[i][4] = DateUtil.date2String((Date)list.get(i)[4]);
			result[i][5] = (String)list.get(i)[5];
			result[i][6] = String.valueOf(list.get(i)[6]);
			result[i][7] = String.valueOf(list.get(i)[7]);
			result[i][8] = String.valueOf(list.get(i)[8]);
			result[i][9] = String.valueOf(list.get(i)[9]);
		}
		return result;
    }
}
