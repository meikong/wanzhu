package com.wanzhu.service.promotion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanzhu.base.CommonConstant;
import com.wanzhu.dao.promotion.PromotionDao;
import com.wanzhu.entity.promotion.Promotion;

@Service
public class PromotionService{
	@Autowired
	private PromotionDao promotionDao;
	
	/**
	 * 得到首页幻灯片列表
	 * @param topSize
	 */
	public List<Promotion> getTopPromotions(int topSize){
		List<Promotion> list = this.promotionDao.find("from Promotion where isEnable=1 order by showorder desc");
		if(list == null || list.size() == 0){
			return new ArrayList<Promotion>(0);
		}else if(list.size() > topSize){
			return list.subList(0, topSize);
		}else{
			return list;
		}
	}
	
	/**
	 * 编辑首页幻灯片列表
	 * @param p
	 */
	public boolean editPromotions(Promotion p){
		if(p == null)
			return false;
		if(p.getId() == null){
			
			
		}else{
			
			
		}
		
		return false;
	}
	
	/**
	 * 编辑首页幻灯片列表
	 * @param p
	 */
	public boolean deletePromotions(Integer id){
		Promotion p = this.promotionDao.get(id);
		if(p == null)
			return false;
		p.setIsEnable(CommonConstant.DELETED);
		this.promotionDao.save(p);
		return true;
	}
}
