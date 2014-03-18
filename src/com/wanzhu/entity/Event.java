package com.wanzhu.entity;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Event entity. 
 */
@Entity
@Table(name = "t_event")
public class Event implements java.io.Serializable,Comparator<Event> {
	private static final long serialVersionUID = -9125032821834206839L;
	//xu
	private String shareman;
	//xu end
	private String eventid;
	private String topic;
	private String subtopic;
	private String citycode;
	private String place;
	@Temporal(TemporalType.TIMESTAMP)
	private Date starttime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endtime;
	private Integer wholeday;
	private BigDecimal expenseonline;
	private BigDecimal expenseoffline;
	private Integer quota;
	private Integer recommendation; //是否推荐 （0-不推荐；1-推荐）
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastmodifytime;
	private String detail;
	private Integer state;
	private Integer visible;
	private String memo;
	private Set<Summary> summaries = new HashSet<Summary>(0);
	private Set<EventLabel> eventLabels = new HashSet<EventLabel>(0);
	private Set<Topic> topics = new HashSet<Topic>(0);
	private Set<UserEvent> userEvents = new HashSet<UserEvent>(0);
	private Set<Lectrue> lectrues = new HashSet<Lectrue>(0);
	
	private Integer applyCount;
	private Integer signCount;
	private Long showorder;
	
	
//	private Set<Label> labels = new HashSet<Label>();
//	private Set<User> lectrues = new HashSet<User>(0);
//	private Set<User> applicants = new HashSet<User>();
	
	
	public Event() {
		//this.showorder = System.currentTimeMillis();
	}
	
	public Event(String eventid) {
		this.eventid = eventid;
	}
 	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "eventid", unique = true, nullable = false, length = 32)
	public String getEventid() {
		return this.eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	@Column(name = "topic", nullable = false, length = 128)
	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Column(name = "subtopic", length = 128)
	public String getSubtopic() {
		return this.subtopic;
	}

	public void setSubtopic(String subtopic) {
		this.subtopic = subtopic;
	}

	@Column(name = "citycode", nullable = false, length = 64)
	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	@Column(name = "place", nullable = false, length = 128)
	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name = "starttime", length = 29)
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Column(name = "endtime", length = 29)
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "wholeday", nullable = false)
	public Integer getWholeday() {
		return this.wholeday;
	}

	public void setWholeday(Integer wholeday) {
		this.wholeday = wholeday;
	}

	@Column(name = "expenseonline", nullable = false, precision = 131089, scale = 0)
	public BigDecimal getExpenseonline() {
		return this.expenseonline;
	}

	public void setExpenseonline(BigDecimal expenseonline) {
		this.expenseonline = expenseonline;
	}

	@Column(name = "expenseoffline",  precision = 131089, scale = 0)
	public BigDecimal getExpenseoffline() {
		return this.expenseoffline;
	}

	public void setExpenseoffline(BigDecimal expenseoffline) {
		this.expenseoffline = expenseoffline;
	}

	@Column(name = "quota", nullable = false)
	public Integer getQuota() {
		return this.quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}

	@Column(name = "recommendation", nullable = false)
	public Integer getRecommendation() {
		return this.recommendation;
	}

	public void setRecommendation(Integer recommendation) {
		this.recommendation = recommendation;
	}

	@Column(name = "createtime", nullable = false, length = 29)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "lastmodifytime", nullable = false, length = 29)
	public Date getLastmodifytime() {
		return this.lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}

	@Column(name = "detail", nullable = false)
	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Column(name = "state", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Column(name = "visible", nullable = false)
	public Integer getVisible() {
		return this.visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "event")
	@OrderBy(value="num asc")
	public Set<Summary> getSummaries() {
		return this.summaries;
	}

	public void setSummaries(Set<Summary> summaries) {
		this.summaries = summaries;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "event")
	@OrderBy(value="assignid desc")
	public Set<EventLabel> getEventLabels() {
		return this.eventLabels;
	}

	public void setEventLabels(Set<EventLabel> eventLabels) {
		this.eventLabels = eventLabels;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "event")
	public Set<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "event")
	public Set<UserEvent> getUserEvents() {
		return this.userEvents;
	}

	public void setUserEvents(Set<UserEvent> userEvents) {
		this.userEvents = userEvents;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "event")
	@OrderBy(value="user desc")
	public Set<Lectrue> getLectrues() {
		return this.lectrues;
	}

	public void setLectrues(Set<Lectrue> lectrues) {
		this.lectrues = lectrues;
	}

	@Transient   
	public Integer getApplyCount() {
		return applyCount;
	}
	
	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	@Transient
	public Integer getSignCount() {
		return signCount;
	}

	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}
	@Column(name = "shareman")
	public String getShareman() {
		return shareman;
	}

	public void setShareman(String shareman) {
		this.shareman = shareman;
	}
	@Column(name = "showorder", nullable = false)
	public Long getShoworder() {
		return showorder;
	}

	public void setShoworder(Long showorder) {
		this.showorder = showorder;
	}

	@Override
	public int compare(Event o1, Event o2) {
		if(o1.getRecommendation() > o2.getRecommendation())
			return 1;
		if(o1.getRecommendation() < o2.getRecommendation())
			return -1;
		if(o1.getShoworder() > o2.getShoworder())
			return 1;
		if(o1.getShoworder() < o2.getShoworder())
			return -1;
		return o1.getStarttime().compareTo(o2.getStarttime());
	}

	@Override
	public String toString() {
		return "Event [topic=" + topic + ", showorder=" + showorder + "]";
	}
	
	

}