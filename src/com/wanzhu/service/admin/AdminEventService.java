package com.wanzhu.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.Page;
import com.wanzhu.dao.NotificationDao;
import com.wanzhu.dao.UserDao;
import com.wanzhu.dao.admin.AdminCodeDao;
import com.wanzhu.dao.admin.AdminEventDao;
import com.wanzhu.dao.admin.AdminEventLabelDao;
import com.wanzhu.dao.admin.AdminLabelsDao;
import com.wanzhu.dao.admin.AdminLectrueDao;
import com.wanzhu.dao.admin.AdminNotificationDao;
import com.wanzhu.dao.admin.AdminSummaryDao;
import com.wanzhu.dao.admin.AdminUserEventDao;
import com.wanzhu.entity.Code;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.EventLabel;
import com.wanzhu.entity.Label;
import com.wanzhu.entity.Lectrue;
import com.wanzhu.entity.Notification;
import com.wanzhu.entity.Summary;
import com.wanzhu.entity.User;
import com.wanzhu.entity.UserEvent;
import com.wanzhu.utils.showorder.Order;

@Service
@Transactional(readOnly=true)
public class AdminEventService {

	@Autowired
	private AdminEventDao adminEventDao;
	@Autowired
	private AdminNotificationDao adminNotificationDao;
	@Autowired
	private AdminLabelsDao adminLabelsDao;
	@Autowired
	private AdminLectrueDao adminLectrueDao;
	@Autowired
	private AdminSummaryDao adminSummaryDao;
	@Autowired
	private AdminUserEventDao adminUserEventDao;
	@Autowired
	private AdminEventLabelDao adminEventLabelDao;
	@Autowired
	private AdminCodeDao adminCodeDao;
	@Autowired
	private NotificationDao notificationDao;
	@Autowired
	private UserDao userDao;
	
