package com.wanzhu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Code;
import com.wanzhu.entity.Hot;
import com.wanzhu.entity.Recommendation;

@Repository
public class RecommendationDao extends BaseDao<Recommendation> {

	public Object[] getRecommendation(Integer type, Integer isable){
		return null;
	}
}
