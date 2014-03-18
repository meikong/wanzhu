package com.wanzhu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Code;
import com.wanzhu.entity.Hot;

@Repository
public class HotDao extends BaseDao<Hot> {

	/**
	 * @return
	 * @Date:2013-5-7  
	 * @Author:xuguangyun  
	 * @Description:获取话题热点的所有ID
	 */
	public List<Hot> getAllHotList() {
		return this.loadAll();
	}
}
