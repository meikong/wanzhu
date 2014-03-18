package com.wanzhu.dao.admin;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.Label;
import com.wanzhu.entity.Summary;
import com.wanzhu.utils.StringUtils;

@Repository
public class AdminLabelsDao extends BaseDao<Label> {
	
	
	public Page<Label> listLabelPage(Integer pageNo, Integer pageSize, String recommendation, String content) throws Exception {
		Criteria cri = this.createCriteria();
		if(!StringUtils.isEmpty(content)) {
			cri.add(Restrictions.like("label", "%" + content + "%"));
		}
		if (recommendation != null) {
			Integer sta = Integer.parseInt(recommendation);
			cri.add(Restrictions.eq("recommendation", sta));
		}
    	return this.pagedQuery(cri, pageNo, pageSize, Order.desc("recommendation"), Order.desc("showorder"), Order.desc("createtime"));
	}
	

	public boolean saveLabel(Label label) {
		try {
			this.save(label);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public List<Label> listLabel() {
		String hql="from Label where type=0";
		return this.find(hql);
	}
	
	public int listLabelWhere(String label) {
		String hql="from Label where label=?";
		return this.find(hql,new Object[]{label}).size();
	}

	/**
	 * 保存当页排序
	 * @param orders
	 * @return
	 * @Date:2013-4-27  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public boolean saveOrder(List<com.wanzhu.utils.showorder.Order> orders) {
		int result = 0;
		for (com.wanzhu.utils.showorder.Order order : orders) {
			result += this.execute("update Label set showorder=" + order.order + " where labelid='" + order.id + "'");
		}
		return result > 0 ? true : false;
	}

	/**
	 * 保存跨页排序
	 * @param id
	 * @param kind 1--向上跨页 2--向下跨页
	 * @return
	 * @Date:2013-4-27  
	 * @Author:Guibin Zhang  
	 * @Description:
	 */
	public boolean crossPageSaveOrder(String id, Integer kind) {
		Label label = this.get(id);
		String sql = "";
		if (kind == 2) {// 向下跨页
			sql = "select max(showorder),cast(labelid as char(32)) from t_label where type=" + label.getType()
					+ " and showorder=(select max(showorder) from t_label where showorder<" + label.getShoworder() + ")";
		} else if (kind == 1) {// 向上跨页
			sql = "select min(showorder),cast(labelid as char(32)) from t_label where type=" + label.getType()
					+ " and showorder=(select min(showorder) from t_label where showorder>" + label.getShoworder() + ")";
		} else
			return false;
		// System.out.println("sql ---> " + sql);
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list == null || list.size() == 0)
			return false;
		Object[] os = list.get(0);
		long anotherShoworder = Long.parseLong(String.valueOf(os[0]));
		String anotherId = String.valueOf(os[1]);
		// 上条或下条更新
		Label l = this.get(anotherId);
		l.setShoworder(label.getShoworder());
		label.setShoworder(anotherShoworder);
		this.getHibernateTemplate().merge(l);
		this.getHibernateTemplate().merge(label);
		return true;
	}

	
	public boolean recommendLabelById(String labelid, int recommend) {
		int count = this.execute("update Label set recommendation="
				+ recommend + " where labelid='" + labelid + "'");
		return count > 0 ? true : false;
	}


	public int queryRecommendationLabelCount() {
		List<Label> labels = this.find("from Label where recommendation=1");
		if(labels == null)
			return 0;
		else
			return labels.size();
	}
}
