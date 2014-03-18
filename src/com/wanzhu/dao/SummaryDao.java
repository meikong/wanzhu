package com.wanzhu.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Summary;
import com.wanzhu.jsonvo.ArticleVo;
import com.wanzhu.jsonvo.VideoVo;

@Repository
public class SummaryDao extends BaseDao<Summary> {
	
	public void downloadCount(String summaryId) {
		String hql="update Summary set downloadcount=downloadcount+1 where summaryid='"+summaryId+"'";
		this.execute(hql);//为避免并发冲突，下载计数最好的办法是调用BaseDao的execute方法，而不是update。
	}
	/**
	 * 
	 * @param summaryId
	 * @return
	 * @Date:2013-4-12  
	 * @Author:xuguangyun  
	 * @Description:
	 */
	public Integer getDownloadCount(String summaryId) {
		return this.get(summaryId).getDownloadcount();
	}
	/**
	 * @param id
	 * @return
	 * @Date:2013-5-6  
	 * @Author:xuguangyun  
	 * @Description:根据文章ID活动文章的名字
	 */
	public Summary getSummaryById(String id){
		String hql=" from  Summary where summaryid='"+id+"'";
	
		return this.find(hql).get(0);
	}
	/**
	 * 
	 * @return
	 * @Date:2013-4-12  
	 * @Author:xuguangyun  
	 * @Description: 获得素材文章的标题
	 */
	public List<Summary> getSummaryuTitle(){
		String hql=" from  Summary";
		List<Summary> summarys=new ArrayList<Summary> ();
		return this.find(hql).subList(1, 2);
	}
	
	
	public List<ArticleVo> queryRecommendArticle(int max) {
		List<Summary> articles = this.find("from Summary where type=2 and recommendation=1 order by showorder desc");
		//截取
		if(articles == null || articles.size() == 0)
			return new ArrayList<ArticleVo>(0);
		else if(articles.size() <= max)
			return convertSummaryToArticleVo(articles);
		else{
			return convertSummaryToArticleVo(new ArrayList<Summary>(articles.subList(0, max)));
		}
	}

	public List<VideoVo> queryRecommendVideo(int max) {
		List<Summary> videos = this.find("from Summary where type=0 and recommendation=1 order by showorder desc");
		//截取
		if(videos == null || videos.size() == 0)
			return new ArrayList<VideoVo>(0);
		else if(videos.size() <= max)
			return convertSummaryToVideoVo(videos);
		else{
			return convertSummaryToVideoVo(new ArrayList<Summary>(videos.subList(0, max)));
		}
	}
	
	private List<ArticleVo> convertSummaryToArticleVo(List<Summary> articles){
		List<ArticleVo> articleVos = new ArrayList<ArticleVo>();
		for (Summary summary : articles) {
			ArticleVo vo = new ArticleVo(summary.getSummaryid(), summary.getEvent().getEventid(), summary.getSummaryTitle(), summary.getType(), summary.getWords());
			articleVos.add(vo);
		}
		return articleVos;
	}
	
	private List<VideoVo> convertSummaryToVideoVo(List<Summary> videos){
		List<VideoVo> videoVos = new ArrayList<VideoVo>();
		for (Summary summary : videos) {
			VideoVo vo = new VideoVo(summary.getSummaryid(), summary.getEvent().getEventid(), summary.getEvent().getTopic(), summary.getType(), summary.getWords(), summary.getUrl(), summary.getNum(), summary.getSnapshot(), summary.getDownloadcount(), summary.getDuration().toString(), summary.getVideoSource());
			videoVos.add(vo);
		}
		return videoVos;
	}
}
