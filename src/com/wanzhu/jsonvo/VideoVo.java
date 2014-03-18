package com.wanzhu.jsonvo;


/**
 * 素材缓存VO
 */
public class VideoVo {

	private String summaryid;
	private String eventid;
	private String eventTopic;//活动名
	private Integer type;//种类 0 视频
	private String words;//文字
	private String url;//视频链接
	private Integer num;//点击量
	private String snapshot; //视频缩略图
	private Integer downloadcount;//下载观看量
	private String duration;//时长
	private Integer videoSource;//视频来源
	public VideoVo() {
		super();
	}
	
	public VideoVo(String summaryid, String eventid, String eventTopic,
			Integer type, String words, String url, Integer num,
			String snapshot, Integer downloadcount, String duration,
			Integer videoSource) {
		super();
		this.summaryid = summaryid;
		this.eventid = eventid;
		this.eventTopic = eventTopic;
		this.type = type;
		this.words = words;
		this.url = url;
		this.num = num;
		this.snapshot = snapshot;
		this.downloadcount = downloadcount;
		this.duration = duration;
		this.videoSource = videoSource;
	}
	public VideoVo(String summaryid, String eventid, Integer type,
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
	public String getEventTopic() {
		return eventTopic;
	}
	public void setEventTopic(String eventTopic) {
		this.eventTopic = eventTopic;
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

	@Override
	public String toString() {
		return "VideoVo [summaryid=" + summaryid + ", eventid=" + eventid
				+ ", eventTopic=" + eventTopic + ", type=" + type + ", words="
				+ words + ", url=" + url + ", num=" + num + ", snapshot="
				+ snapshot + ", downloadcount=" + downloadcount + ", duration="
				+ duration + ", videoSource=" + videoSource + "]";
	}
	
}
