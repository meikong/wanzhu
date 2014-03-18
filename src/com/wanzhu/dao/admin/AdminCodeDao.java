package com.wanzhu.dao.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Code;
@Repository
public class AdminCodeDao extends BaseDao<Code> {

	public List<Code> findCode()
	{
		String hql="from Code";
		return this.find(hql);
	}
}
