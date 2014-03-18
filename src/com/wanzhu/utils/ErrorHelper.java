package com.wanzhu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ErrorHelper extends Properties {
	private static final long serialVersionUID = 1L;
	private static final Properties errors = new Properties();
	
	static {
		InputStream is = ErrorHelper.class.getResourceAsStream("/errors.properties");
        try {
        	errors.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ErrorHelper(){}
	
	public static String error(String errorCode) {
		return errors.getProperty(errorCode);
	}
	
	public static String getMsg(String errorCode) {
		String errorMsg = errors.getProperty(errorCode);
		if(StringUtils.isEmpty(errorMsg)){
			errorMsg = errors.getProperty("33001");
		}
    	return errorMsg;
	}
	
	
}
