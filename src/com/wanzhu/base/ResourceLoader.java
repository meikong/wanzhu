package com.wanzhu.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class ResourceLoader {
	/**
	 * 获取类装载器
	 * 
	 * @param 无
	 * @return 当前的类装载器
	 */
	private static ClassLoader getTCL() throws IllegalAccessException,
			InvocationTargetException {
		// Are we running on a JDK 1.2 or later system?
		Method method = null;
		try {
			method = Thread.class.getMethod("getContextClassLoader", null);
		} catch (NoSuchMethodException e) {
			// We are running on JDK 1.1
			return null;
		}
		return (ClassLoader) method.invoke(Thread.currentThread(), null);
	}

	/**
	 * 从类路径中获取资源位置
	 * 
	 * @param resource
	 *            资源名
	 * @return 资源URL
	 */
	public URL getResource(String resource) {
		ClassLoader classLoader = null;
		URL url = null;

		try {
			classLoader = getTCL();
			if (classLoader != null) {
				url = classLoader.getResource(resource);
				if (url != null)
					return url;
			}
			// We could not find resource. Ler us now try with the
			// classloader that loaded this class.
			classLoader = this.getClass().getClassLoader();
			if (classLoader != null) {
				url = classLoader.getResource(resource);
				if (url != null)
					return url;
			}
		} catch (Throwable t) {
		}
		return ClassLoader.getSystemResource(resource);
	}
}
