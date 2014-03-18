package com.wanzhu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Summary entity. 
 */
@Entity
@Table(name = "t_summary")
public class Summary implements java.io.Serializable {
	private static final long serialVersionUID = 5165789415627431921L;
	
	private String summaryid;
	private Event event;
	private Integer type;
	private String words;
	private String url;
	private Integer num;
	private String snapshot;
	private Integer downloadcount;
	private Integer duration;
	private Integer videoSource;
	private Integer isShow;
	//xu 添加文字素材标题
	private String summaryTitle;
	private Integer recommendation;
	private long showorder;
	
	public Summary() {
		this.showorder = System.currentTimeMillis();
	}

	@Column(name = "recommendation", nullable = false)
	public Integer getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Integer recommendation) {
		this.recommendation = recommendation;
	}

	@Column(name = "showorder", nullable = false)
	public long getShoworder() {
		return showorder;
	}

	public void setShoworder(long showorder) {
		this.showorder = showorder;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "summaryid", unique = true, nullable = false, length = 32)
	public String getSummaryid() {
		return this.summaryid;
	}

	public void setSummaryid(String summaryid) {
		this.summaryid = summaryid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventid")
	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "words", nullable = false)
	public String getWords() {
		return this.words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	@Column(name = "url", length = 128)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "downloadcount")
	public Integer getDownloadcount() {
		return this.downloadcount;
	}

	public void setDownloadcount(Integer downloadcount) {
		this.downloadcount = downloadcount;
	}
	
	@Column(name = "duration")
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "snapshot", length = 128)
	public String getSnapshot() {
		return this.snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	@Column(name = "videosource")
	public Integer getVideoSource() {
		return videoSource;
	}

	public void setVideoSource(Integer videoSource) {
		this.videoSource = videoSource;
	}
	@Column(name = "summarytitle")
	public String getSummaryTitle() {
		return summaryTitle;
	}
	@Transient
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public void setSummaryTitle(String summaryTitle) {
		this.summaryTitle = summaryTitle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((summaryid == null) ? 0 : summaryid.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Summary other = (Summary) obj;
		if (summaryid == null) {
			if (other.summaryid != null)
				return false;
		} else if (!summaryid.equals(other.summaryid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Summary [summaryid=" + summaryid + ", event=" + event
				+ ", type=" + type + ", words=" + words + ", summaryTitle="
				+ summaryTitle + ", recommendation=" + recommendation
				+ ", showorder=" + showorder + "]";
	}
	
	
	
}