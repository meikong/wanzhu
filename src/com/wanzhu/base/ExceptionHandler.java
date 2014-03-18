package com.wanzhu.base;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;

public class ExceptionHandler implements ThrowsAdvice {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 对异常的处理.
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @param ex
	 * @throws Throwable
	 */
	public void afterThrowing(Method method, Object[] args, Object target,
			Exception ex) throws Throwable {
		logErrorMessage(method, args, target, ex);
	}

	private void logErrorMessage(Method method, Object[] args, Object target,
			Exception ex) {
		log.error(method.getName() + "方法出错：", ex);
		log.error("=====================================");
	}
}
