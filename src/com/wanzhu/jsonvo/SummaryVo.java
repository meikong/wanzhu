package com.wanzhu.jsonvo;

import com.wanzhu.entity.Event;

/**
 * 素材缓存VO
 */
public class SummaryVo {

	private String summaryid;
	private String eventid;
	private Integer type;
	private String words;
	private String url;
	private Integer num;
	private String snapshot;
	private Integer downloadcount;
	private String duration;
	private Integer videoSource;
	public SummaryVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SummaryVo(String summaryid, String eventid, Integer type,
			String words, String url, Integer num, String snapshot,
			Integer downloadcount, String duration) {
		super();
		this.summaryid = summaryid;
		this.eventid = eventid;
		this.type = type;
		this.words = words;
		this.url = url;
		this.num = num;
		this.snapshot = snapshot;
		this.downloadcount = downloadcount;
		this.duration = duration;
	}
	public String getSummaryid() {
		return summaryid;
	}
	public void setSummaryid(String summaryid) {
		this.summaryid = summaryid;
	}
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getSnapshot() {
		return snapshot;
	}
	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}
	public Integer getDownloadcount() {
		return downloadcount;
	}
	public void setDownloadcount(Integer downloadcount) {
		this.downloadcount = downloadcount;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Integer getVideoSource() {
		return videoSource;
	}
	public void setVideoSource(Integer videoSource) {
		this.videoSource = videoSource;
	}
	
}
