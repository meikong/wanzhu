package com.wanzhu.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.Notification;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.Summary;
import com.wanzhu.entity.User;
import com.wanzhu.entity.UserEvent;
import com.wanzhu.jsonvo.EventVO;
import com.wanzhu.jsonvo.RemarkHotVo;
import com.wanzhu.jsonvo.TopicVO;
import com.wanzhu.service.ActivityService;
import com.wanzhu.service.AdvService;
import com.wanzhu.service.DiscuzService;
import com.wanzhu.service.EventService;
import com.wanzhu.service.HotService;
import com.wanzhu.service.UserEventService;
import com.wanzhu.service.UserService;
import com.wanzhu.service.admin.AdminEventService;
import com.wanzhu.task.MailTask;
import com.wanzhu.task.MailThread;
import com.wanzhu.utils.TemplateHelper;

/**
 * 活动
 */
@Controller
@RequestMapping("/event")
public class EventController extends BaseController {
	private static final long flag = 86400000L;
	@Autowired
	private EventService eventService;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private AdvService advService;
	
	@Autowired
	private HotService hotService;
	@Autowired
	private DiscuzService discuzService;
	
	@Autowired
	private AdminEventService adminEventService;
	/**
	 * @param model
	 * @param eventId
	 * @return
	 * @throws Exception
	 * @Date:2013-4-28  
	 * @Author:xuguangyun  
	 * @Description:跳转到活动的视频列表
	 */
	@RequestMapping("/articleList_{eventId}_{summaryId}.html")
	public String redirectArticle(Model model, @PathVariable("eventId") String eventId,@PathVariable("summaryId") String summaryId) throws Exception{
		
		model.addAttribute("eventId",eventId);
		String summaryIds="";
		Event event = this.eventService.queryEventDetail(eventId);
		
		if(StringUtils.isBlank(summaryId)){
			Iterator it = event.getSummaries().iterator();
			for(;it.hasNext();){
			    Summary e =  (Summary) it.next();
			    if(e.getType()==2){
			    	summaryIds=e.getSummaryid();
			    	break;
			    }
					
			    
			}
		}
		
		else{
			Iterator its = event.getSummaries().iterator();
			for(;its.hasNext();){
			    Summary e =  (Summary) its.next();
			    if(e.getSummaryid().equals(summaryId)){
					summaryIds=summaryId;
			    }
			    
			}
		}
		model.addAttribute("summaryId", summaryIds);
		model.addAttribute("event", event);
		
		
		 List<TopicVO> topicVoList=new ArrayList<TopicVO>();
		List<RemarkHotVo> remarkHotVOList=hotService.queryByParking();
		for(RemarkHotVo remarkHotVo:remarkHotVOList){
			topicVoList.add(discuzService.getTopicById(remarkHotVo.getHotId()).get(0));
		
		}
		return "/event/past_event_excerpt";
	}
	/**
	 * @param model
	 * @param eventId
	 * @return
	 * @throws Exception
	 * @Date:2013-4-28  
	 * @Author:xuguangyun  
	 * @Description:跳转到活动的视频列表
	 */
	@RequestMapping("/videoList_{eventId}_{summaryid}.html")
	public String redirectVideo(Model model, @PathVariable("eventId") String eventId, @PathVariable("summaryid") String summaryid) throws Exception{
		//加入活动id
		model.addAttribute("eventId",eventId);
		//加入活动本身信息
		Event event = this.eventService.queryEventDetail(eventId);
		model.addAttribute("event", event);
		//加入文章信息
		if(StringUtils.isBlank(summaryid)){
			 for(Summary summary:event.getSummaries()){
				if(summary.getType()==0){
					summaryid=summary.getSummaryid();
					break;
				}
			}
		}
		model.addAttribute("summaryid", summaryid);
		//加入将要开始的活动
		List<Event> comingEvents = eventService.queryRecommendComingEvents();
		model.addAttribute("comingEvents", comingEvents);
		
		return "/event/past_event_video";
	}
	/**
	 * 查询近期或往期活动列表
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @param recent
	 *            0-往期,1-近期
	 * @param cityCode
	 *            0：不限
	 * @param labelId
	 *            0：不限
	 * @param view
	 *            "json"-返回json， else-velocity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/e_{recent}_{cityCode}_{labelId}.html")
	public String queryEvents(Model model, Integer pageNo,
			Integer pageSize, @PathVariable("recent") Integer recent,
			@PathVariable("cityCode") String cityCode,
			@PathVariable("labelId") String labelId, String view)
			throws Exception {
		if (null == pageNo) {
			pageNo = 1;
		}
		if (null == pageSize) {
			//pageSize = Page.DEFAULT_PAGE_SIZE;
			if(recent == 1) {
				pageSize = 8;
			} else {
				pageSize = 10;
			}
		}
		if ("0".equals(cityCode)) cityCode = null;
		if ("0".equals(labelId)) labelId = null;
		Page<EventVO> result = recent == 1 
				? this.eventService.queryEventsPage(pageNo, pageSize, recent, labelId, cityCode, this.getSessionUser()) 
				: this.eventService.queryEventsPage(pageNo, pageSize, recent, labelId, cityCode);
		model.addAttribute("result", result);
		
		String viewName = "json".equals(view) ? "jsonview"
				: recent == 0 ? "event/past_events" 
				: "event/coming_events";
		return viewName;
	}
	
	/**
	 * 查询所有活动（包含近往期）
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @param labelId
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ae_{labelId}.html")
	public String queryAllEvents(Model model, Integer pageNo,
			Integer pageSize, @PathVariable("labelId") String labelId, String view)
			throws Exception {
		if ("0".equals(labelId)) labelId = null;
		Page<EventVO> comings = this.eventService.queryEventsPage(1, 3, 1, labelId, null, this.getSessionUser());
		Page<EventVO> pasts = this.eventService.queryEventsPage(1, 5, 0, labelId, null);
		
		model.addAttribute("comings", comings);
		model.addAttribute("pasts", pasts);
		
		return "event/all_events";
	}

	/**
	 * 查询活动详情（将要开始或往期）
	 * 
	 * @param eventId
	 * @return
	 */
	@RequestMapping("/d_{eventId}.html")
	public String queryComingEventDetail(Model model,
			@PathVariable("eventId") String eventId) {
		Event event = this.eventService.queryEventDetail(eventId);
		model.addAttribute("event", event);
		model.addAttribute("basePath", this.getWebRoot());
		return event.getState() == CommonConstant.STATUS_EVNET_END ? "event/past_detail"
				: "event/coming_detail";
	}

