package com.wanzhu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wanzhu.base.BaseDao;
import com.wanzhu.entity.Code;

@Repository
public class CodeDao extends BaseDao<Code> {

	public List<Code> getAllCodeList() {
		return this.loadAll();
	}
}
