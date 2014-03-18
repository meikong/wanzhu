package com.wanzhu.jsonvo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wanzhu.base.CommonConstant;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.EventLabel;
import com.wanzhu.entity.Lectrue;
import com.wanzhu.entity.Summary;
import com.wanzhu.entity.User;
import com.wanzhu.entity.UserEvent;
import com.wanzhu.utils.JsonDateSerializer;

public class EventVO {
	private String eventId;
	private String topic;
	private String place;
	private String cityCode;
	private Integer state;
	//xu
    private String shareman;
    //xu end
	private Date startTime;
	private Date endTime;
	
	private Integer wholeday;
	private String subTopic;
	private BigDecimal expenseOnline;
	private BigDecimal expenseOffline;
	private Integer quota;
	private String detail;
	private Integer applyCount;
	private boolean hasSignuped;
	private boolean audit;
	
	private boolean hasVideo;
	private boolean hasPPT;
	private boolean hasAbstract;
	private boolean hasPoster;
	
	/**
	 * 海报url
	 */
	private String posterUrl = "";
	

	private List<LabelVO> labels;
	private List<UserVO> lectrues;
	
	public EventVO() {};
	
	public EventVO(String eventId, String topic, Integer state) {
		this.eventId = eventId;
		this.topic = topic;
		this.state = state;
	}

	public static List<EventVO> convertFromEntitis(List events, boolean needLabels, boolean needLectrues, User user) {
		List<EventVO> vos = new ArrayList<EventVO>();
		for (int i = 0; i < events.size(); i++) {
			Object eventObj = events.get(i);
			if(eventObj instanceof Object[]) {
				Object[] objs = (Object[]) events.get(i);
				for (int j = 0; j < objs.length; j++) {
					if(objs[j] instanceof Event) {
						vos.add(convertFromEntity((Event) objs[j], needLabels, needLectrues, user));
					}
				}
			} else if(eventObj instanceof Event) {
				vos.add(convertFromEntity((Event) events.get(i), needLabels, needLectrues, user));
			}
		}
		return vos;
	}
	
	public static EventVO convertFromEntity(Event event, boolean needLabels, boolean needLectrues, User user) {
		EventVO vo = new EventVO();
		vo.setEventId(event.getEventid());
		vo.setCityCode(event.getCitycode());
		vo.setPlace(event.getPlace());
		vo.setShareman(event.getShareman());
		vo.setStartTime(event.getStarttime());
		vo.setEndTime(event.getEndtime());
		vo.setTopic(event.getTopic());
		vo.setState(event.getState());
		vo.setWholeday(event.getWholeday());
		vo.setSubTopic(event.getSubtopic());
		vo.setExpenseOnline(event.getExpenseonline());
		vo.setExpenseOffline(event.getExpenseoffline());
		vo.setQuota(event.getQuota());
		vo.setDetail(event.getDetail());
		vo.setApplyCount(event.getApplyCount());
		
		Set<Summary> summaries = event.getSummaries();
		Iterator<Summary> sIt = summaries.iterator();
		while(sIt.hasNext()) {
			Summary s = sIt.next();
			if(!vo.hasPoster && s.getType() == CommonConstant.SUMMARY_OF_POSTER) {
				vo.setPosterUrl(s.getSnapshot());
				vo.setHasPoster(true);
			}
			if(!vo.hasAbstract && s.getType() == CommonConstant.SUMMARY_OF_ABSTRCT) {
				vo.setHasAbstract(true);
			}
			if(!vo.hasPPT && s.getType() == CommonConstant.SUMMARY_OF_PPT) {
				vo.setHasPPT(true);
			}
			if(!vo.hasVideo && s.getType() == CommonConstant.SUMMARY_OF_VIDEO) {
				vo.setHasVideo(true);
			}
		}
		
		if(null != user) {
			Set<UserEvent>  userEvents = event.getUserEvents();
			Iterator<UserEvent> ueIt = userEvents.iterator();
			while(ueIt.hasNext()) {
				UserEvent ue = ueIt.next();
				if(ue.getUser().getUserid().equals(user.getUserid())) {
					vo.setHasSignuped(true);
					if(ue.getAudit()==1) vo.setAudit(true);
					break;
				}
			}
		}
		
		if(needLabels) {
			List<LabelVO> labelVOs = new ArrayList<LabelVO>();
			vo.setLabels(labelVOs);
			Iterator<EventLabel> it = event.getEventLabels().iterator();
			while (it.hasNext()) {
				labelVOs.add(LabelVO.convertFromEntity(it.next().getLabel()));
			}
		}

		if(needLectrues) {
			List<UserVO> lectrues = new ArrayList<UserVO>();
			vo.setLectrues(lectrues);
			Iterator<Lectrue> lit = event.getLectrues().iterator();
			while (lit.hasNext()) {
				lectrues.add(UserVO.convertFromEntity(lit.next().getUser()));
			}
		}
		return vo;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public List<UserVO> getLectrues() {
		return lectrues;
	}

	public void setLectrues(List<UserVO> lectrues) {
		this.lectrues = lectrues;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public List<LabelVO> getLabels() {
		return labels;
	}

	public void setLabels(List<LabelVO> labels) {
		this.labels = labels;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Integer getWholeday() {
		return wholeday;
	}

	public void setWholeday(Integer wholeday) {
		this.wholeday = wholeday;
	}

	public String getSubTopic() {
		return subTopic;
	}

	public void setSubTopic(String subTopic) {
		this.subTopic = subTopic;
	}

	public BigDecimal getExpenseOnline() {
		return expenseOnline;
	}

	public void setExpenseOnline(BigDecimal expenseOnline) {
		this.expenseOnline = expenseOnline;
	}

	public BigDecimal getExpenseOffline() {
		return expenseOffline;
	}

	public void setExpenseOffline(BigDecimal expenseOffline) {
		this.expenseOffline = expenseOffline;
	}

	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	public boolean isHasSignuped() {
		return hasSignuped;
	}

	public void setHasSignuped(boolean hasSignuped) {
		this.hasSignuped = hasSignuped;
	}

	public static List<EventVO> convertFromEntitis(
			List<Event> queryEventsInTimeRange, boolean needLabels, boolean needLectures) {
		return convertFromEntitis(queryEventsInTimeRange, needLabels, needLectures, null);
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public boolean isHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
	}

	public boolean isHasPPT() {
		return hasPPT;
	}

	public void setHasPPT(boolean hasPPT) {
		this.hasPPT = hasPPT;
	}

	public boolean isHasAbstract() {
		return hasAbstract;
	}

	public void setHasAbstract(boolean hasAbstract) {
		this.hasAbstract = hasAbstract;
	}

	public boolean isHasPoster() {
		return hasPoster;
	}

	public void setHasPoster(boolean hasPoster) {
		this.hasPoster = hasPoster;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	public String getShareman() {
		return shareman;
	}

	public void setShareman(String shareman) {
		this.shareman = shareman;
	}

}