	/**
	 * 查询某时间段是否有活动
	 * 
	 * @return json
	 * @throws ParseException 
	 */
	@RequestMapping("/range.json")
	public String queryEventsInTimeRange(Model model, Date start, Date end, Integer needFormat) throws ParseException {
		List<EventVO> events = this.eventService.queryEventsInTimeRange(start, end);
		if(needFormat == 1) {
			model.addAttribute("result", formatData(events));
		} else {
			model.addAttribute("result", events);
		}
		return "jsonview";
	}

	private List<EventVO> formatData(List<EventVO> events) throws ParseException {
		List<EventVO> result = new ArrayList<EventVO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < events.size(); i++) {
			EventVO vo = events.get(i);
			long start = sdf.parse(sdf.format(vo.getStartTime())).getTime(); 
			long end = sdf.parse(sdf.format(vo.getEndTime())).getTime(); 
			while(start<=end) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(start);
				EventVO e = new EventVO();
				e.setEventId(vo.getEventId());
				e.setStartTime(c.getTime());
				e.setLectrues(vo.getLectrues());
				e.setTopic(vo.getTopic());
				e.setSubTopic(vo.getSubTopic());
				e.setShareman(vo.getShareman());
				result.add(e);
				start += flag;
			}
		}
		return result;
	}

	/**
	 * 查询活动所在的城市列表
	 * 
	 * @return
	 * 
	 */
	@RequestMapping("/citis.json")
	public String queryCitiesOfEvent(Model model) {
		List<Map<String, String>> citis = this.eventService.queryCitis();
		model.addAttribute("result", citis);
		return "jsonview";
	}

	/**
	 * 查询某人已参加的活动
	 * 
	 * @return json
	 * @throws Exception 
	 */
	@RequestMapping("/signUpedEvents.json")
	public String querySignUpedEvents(Model model, String userId) throws Exception {
		model.addAttribute("result", this.eventService.querySignUpedEvents(userId));
		return "jsonview";
	}

	@RequestMapping("/s_{eventId}.json")
	@ResponseBody
	public String signPre() {
		return "{\"success\": true, \"msg\":\"\"}";
	}
	
	/**
	 * 取消报名
	 * @param model
	 * @param eventId
	 * @return
	 */
	@RequestMapping("/us_{eventId}.json")
	public String unsign(Model model, @PathVariable("eventId") String eventId) {
		Result rs = new Result();
		model.addAttribute("result", rs);
		try {
			String userId = this.getSessionUser().getUserid();
			this.userEventService.unsign(userId, eventId);
			rs.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setSuccess(false);
			rs.setMsg(e.getMessage());
		}
		return "jsonview";
	}
	
	/**
	 * 报名 填写的报名资料影响个人资料 产生好友动态 产生个人动态
	 * 
	 * @param model
	 * @param eventId
	 * @throws Exception 
	 */
	@RequestMapping("/s_{eventId}.html")
	public String signUp(Model model, @PathVariable("eventId") String eventId) throws Exception {
	    model.addAttribute("adv",advService.queryByParking("6"));
		if(this.userService.isLimitedUser(this.getSessionUser().getUserid())) {
			return "redirect:/event/d_" + eventId + ".html?f=2";
		}
		User user = this.userService.getUser(this.getSessionUser().getUserid());
		Event event = this.eventService.queryEventDetail(eventId);
		model.addAttribute("user", user);
		model.addAttribute("topic", event.getTopic());
		model.addAttribute("eventId", eventId);
		return "/user/fillup_info";
	}
	
	/**
	 * 报名后自动审核
	 * @param UserEventId
	 * @param signup null
	 * @param audit 1
	 * @return
	 * @Date:2013-6-18  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	private int autoAudit(String UserEventId){
		boolean bool = adminEventService.upUserEvent(UserEventId, null, "1");
		UserEvent ue=adminEventService.queryAudit2(UserEventId);
		User u = userService.getUser(ue.getUser().getUserid());
		Event e = eventService.queryEventDetail(ue.getEvent().getEventid());
		
		ue.setEvent(e);
		Notification notification = new Notification();
		if (bool) {
			// //////////////
			// 发送通知 //
			// //////////////
			int type=1;
			boolean addflag=false;
			try {
				adminEventService.addNotification(type, u.getUserid(), null,new  String[]{e.getEventid(),e.getTopic()});
				addflag=true;
			} catch (Exception ex) {
				// TODO: handle exception
				addflag=false;
			}
			if (addflag){
					try {
						Map<String, String> pairs=null;
						StringBuffer sb=null;
						pairs=new HashMap<String, String>();
						 SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日 HH时:mm分");
				    	
				    	 
						pairs.put("name", u.getName());
						pairs.put("event", e.getTopic());
						//xu 
						pairs.put("place", e.getPlace());
						System.out.println(e);
						pairs.put("eventTime", df.format(e.getStarttime()));
						//xu end
						sb=TemplateHelper.merge("eventpermission.tpl", pairs);
					try
					{
						MailThread mailThread=new MailThread();
						List<String> emailList=new ArrayList<String>();
						emailList.add(u.getPersonalemail());
						mailThread.setEmailList(emailList);
						mailThread.setSubject(CommonConstant.audit_mail_subject);
						mailThread.setContent(sb.toString());
						mailThread.start();
						LogFactory.getLog(MailTask.class).info("提交发送审核通知邮件至用户["+u.getPersonalemail()+"]的邮箱["+u.getPersonalemail()+"]的后台任务。");
					}catch(Exception ex){
						LogFactory.getLog(MailTask.class).error("提交发送审核通知邮件至用户["+u.getPersonalemail()+"]的邮箱["+u.getPersonalemail()+"]的后台任务失败。", ex);
						return 4;
					}
					sb=null;
					pairs=null;
					return 0;
				} catch (Exception exx) {
					// TODO Auto-generated catch block
					exx.printStackTrace();
					log.info("邮件发送失败");
				}
			}else{
				log.info("发送通知失败");
				return 1;
			}
		} else {
			adminEventService.addNotification(2, u.getUserid(), null,new  String[]{e.getEventid(),e.getTopic()});
			return 1;
		}
		return -1;
	}
	
	/**
	 * 保存用户信息，并报名
	 * @param model
	 * @param eventId
	 * @param name
	 * @param company
	 * @param position
	 * @param phone
	 * @param personalemail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fas.html")
	public String fillUpInfoAndSignUp(Model model, String eventId, String topic, String name,
			String company, String position, String phone, String personalemail)
			throws Exception {
		String userId = this.getSessionUser().getUserid();
		this.userService.fillUpPersonalInfo(userId,
				name, company, position, phone, personalemail);
		this.getSessionUser().setName(name);
		boolean success = sign(eventId, topic);
		if(success){
			String userEventId = userEventService.findId(userId, eventId);
			System.out.println("====================================================================userEventId" + userEventId);
			autoAudit(userEventId);
		}
		return "redirect:/event/d_" + eventId + (success ? ".html?f=1" : ".html?f=0");
	}
	
	private boolean sign(String eventId, String topic) throws Exception {
		boolean signed = false;
		try {
			signed = this.userEventService.signUp(this.getSessionUser().getUserid(), eventId);
			// 生成动态
			if(signed) {
				activityService.addPersonActivity(
						this.getSessionUser().getUserid(), topic,
						CommonConstant.PERSONALACTIVITY_EVENT_TYPE, eventId);
				activityService.addFriendActivity(
						this.getSessionUser().getUserid(), topic,
						CommonConstant.FRIENDACTIVITY_EVENT_TYPE, eventId);
			}
		} catch(Exception e) {
			log.error(e);
			return false;
		}
		return signed;
	}

}
