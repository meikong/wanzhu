package com.wanzhu.service.admin;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.Page;
import com.wanzhu.dao.admin.AdminLabelsDao;
import com.wanzhu.entity.EventLabel;
import com.wanzhu.entity.Label;
import com.wanzhu.utils.showorder.Order;

@Service
@Transactional(readOnly=true)
public class AdminLabelsService {
	
	@Autowired
	private AdminLabelsDao adminLabelsDao;
	
	
	public Page<Label> listLabelPage(Integer pageNo, Integer pageSize, String isRecommend, String content) throws Exception {
		return this.adminLabelsDao.listLabelPage(pageNo, pageSize, isRecommend,content);
	}
	
	public Label getLabel(String labelid) {
		return this.adminLabelsDao.get(labelid);
	}
	
	@Transactional(readOnly=false)
	public void saveLabel(Label label) {
		this.adminLabelsDao.save(label);
	}
	
	@Transactional(readOnly=false)
	public void updateLabel(Label label) {
		this.adminLabelsDao.update(label);
	}
	
	
	public String getLabelEventCount(String labelId) {
		Label label = this.adminLabelsDao.get(labelId);
		Set<EventLabel> set = label.getEventLabels();
		return set.size() + "";
	}
	
	@Transactional(readOnly=false)
	public void deleteLabel(String labelId) {
		Label label = this.adminLabelsDao.get(labelId);
		
		Set<EventLabel> set = label.getEventLabels();
		Iterator<EventLabel> iter = set.iterator();
		while(iter.hasNext()) {
			EventLabel eventLabel = iter.next();
			this.adminLabelsDao.getSession().delete(eventLabel);
		}
		
		this.adminLabelsDao.remove(label);
	}

	/**
	 * 保存当页标签排序
	 * @param orders
	 * @return
	 * @Date:2013-4-27  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@Transactional(readOnly=false)
	public boolean saveOrder(List<Order> orders) {
		return adminLabelsDao.saveOrder(orders);
	}

	/**
	 * 跨页保存排序
	 * @param id 当前记录id
	 * @param kind 1--向上跨页 2--向下跨页
	 * @Date:2013-4-27  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	@Transactional(readOnly=false)
	public boolean crossPageSaveOrder(String id, Integer kind) {
		return adminLabelsDao.crossPageSaveOrder(id, kind);
	}

	/**
	 * 标签: 推荐/收起推荐
	 * @param labelid
	 * @param kind 0--收起推荐  1--推荐
	 * @return
	 * @Date:2013-4-28  
	 * @Author:Guibin Zhang   
	 * @Description:
	 */
	@Transactional(readOnly=false)
	public boolean recommendLabelById(String labelid, int kind) {
		return adminLabelsDao.recommendLabelById(labelid, kind);
	}

	public int queryRecommendationLabelCount() {
		return adminLabelsDao.queryRecommendationLabelCount();
	}

	@Transactional(readOnly=false)
	public void upgradeLabel(String labelId) {
		 Label label = adminLabelsDao.get(labelId);
		 label.setType(0);//0--系统标签  1--用户标签
		 adminLabelsDao.save(label);
	}

}