	/**
	 * 移除/推荐 视频
	 * @param eventId
	 * @param recommend
	 * @return
	 * @Date:2013-4-16  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@Transactional(readOnly=false)
	public boolean recommendById(String eventId, int recommend){
		return adminEventDao.recommendById(eventId, recommend);
	}
	
	/**
	 * 首页设置
	 * @param pageNo
	 * @param pageSize
	 * @param recommendation
	 * @param state
	 * @param visible
	 * @param content
	 * @return
	 * @throws Exception
	 * @Date:2013-4-15  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public Page<Event> queryEventsPage(int pageNo, int pageSize, String recommendation, String state, String visible, String content) throws Exception {
		return adminEventDao.queryEventsPage(pageNo, pageSize, recommendation, state, visible, content);
	}
	
	
	public Page<Summary> querySummaryPage(int type, int pageNo, int pageSize, String isRecommend, String content) throws Exception {
		return adminSummaryDao.querySummaryPage(type, pageNo, pageSize, isRecommend, content);
	}
	
	
	public Page<Event> queryEventsPage(int pageNo, int pageSize,String flag,String flag2,String flag3,String beginTime,String endTime,String content) throws Exception {
		if("请选择活动开始日期".equals(beginTime)&&beginTime!=null&&!"".equals(beginTime))
		{
			beginTime=null;
		}
		if("请选择活动结束日期".equals(endTime)&&endTime!=null&&!"".equals(endTime))
		{
			endTime=null;
		}
		if("请输入查询条件".equals(content)&&content!=null&&!"".equals(content))
		{
			content=null;
		}
		if("8".equals(flag))flag=null;
		if("8".equals(flag2))flag2=null;
		if("8".equals(flag3))flag3=null;
		return adminEventDao.queryEventsPage(pageNo, pageSize,flag,flag2, flag3,beginTime, endTime, content);
	}
	
	public List<Code> findCode(){
		return adminCodeDao.findCode();
	}
	
	public int queryRecommendationEventsCount(){
		return adminEventDao.queryRecommendationEventsCount();
	}
	
	/**
	 * 没有用到
	 * @param max
	 * @return
	 * @Date:2013-4-15  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public List<Map<String, Object>> queryRecommendationEvents(int max){
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>(0);
        for(Event event : adminEventDao.queryRecommendationEvents(max)){
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("topic", event.getTopic());
            temp.put("citycode", event.getCitycode());
            temp.put("starttime", event.getStarttime());
            temp.put("showorder", event.getShoworder());
            temp.put("recommendation", event.getRecommendation());
            result.add(temp);
        }
        return result;
    }
	/**
	 * xu add
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean updateEvents()
	{
		try {
			return adminEventDao.updateEvents(1,10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public Event queryEvents(String EventId)
	{
		return adminEventDao.queryEvents(EventId);
	}
	
	@Transactional(readOnly=false)
	public boolean saveOrUpdateEvent(Event event,String[] biaoqianList,	String[] jiabinList) {
		if(event.getEventid()==null){//创建活动 ，生成序号
			//double min = adminEventDao.queryMinShowOrder(event.getRecommendation());//得到当前排在第一位的序号
			event.setShoworder(System.currentTimeMillis());
		}
		boolean bool= adminEventDao.saveOrUpdateEvent(event);
		if(bool)
		{
			this.delEventLabelEventId(event.getEventid());
			this.deleteLectrueEventId(event.getEventid());
			//添加标签 会员
			List<Lectrue> lectruelist=new ArrayList<Lectrue>();
			for (String string : jiabinList) {
				Lectrue lectrue=new Lectrue();
				
				Event eventlectrue=new Event();
				eventlectrue.setEventid(event.getEventid());
				lectrue.setEvent(eventlectrue);
				
				User userlectrue=new User();
				userlectrue.setUserid(string);
				lectrue.setUser(userlectrue);
				
				lectruelist.add(lectrue);
			}
			
			List<EventLabel> eventLabellist=new ArrayList<EventLabel>();
			for (String labelid : biaoqianList) {
				EventLabel eventlabel=new EventLabel();
				
				Event eventlectrue=new Event();
				eventlectrue.setEventid(event.getEventid());
				eventlabel.setEvent(eventlectrue);
				
				Label labelEvent=new Label();
				labelEvent.setLabelid(labelid);
				eventlabel.setLabel(labelEvent);
				
				eventLabellist.add(eventlabel);
			}
			
			if(lectruelist.size()!=0){
				for (int i = 0; i < lectruelist.size(); i++) {
					this.saveLectrue(lectruelist.get(i));
				}
			}
			if(eventLabellist.size()!=0){
				for (int i = 0; i < eventLabellist.size(); i++) {
					adminEventLabelDao.saveEventLabel(eventLabellist.get(i));
				}
			}
					
			return true;
		}else
		{
			return false;
		}
	}
	@Transactional(readOnly=false)
	public void deleteEvent(String eventId)	{
		adminEventDao.deleteEvent(eventId);
	}
	
	@Transactional(readOnly=false)
	public boolean saveLabel(Label label)
	{
		return adminLabelsDao.saveLabel(label);
	}
	
	public int listLabelWhere(String label)
	{
		return adminLabelsDao.listLabelWhere(label);
	}
	
	public List<Label> listEventLabel()
	{
		return adminLabelsDao.listLabel();
	}
	
	@Transactional(readOnly=false)
	public boolean saveLectrue(Lectrue lectrue) {
		return adminLectrueDao.saveLectrue(lectrue);
	}
	
	public String findLectrue(String evid)
	{
		return adminLectrueDao.findLectrue(evid);
	}
	
	@Transactional(readOnly=false)
	public boolean deleteLectrue(String LectrueId) {
		return adminLectrueDao.deleteLectrue(LectrueId);
	}
	
	@Transactional(readOnly=false)
	public boolean deleteLectrueEventId(String eventid) {
		if(this.findLectrue(eventid)!=null){
			return adminLectrueDao.deleteLectrueEventId(eventid);
		}
		return false;
	}
		
	public List<Summary>  listSummary(String eventId){
		return adminSummaryDao.listSummary(eventId);
	}
	
	@Transactional(readOnly=false)
	public boolean saveSummary(Summary summary)
	{
		return adminSummaryDao.saveSummary(summary);
	}
	
	@Transactional(readOnly=false)
	public boolean updateSummaryEvent(String id,String eventid){
		return adminSummaryDao.updateSummaryEvent(id, eventid);
	}
	
	@Transactional(readOnly=false)
	public boolean updateSummary(Summary summary)
	{
		return adminSummaryDao.updateSummary(summary);
	}
	
	@Transactional(readOnly=false)
	public boolean deleteSummary(String summaryId)
	{
		return adminSummaryDao.deleteSummary(summaryId);
	}
	
	public Page<UserEvent> queryUserEventPage(String id,int pageNo, int pageSize,Integer start) throws Exception
	{
		return adminUserEventDao.queryUserEventPage(id,pageNo, pageSize, start);
	}
	
	/**
	 * 模糊检索：支持拼音简拼
	 */
	public Page<UserEvent> queryUserEventPages(String name,String id,int pageNo, int pageSize,Integer start) throws Exception
	{
		return adminUserEventDao.queryUserEventPages(name,id,pageNo, pageSize, start);
	}
	
