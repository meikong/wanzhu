package com.wanzhu.dao.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Event;
import com.wanzhu.utils.StringUtils;

@Repository
public class AdminEventDao  extends BaseDao<Event>{
	
	private static int RecommendationCount = 0;
	
	/**
	 * 查询被推荐的活动
	 * recommendation 是否推荐 0-否 1-是
	 * state 1-未开始 2-已开始
	 * visible 是否可见 0-不可见 1-可见
	 * max < 0 无限制  查询个数
	 * @return
	 * @Date:2013-4-15  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public List<Event> queryRecommendationEvents(int max){
		List<Event> events = this.find("from Event where visible=1 and state=1 and recommendation=1 order by showorder desc");
		if(events == null)
			return new ArrayList<Event>(0);
		if(max <= 0)
			return events;
		if(max < events.size())
			return events.subList(0, max);
		else
			return events;
	}
	
	public int queryRecommendationEventsCount(){
		List<Event> events = this.find("from Event where visible=1 and state=1 and recommendation=1");
		if(events == null)
			return 0;
		else
			return events.size();
	}
	
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param recommendation 是否推荐 0-否 1-是
	 * @param state 1-未开始 2-已开始
	 * @param visible 是否可见 0-不可见 1-可见
	 * @param content
	 * @return
	 * @throws Exception
	 * @Date:2013-4-15  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public Page<Event> queryEventsPage(int pageNo, int pageSize, String recommendation, String state, String visible, String content) throws Exception {
		Criteria cri = this.createCriteria();
		if(!StringUtils.isEmpty(content)&&!"".equals(content)) {
			cri.add(Restrictions.or(Restrictions.like("topic","%"+content+"%"), Restrictions.like("subtopic","%"+content+"%")));
		}
		//flag  1 推荐 2不推荐 3不接受报名 4接受报名 5已结束 6可见 7 不可见 8其他
		if(recommendation!=null)
		{
			Integer sta=Integer.parseInt(recommendation);
			cri.add(Restrictions.eq("recommendation", sta));
		}
		if(state!=null)
		{
			Integer sta=Integer.parseInt(state);
			cri.add(Restrictions.eq("state", sta));
		}
		if(visible!=null)
		{
			Integer sta=Integer.parseInt(visible);
			cri.add(Restrictions.eq("visible", sta));
		}
		Page<Event> result = this.pagedQuery(cri, pageNo, pageSize, Order.desc("recommendation"), Order.desc("showorder"), Order.desc("starttime"));
		return result;
	}

	/**
	 * @param pageNo 查第几页
	 * @param pageSize 每页数据
	 * @param queryType 0-往期，1-近期
	 * @param label 活动标签
	 * @param city 活动举办城市
	 * @return  xu add
	 * @throws Exception 
	 */
	public boolean updateEvents(int pageNo, int pageSize) throws Exception {
		Criteria cri = this.createCriteria();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		                 //查询制定时间之后的记录  
		cri.add(Restrictions.lt("endtime",  df.parseObject(df.format(new Date()))));  
		//flag  1 推荐 2不推荐 3不接受报名 4接受报名 5已结束 6可见 7 不可见 8其他
		cri.add(Restrictions.ne("state", 2));
		Page<Event> result = this.pagedQuery(cri, pageNo, pageSize);
		for(Event event : result.getResult()) {
			event.setState(2);
			event.setRecommendation(0);
			this.getHibernateTemplate().update(event);
			//this.save(event);
		}
		return true;
	}

