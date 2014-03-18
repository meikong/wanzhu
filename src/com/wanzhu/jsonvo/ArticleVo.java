package com.wanzhu.jsonvo;


/**
 * 素材缓存VO
 */
public class ArticleVo {

	private String summaryid;
	private String eventid;
	private String summaryTitle;//文章标题
	private Integer type;//2 文章
	private String words;//文章内容
	private String url; //视频链接，文章没有这个属性
	private Integer num; //下载观看量，文章没有这个属性
	private String snapshot; //缩略图，文章没有这个属性
	private Integer downloadcount;//视频下载量，文章没有这个属性
	private String duration;//视频市场，文章没有这个属性
	private Integer videoSource;//视频来源，文章没有这个属性
	public ArticleVo() {
		super();
	}
	public ArticleVo(String summaryid, String eventid, String summaryTitle,
			Integer type, String words) {
		super();
		this.summaryid = summaryid;
		this.eventid = eventid;
		this.summaryTitle = summaryTitle;
		this.type = type;
		this.words = words;
	}
	public ArticleVo(String summaryid, String eventid, Integer type,
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
	public String getSummaryTitle() {
		return summaryTitle;
	}
	public void setSummaryTitle(String summaryTitle) {
		this.summaryTitle = summaryTitle;
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
		return "ArticleVo [summaryid=" + summaryid + ", eventid=" + eventid
				+ ", summaryTitle=" + summaryTitle + ", type=" + type + "]";
	}
	
}
