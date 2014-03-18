package com.wanzhu.dao.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Lectrue;

@Repository
public class AdminLectrueDao extends BaseDao<Lectrue> {

	public boolean saveLectrue(Lectrue lectrue) {
		try {
			this.save(lectrue);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public String findLectrue(String evid)
	{
		try {
			String hql="from Lectrue where event.eventid=?";
			List<Lectrue> list=this.find(hql, new Object[]{evid});
			if(list.size()!=0){
				return list.get(0).getLectureid();
			}else{
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public boolean deleteLectrue(String LectrueId) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("lectureid", LectrueId);
			this.delete(map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean deleteLectrueEventId(String eventid) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("event.eventid", eventid);
			this.delete(map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