	/**
	 * @param pageNo 查第几页
	 * @param pageSize 每页数据
	 * @param queryType 0-往期，1-近期
	 * @param label 活动标签
	 * @param city 活动举办城市
	 * @return
	 * @throws Exception 
	 */
	public Page<Event> queryEventsPage(int pageNo, int pageSize,String flag,String flag2,String flag3,String beginTime,String endTime,String content) throws Exception {
		Criteria cri = this.createCriteria();
		
		if(!StringUtils.isEmpty(content)&&!"".equals(content)) {
			cri.add(Restrictions.or(Restrictions.like("topic","%"+content+"%"), Restrictions.like("subtopic","%"+content+"%")));
		}
		
		if(endTime!=null&&!"".equals(endTime)&&beginTime!=null&&!"".equals(beginTime))                     //查询制定时间之后的记录  
			cri.add(Restrictions.between("starttime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime+" 00:00:01"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime+" 23:59:59")));  
		
		//flag  1 推荐 2不推荐 3不接受报名 4接受报名 5已结束 6可见 7 不可见 8其他
		if(flag!=null)
		{
			Integer sta=Integer.parseInt(flag);
			cri.add(Restrictions.eq("recommendation", sta));
		}
		if(flag2!=null)
		{
			Integer sta=Integer.parseInt(flag2);
			cri.add(Restrictions.eq("state", sta));
		}
		if(flag3!=null)
		{
			Integer sta=Integer.parseInt(flag3);
			cri.add(Restrictions.eq("visible", sta));
		}
		Page<Event> result = this.pagedQuery(cri, pageNo, pageSize, Order.desc("starttime"));
		for(Event event : result.getResult()) {
			String hql = "select count(a.assignid) from UserEvent a where a.signup = 1 and a.event.eventid = ?";
			Query query = this.getSession().createQuery(hql).setString(0, event.getEventid());
			event.setSignCount(((Number)query.iterate().next()).intValue()); 
			
			hql = "select count(a.assignid) from UserEvent a where a.audit = 1 and a.event.eventid = ?";
			query = this.getSession().createQuery(hql).setString(0, event.getEventid());
			event.setApplyCount(((Number)query.iterate().next()).intValue()); 
		}
		return result;
	}
	
	public Event queryEvents(String EventId)
	{
		return this.get(EventId);
	}
	
	public boolean saveOrUpdateEvent(Event event) {
		try {
			if(event.getEventid()!=null){
				this.update(event);
			} else {
				this.save(event);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void deleteEvent(String eventId)
	{
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("eventid", eventId);
			this.delete(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean recommendById(String eventId, int recommend) {
		int count = this.execute("update Event set recommendation=" + recommend + " where eventid='" + eventId + "'");
		return count>0?true:false;
	}
	
	/**
	 * 查询最小显示序号
	 * @param recommend
	 * @return
	 * @Date:2013-4-16  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@Deprecated
	public double queryMinShowOrder(int recommend){ 
		try {
			String min = this.createQuery("select min(e.showorder) from Event e where state=1 and recommend=?", recommend).uniqueResult().toString();
			return Double.parseDouble(min);
		} catch (Exception e) {
			return 0.0d;
		}
	}
	
	/**
	 * 不跨页 保存排序
	 * @param orders
	 * @return
	 * @Date:2013-4-23  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public boolean saveOrder(List<com.wanzhu.utils.showorder.Order> orders) {
		int result = 0;
		for (com.wanzhu.utils.showorder.Order order : orders) {
			result += this.execute("update Event set showorder=" + order.order + " where eventid='" + order.id + "'");
		}
		return result>0 ? true:false;
	}

	public List<Event> findEvent(int eventState) {
		return this.find("from Event");
	}

	/**
	 * 跨页保存序号
	 * @param id
	 * @param kind
	 * @return
	 * @Date:2013-4-23  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public boolean crossPageSaveOrder(String id, int kind) {
		Event event = this.get(id);
		String sql = "";
		if(kind == 2){//向下跨页
			sql = "select max(showorder),cast(eventid as char(32)) from t_event where recommendation=0 and state=1 and showorder=(select max(showorder) from t_event where recommendation=0 and state=1 and showorder<" + event.getShoworder() + ")";
		}else if(kind == 1){//向上跨页
			sql = "select min(showorder),cast(eventid as char(32)) from t_event where recommendation=0 and state=1 and showorder=(select min(showorder) from t_event where recommendation=0 and state=1 and showorder>" + event.getShoworder() + ")";
		}else
			return false;
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if(list == null || list.size() == 0)
			return false;
		Object[] os = list.get(0);
		long anotherShoworder = Long.parseLong(String.valueOf(os[0]));
		String anotherId = String.valueOf(os[1]);
		//上条或下条更新
		Event e = this.get(anotherId);
		e.setShoworder(event.getShoworder());
		event.setShoworder(anotherShoworder);
		this.getHibernateTemplate().merge(e);
		this.getHibernateTemplate().merge(event);
		return true;
	} 
	
	
}
