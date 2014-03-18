package com.wanzhu.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Event;
import com.wanzhu.jsonvo.ArticleVo;
import com.wanzhu.jsonvo.EventVO;
import com.wanzhu.jsonvo.VideoVo;
import com.wanzhu.utils.StringUtils;

@Repository
public class EventDao extends BaseDao<Event> {

	/**
	 * @param pageNo 查第几页
	 * @param pageSize 每页数据
	 * @param queryType 0-往期，1-近期
	 * @param label 活动标签
	 * @param city 活动举办城市
	 * @return
	 * @throws Exception 
	 */
	public Page<Event> queryEventsPage(int pageNo, int pageSize, int recent, String labelId, String cityCode) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select e from Event as e ");
		if(!StringUtils.isEmpty(labelId)) {
			hql.append("left join e.eventLabels el where 1=1 ");
		} else {
			hql.append("where 1=1 ");
		}
		
		List<Object> params = new ArrayList<Object>();
		switch (recent) {
			case 0:
				params.add(CommonConstant.STATUS_EVNET_END);
				hql.append("and e.state=? ");
				break;
			case 1:
				params.add(CommonConstant.STATUS_EVNET_END);
				hql.append("and e.state<>? ");
			default:
				break;
		}
		if(!StringUtils.isEmpty(cityCode)) {
			params.add(cityCode);
			hql.append("and e.citycode=? ");
		}
		if(!StringUtils.isEmpty(labelId)) {
			params.add(labelId);
			hql.append("and el.label.labelid=? ");
		}
		hql.append("and e.visible=1 ");
		hql.append("order by e.recommendation desc, e.starttime desc, e.eventid desc ");
		Page<Event> page = this.pagedQuery(hql.toString(), pageNo, pageSize, params.toArray());
		List<Event> list = page.getResult();
		for(Event e : list){
			e.setApplyCount(getApplyCount(e.getEventid()));
		}
		return page;
	}

	/**
	 * 查询往期精采活动
	 * @return
	 */
	public List<Event> queryRecommendPastEvents() {
		return this.find("from Event where state=? and recommendation=? and visible=? order by starttime desc, eventid desc ", new Object[]{2, 1, 1});
	}

	/**
	 * 查询近期推荐活动
	 * @return
	 * @Date:2013-4-24  
	 * @Author:Guibin Zhang  
	 * @Description: 修改了轮播的排序
	 */
	public List<Event> queryRecommendComingEvents() { 
		List<Event> list =  this.find("from Event where state!=? and recommendation=? and visible=? order by showorder desc, starttime desc, eventid desc ", new Object[]{2, 1, 1});
		for(Event e : list){
			//System.out.println(e);
			//System.out.println(e.getCitycode());
			e.setApplyCount(getApplyCount(e.getEventid()));
		}
		return list;
	}
	
	public Event findById(String eventId) {
		Event e = this.load(eventId);
		e.setApplyCount(getApplyCount(e.getEventid()));
		return e;
	}
	
	private Integer getApplyCount(String eventId) {
		String hql = "select count(a.assignid) from UserEvent a where a.audit = 1 and a.event.eventid = ?";
		Query query = this.getSession().createQuery(hql).setString(0, eventId);
		return ((Number)query.iterate().next()).intValue();
	}

	public List<Event> queryEventsInTimeRange(Date start, Date end) {
		return this.find("from Event e where visible=1 and e.endtime>=? and e.starttime<=? ", new Object[]{start, end});
	}

	/**
	 * 查询将要开始的活动所在举办城市代码
	 * @return
	 */
	public List<String> queryCitis() {
		String sql = "SELECT distinct e.citycode from t_event e where e.state=1 and e.visible=1 order by e.citycode ";
		return this.getSession().createSQLQuery(sql).list();
	}

	public List<EventVO> querySignUpedEvents(String userId) {
		String sql = "SELECT e.eventid, e.topic, e.state FROM t_event e " +
				" LEFT JOIN " +
				" t_user_event ue " +
				" ON " +
				" e.eventid=ue.eventid " +
//				"where ue.userid=?  and ue.audit=1 " +
				"where ue.userid=? " +
				"ORDER BY e.state, e.starttime DESC";
		List rs = this.getSession().createSQLQuery(sql)
				.addScalar("eventid", Hibernate.STRING)
				.addScalar("topic", Hibernate.STRING)
				.addScalar("state", Hibernate.INTEGER)
				.setString(0, userId)
				.list();
		List<EventVO> vos = new ArrayList<EventVO>();
		for (int i = 0; i < rs.size(); i++) {
			Object obj[] = (Object[]) rs.get(i);
			vos.add(new EventVO((String)obj[0], (String)obj[1], (Integer)obj[2]));
		}
		return vos;
	}

}
