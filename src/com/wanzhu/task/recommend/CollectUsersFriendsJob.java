package com.wanzhu.task.recommend;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;	
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import com.wanzhu.base.AppliactionContextHelper;
import com.wanzhu.service.admin.AdminEventService;

/**
 * 定时按策略更新每个用户的好友
 * @author Guibin Zhang
 * @since 2012-11-16
 */
public class CollectUsersFriendsJob implements Job {
	
	private static final String COLLECT_EXCUTE="0 0 2 * * ?";
	/** 
	 * 初始化定时更新新消息缓存任务
	 * @return
	 */
	public boolean initTask() {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			JobDetail jobDetail = new JobDetail("CollectUsersFriendsJob", "CollectJob", CollectUsersFriendsJob.class);
			CronTrigger trigger = new CronTrigger("CollectUsersFriendsJob", "CollectJob", COLLECT_EXCUTE);
			scheduler.start();
			scheduler.scheduleJob(jobDetail, trigger);
			execute(null);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/* (non-Javadoc)  
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("开始");
		RecommendFriendHelper.execute();
		System.out.println("结束");
	}
}
