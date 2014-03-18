package com.wanzhu.dao.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.EventLabel;

@Repository
public class AdminEventLabelDao  extends BaseDao<EventLabel>{

	public void saveEventLabel(EventLabel EventLabel){
		this.save(EventLabel);
	}
	
	public String findEventLabel(String veid)
	{
		try {
			String hql="from EventLabel where event.eventid=?";
			List<EventLabel> list=this.find(hql, new Object[]{veid});
			if(list.size()!=0)
				return list.get(0).getAssignid();
			else
				return null;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public boolean delEventLabel(String id)
	{
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("assignid", id);
			this.delete(map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean delEventLabelEventId(String id)
	{
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("event.eventid", id);
			this.delete(map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
}
