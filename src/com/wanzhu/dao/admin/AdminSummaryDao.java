package com.wanzhu.dao.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.Summary;
import com.wanzhu.utils.StringUtils;

@Repository
public class AdminSummaryDao extends BaseDao<Summary> {

	public List<Summary> listSummary(String eventId) {
		return this.find("from Summary where event.eventid=?",
				new Object[] { eventId });
	}

	public List<Summary> listSummaryType(String eventId) {
		return this.find("from Summary where event.eventid=? and type=?",
				new Object[] { eventId, 2 });
	}

	public Summary getSummary(String id) {
		if (id == null) {
			return null;
		}
		return this.get(id);
	}

	public boolean saveSummary(Summary summary) {
		try {
			if (summary.getSummaryid() != null) {
				this.update(summary);
			} else {
				this.save(summary);
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean updateSummary(Summary summary) {
		try {

			// if(s.getShoworder() == 0)
			// s.setShoworder(System.currentTimeMillis());
			// summary.setShoworder(s.getShoworder());
			int result = this.execute("update Summary set words='" + summary.getWords() + "', summaryTitle='" + summary.getSummaryTitle() + "' where summaryid='" + summary.getSummaryid() + "'");
//			boolean bool = this.deleteSummary(summary.getSummaryid());
//			if (bool)
//				this.save(summary);
			return result>0?true:false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean updateSummaryEvent(String id, String eventid) {
		try {
			Summary summary = getSummary(id);
			Event event = new Event();
			event.setEventid(eventid);
			summary.setEvent(event);
			this.update(summary);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean deleteSummary(String summaryId) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("summaryid", summaryId);
			this.delete(map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean saveOrder(List<com.wanzhu.utils.showorder.Order> orders) {
		int result = 0;
		for (com.wanzhu.utils.showorder.Order order : orders) {
			result += this.execute("update Summary set showorder="
					+ order.order + " where summaryid='" + order.id + "'");
			// System.out.println("Summary ----id为： " + order.id +
			// " 的 记录 showorder 变为 :  " + order.order);
		}
		return result > 0 ? true : false;
	}

	public int queryRecommendationSummaryCount(int type) {
		List<Summary> summarys = this.find("from Summary where type=" + type
				+ " and recommendation=1");
		if (summarys == null)
			return 0;
		else
			return summarys.size();
	}

	public Page<Summary> querySummaryPage(int type, int pageNo, int pageSize, String recommendation, String content) throws Exception {
		Criteria cri = this.createCriteria();
		if (!StringUtils.isEmpty(content) && !"".equals(content)) {
			cri.add(Restrictions.or(Restrictions.like("summaryTitle", "%" + content + "%"), Restrictions.like("words", "%" + content + "%")));
		}
		if (recommendation != null) {
			Integer sta = Integer.parseInt(recommendation);
			cri.add(Restrictions.eq("recommendation", sta));
		}
		cri.add(Restrictions.eq("type", type));
		Page<Summary> result = this.pagedQuery(cri, pageNo, pageSize,
				Order.desc("recommendation"), Order.desc("showorder"), Order.desc("words"));
		return result;
	}

	public boolean recommendById(String summaryid, int recommend) {
		int count = this.execute("update Summary set recommendation="
				+ recommend + " where summaryid='" + summaryid + "'");
		return count > 0 ? true : false;
	}

	public boolean crossPageSaveOrder(String id, int kind) {
		Summary summary = this.get(id);
		String sql = "";
		if (kind == 2) {// 向下跨页
			sql = "select max(showorder),cast(summaryid as char(32)) from t_summary where type="
					+ summary.getType()
					+ " and showorder=(select max(showorder) from t_summary where type="
					+ summary.getType()
					+ " and showorder<"
					+ summary.getShoworder() + ")";
		} else if (kind == 1) {// 向上跨页
			sql = "select min(showorder),cast(summaryid as char(32)) from t_summary where type="
					+ summary.getType()
					+ " and showorder=(select min(showorder) from t_summary where type="
					+ summary.getType()
					+ " and showorder>"
					+ summary.getShoworder() + ")";
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
		Summary s = this.get(anotherId);
		s.setShoworder(summary.getShoworder());
		summary.setShoworder(anotherShoworder);
		// System.out.println("原id : " + summary.getSummaryid() +
		// " =---=-=-=- order : " + summary.getShoworder());
		// System.out.println("原id : " + s.getSummaryid() +
		// " =---=-=-=- order : " + s.getShoworder());
		this.getHibernateTemplate().merge(s);
		this.getHibernateTemplate().merge(summary);
		return true;
	}

	public Summary saveArticle(Summary summary) {
		this.save(summary);
		return summary;
	}
}
