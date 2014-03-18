package com.wanzhu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Label;
import com.wanzhu.jsonvo.LabelVO;

@Repository
public class LabelDao extends BaseDao<Label> {

	public List<LabelVO> queryLabelsWithEventCount(int recent, String cityCode) {
		String hql = "select new com.wanzhu.jsonvo.LabelVO(l.labelid, l.label, count(el)) from Label as l " +
				"left join l.eventLabels as el " +
				"where l.type=0 and el.label.labelid=l.labelid and el.event.visible=1 "+
				(recent==0 ? "and el.event.state=2 " : (recent == 1 ? "and el.event.state<>2 " : "")) +
				(!"0".equals(cityCode) ? "and el.event.citycode=? " : "") +
				"group by l";
		
		if(!"0".equals(cityCode)) {
			return this.getHibernateTemplate().find(hql, cityCode);
		} else 
		return this.getHibernateTemplate().find(hql);
	}

	public List<LabelVO> queryRecommendLables() {
		String hql = "select new com.wanzhu.jsonvo.LabelVO(l.labelid, l.label) from Label l where recommendation=1 order by showorder desc, createtime desc";
		return this.getHibernateTemplate().find(hql);
	}

}
