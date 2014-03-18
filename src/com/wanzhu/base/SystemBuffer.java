package com.wanzhu.base;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.wanzhu.entity.Code;
import com.wanzhu.service.CodeService;
import com.wanzhu.utils.UUIDHexGenerator;

public class SystemBuffer {
	// 主键生成器
	public static UUIDHexGenerator uuidHexGenerator = null;
	// 省份代码
	public static List<Code> areaCode = null;
	/**
	 * 1、计数：好友个数，有新增或者删除好友时，要更新friendsCount 2、用户id作为key、好友数作为value
	 */
	public static ConcurrentHashMap<String, Long> friendsCount = new ConcurrentHashMap<String, Long>();

	private SystemBuffer() {
	}

	public static synchronized void init() {
		uuidHexGenerator = new UUIDHexGenerator();
		// 加载代码表
		CodeService codeService = AppliactionContextHelper.getBean("codeService", CodeService.class);
		areaCode = codeService.getAllCodeList();
	}

	public static void main(String[] args) {
		SystemBuffer.uuidHexGenerator = new UUIDHexGenerator();
		System.out.println(SystemBuffer.uuidHexGenerator.generateUUID());
	}

}
