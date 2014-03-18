package com.wanzhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.dao.SummaryDao;
import com.wanzhu.entity.Summary;

@Service
@Transactional(readOnly=true)
public class SummaryService {
	
	@Autowired
	SummaryDao summaryDao;
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void downloadCount(String summaryId) {
		this.summaryDao.downloadCount(summaryId);
	}
	
	public Integer getDownloadCount(String summaryId) {
		return this.summaryDao.getDownloadCount(summaryId);
	}
	/**
	 * 
	 * @return
	 * @Date:2013-4-12  
	 * @Author:xuguangyun  
	 * @Description:获得素材文章的标题列表
	 */
	public List<Summary> getSummaryList(){
		return this.summaryDao.getSummaryuTitle();
	}
	public Summary getSummaryById(String Id){
		return this.summaryDao.getSummaryById(Id);
	}
}
