//package com.wanzhu.utils;
//
//import org.quartz.CronTrigger;
//import org.quartz.Job;
//import org.quartz.JobDetail;
//import org.quartz.JobExecutionContext;	
//import org.quartz.JobExecutionException;
//import org.quartz.Scheduler;
//import org.quartz.impl.StdSchedulerFactory;
//import com.wanzhu.base.AppliactionContextHelper;
//import com.wanzhu.service.admin.AdminEventService;
//
///**
// * 定时更新新消息缓存，使用quarz实现定时任务
// * @author xu add
// * @since 2012-11-16
// */
//public class UpdateTserverCacheJob implements Job {
//	
//	private static final String FAIL_SERVER_UPDATE="0 0/10 * * * ?";
//	/** 
//	 * 初始化定时更新新消息缓存任务
//	 * @return
//	 */
//	public boolean initTask() {
//		try {
//			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//			JobDetail jobDetail = new JobDetail("UpdateTserverCacheJob", "CacheJob", UpdateTserverCacheJob.class);
//			CronTrigger trigger = new CronTrigger("UpdateTserverCacheJob", "CacheJob", FAIL_SERVER_UPDATE);
//			scheduler.start();
//			scheduler.scheduleJob(jobDetail, trigger);
//			execute(null);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//	/* (non-Javadoc)  
//	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)  
//	 */
//	@Override
//	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		
//		boolean fal=false;
//		AdminEventService adminEventService=AppliactionContextHelper.getBean("adminEventService", AdminEventService.class);
//		System.out.println("测试定时执行系统执行了。。。");
//		adminEventService.updateEvents();
//	}
//}
