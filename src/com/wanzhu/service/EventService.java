package com.wanzhu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.Page;
import com.wanzhu.base.SystemBuffer;
import com.wanzhu.dao.EventDao;
import com.wanzhu.dao.SummaryDao;
import com.wanzhu.entity.Code;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.User;
import com.wanzhu.entity.UserEvent;
import com.wanzhu.jsonvo.ArticleVo;
import com.wanzhu.jsonvo.EventVO;
import com.wanzhu.jsonvo.VideoVo;

@Service
@Transactional(readOnly=true)
public class EventService {
	
	public static final int TYPE_RECOMMEND_VIDEO = 0;
	public static final int MAX_RECOMMEND_VIDEO_COUNT = 8;
	public static final int TYPE_RECOMMEND_ARTICLE = 2;
	public static final int MAX_RECOMMEND_ARTICLE_COUNT = 6;
	
	@Autowired
	private EventDao eventDao;
	@Autowired
	private SummaryDao summaryDao;
	
	/**
	 * @param pageNo 查第几页
	 * @param pageSize 每页数据
	 * @param recent 0-往期，1-近期
	 * @param labelId 活动标签
	 * @param cityCode 活动举办城市
	 * @param user 已登录的用户（查得该用户是否已报名某活动）
	 * @return
	 * @throws Exception 
	 */
	public Page<EventVO> queryEventsPage(int pageNo, int pageSize, int recent, String labelId, String cityCode, User user) throws Exception {
		Page<Event> page = eventDao.queryEventsPage(pageNo, pageSize, recent, labelId, cityCode);
		Page<EventVO> result = new Page<EventVO>(page.getStart(), page.getTotalCount(), page.getPageSize(), null);
		result.setResult(EventVO.convertFromEntitis(page.getResult(), false, true, user));
		return result; 
	}
	
	/**
	 * @param pageNo 查第几页
	 * @param pageSize 每页数据
	 * @param recent 0-往期，1-近期
	 * @param labelId 活动标签
	 * @param cityCode 活动举办城市
	 * @return
	 * @throws Exception
	 */
	public Page<EventVO> queryEventsPage(int pageNo, int pageSize, int recent, String labelId, String cityCode) throws Exception {
		return this.queryEventsPage(pageNo, pageSize, recent, labelId, cityCode, null);
	}
	
	/**
	 * 查询将要开始的热门推荐活动
	 * @return
	 */
	public List<Event> queryRecommendComingEvents() {
//		List<EventVO> eventVOs = new ArrayList<EventVO>();
//		List<Event> events = eventDao.queryRecommendComingEvents();
//		for (int i = 0; i < events.size(); i++) {
//			eventVOs.add(EventVO.convertFromEntity(events.get(i)));
//		}
		return eventDao.queryRecommendComingEvents();
	}
	
	/**
	 * 查询推荐文章
	 * @return
	 * @Date:2013-4-24  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public List<ArticleVo> queryRecommendArticle() {
		return summaryDao.queryRecommendArticle(MAX_RECOMMEND_ARTICLE_COUNT);
	}
	
	/**
	 * 查询推荐视频
	 * @return
	 * @Date:2013-4-24  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public List<VideoVo> queryRecommendVideo() {
		return summaryDao.queryRecommendVideo(MAX_RECOMMEND_VIDEO_COUNT);
	}
	
	/**
	 * 查询往期精彩推荐
	 * @return
	 */
	public List<Event> queryRecommendPastEvents() {
		return eventDao.queryRecommendPastEvents();
	}
	
	public Event queryEventDetail(String eventId) {	
		return eventDao.findById(eventId);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void createEvent(Event event) {
		eventDao.save(event);
	}

	/**
	 * 查询某时间段内的活动列表（日历用）
	 * @param start 开始时间
	 * @param end 结束时间 
	 * @return
	 */
	public List<EventVO> queryEventsInTimeRange(Date start, Date end) {
		return EventVO.convertFromEntitis(this.eventDao.queryEventsInTimeRange(start, end), false, true);
	}

	public List<Map<String, String>> queryCitis() {
		List<Map<String, String>> codeMaps = new ArrayList<Map<String, String>>();
		List<String> cityCodes = this.eventDao.queryCitis();
		for (int j = 0; j < cityCodes.size(); j++) {
			String cityCode = cityCodes.get(j);
			for (int i = 0; i < SystemBuffer.areaCode.size(); i++) {
				Code code = SystemBuffer.areaCode.get(i);
				if(code.getCode().equals(cityCode)) {
					Map<String, String> codeMap = new HashMap<String, String>();
					codeMap.put("code", code.getCode());
					codeMap.put("value", code.getValue());
					codeMaps.add(codeMap);
					continue;
				}
			}
		}
		return codeMaps;
	}

	public List<EventVO> querySignUpedEvents(String userId) {
		return this.eventDao.querySignUpedEvents(userId);
	}

	
}
