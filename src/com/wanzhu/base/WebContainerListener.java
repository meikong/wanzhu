package com.wanzhu.base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebContainerListener implements ServletContextListener {
	public WebContainerListener() {
		super();
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("[系统开始启动]");
		// 初始化系统缓存
		SystemBuffer.init();
		// 定时任务
	}

	public void contextDestroyed(ServletContextEvent arg0) {

	}
}
