package com.wanzhu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.SystemBuffer;
import com.wanzhu.dao.CodeDao;
import com.wanzhu.entity.Code;

@Service
@Transactional(readOnly=true)
public class CodeService {

	@Autowired
	private CodeDao codeDao;
	
	public List<Code> getAllCodeList() {
		return this.codeDao.getAllCodeList();
	}
	
	/**
	 * 获取省份列表
	 * @return
	 */
	public List<Code> getProvinceCodeList() {
		List<Code> codeList = new ArrayList<Code>();
		for(Code code : SystemBuffer.areaCode) {
			if(code.getType() == 1) {
				codeList.add(code);
			}
		}
		return codeList;
	}
	
	/**
	 * 获取城市列表
	 * @param provinceCode
	 * @return
	 */
	public List<Code> getCityCodeList(String provinceCode) {
		List<Code> codeList = new ArrayList<Code>();
		for(Code code : SystemBuffer.areaCode) {
			if(code.getType() == 2) {
				if(code.getCode().startsWith(provinceCode)) {
					codeList.add(code);
				}
			}
		}
		return codeList;
	}
}