	public boolean queryAudit(String id){
		return adminUserEventDao.queryAudit(id);
	}
	
	@Transactional(readOnly=false)
	public boolean upUserEvent(String UserEventId,String signup,String audit)
	{
		boolean result =  adminUserEventDao.upUserEvent(UserEventId, signup, audit);
		this.userDao.updateSignedEventscount(UserEventId);
		return result;
		
	}
	public UserEvent queryAudit2(String id){
		return adminUserEventDao.queryAudit2(id);
	}
	
	@Transactional(readOnly=false)
	public boolean SendNotification(Notification notification,String audit)
	{
		return adminNotificationDao.SendNotification(notification,audit);
	}
	
	public String findEventLabel(String veid)
	{
		return adminEventLabelDao.findEventLabel(veid);
	}
	
	@Transactional(readOnly=false)
	public boolean delEventLabel(String id)
	{
		return adminEventLabelDao.delEventLabel(id);
	}
	
	@Transactional(readOnly=false)
	public boolean delEventLabelEventId(String id)
	{
		if(this.findEventLabel(id)!=null)
		{
			return adminEventLabelDao.delEventLabelEventId(id);
		}
		return false;
	}
	
	public Summary getSummary(String id)
	{
		return adminSummaryDao.getSummary(id);
	}
	
	public List<Summary>  listSummaryType(String eventId)
	{
		return adminSummaryDao.listSummaryType(eventId);
	}

	@Transactional(readOnly=false)
	public void addNotification(int type, String userId, String friendId, String[] params) {
		notificationDao.addNotification(type, userId, friendId, null, params);
	}

	@Transactional(readOnly=false)
	public boolean saveEventOrder(List<Order> orders) {
		return adminEventDao.saveOrder(orders);
	}
	
	@Transactional(readOnly=false)
	public boolean saveOrder(List<Order> orders) {
		return adminSummaryDao.saveOrder(orders);
	}

	public int queryRecommendationSummaryCount(int type) {
		return adminSummaryDao.queryRecommendationSummaryCount(type);
	}

	public List<Event> findEvent(int eventState) {
		return adminEventDao.findEvent(eventState);
	}

	@Transactional(readOnly=false)
	public boolean recommendSummaryById(String summaryid, int kind) {
		return adminSummaryDao.recommendById(summaryid, kind);
		
	}

	@Transactional(readOnly=false)
	public boolean crossPageSaveOrder(String id, int type, int kind) {
		if(type == 1)
			return adminEventDao.crossPageSaveOrder(id, kind);
		else if(type == 2 || type == 0)
			return adminSummaryDao.crossPageSaveOrder(id, kind);
		else 
			return false;
	}
	
	@Transactional(readOnly=false)
	public Summary saveArticle(Summary summary) {
		return adminSummaryDao.saveArticle(summary);
	}

	
}
