package com.wanzhu.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.logging.LogFactory;

import com.wanzhu.base.AppliactionContextHelper;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.User;
import com.wanzhu.service.EventService;
import com.wanzhu.service.UserService;
import com.wanzhu.utils.DateUtil;
import com.wanzhu.utils.SmtpClient;
import com.wanzhu.utils.TemplateHelper;

public class MailTask extends TimerTask {
	@Override
	public void run() {
		List<Event> recommendatedEventList = null;
		StringBuilder eventStringBuilder = null;
		String eventString = null;
		List<User> userList = null;
		try {
			// 获取精彩推荐
			recommendatedEventList = this.queryRecommendatedEventList();
			if (recommendatedEventList.size() == 0) {
				return;
			}

			// 组织精彩推荐列表
			eventStringBuilder = new StringBuilder();
			for (int i = 0; i < recommendatedEventList.size(); i++) {
				eventStringBuilder.append((i + 1) + "&nbsp;&nbsp;" + recommendatedEventList.get(i).getTopic()
						+ "&nbsp;&nbsp;&nbsp;&nbsp;" + DateUtil.date2String(recommendatedEventList.get(i).getStarttime()) + "~"
						+ DateUtil.date2String(recommendatedEventList.get(i).getEndtime()) + "<br><br>");
			}
			eventString = eventStringBuilder.toString();

			// 检索出订阅了邮件的会员
			userList = this.queryUserListWhoSubscribedNews();

			// 合并邮件模版recommendation.tpl，并发送邮件
			Map<String, String> pairs = null;
			StringBuffer sb = null;
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getPersonalemail() != null && userList.get(i).getPersonalemail().length() > 0) {
					pairs = new HashMap<String, String>();
					pairs.put("name", userList.get(i).getName());
					pairs.put("eventList", eventString);
					sb = TemplateHelper.merge("recommendation.tpl", pairs);
					try {
						SmtpClient.sendMail(CommonConstant.recommendation_mail_from_account,
								CommonConstant.recommendation_mail_from_account, CommonConstant.recommendation_mail_from_name,
								CommonConstant.recommendation_mail_from_account_password, userList.get(i).getPersonalemail(),
								CommonConstant.smtp_server, "精彩推荐", // CommonConstant.recommendation_mail_subject,
								sb.toString(), true);
						LogFactory.getLog(MailTask.class).info(
								"发送精彩推荐邮件至用户[" + userList.get(i).getEmail() + "]的邮箱[" + userList.get(i).getPersonalemail()
										+ "]成功。");
					} catch (Exception ex) {
						LogFactory.getLog(MailTask.class).error(
								"发送精彩推荐邮件至用户[" + userList.get(i).getEmail() + "]的邮箱[" + userList.get(i).getPersonalemail()
										+ "]失败。", ex);
					}
					sb = null;
					pairs = null;
				}
			}
		} catch (Exception e) {
			LogFactory.getLog(MailTask.class).error("在定时发送精彩推荐邮件的过程中出错！", e);
		} finally {
			recommendatedEventList = null;
			eventStringBuilder = null;
			eventString = null;
			userList = null;
		}
	}

	private List<Event> queryRecommendatedEventList() {
		// 分别获取往期精彩和近期精彩活动
		EventService eventService = AppliactionContextHelper.getBean("eventService", EventService.class);
		List<Event> recentEventList = eventService.queryRecommendComingEvents();
		List<Event> pastEventList = eventService.queryRecommendPastEvents();

		// 合并
		List<Event> recommendatedEventList = new ArrayList<Event>();
		recommendatedEventList.addAll(recentEventList);
		recommendatedEventList.addAll(pastEventList);

		// 排序
		Event temp = null;
		for (int i = 0; i < recommendatedEventList.size() - 1; i++) {
			for (int j = i + 1; j < recommendatedEventList.size(); j++) {
				if (recommendatedEventList.get(i).getStarttime().before(recommendatedEventList.get(j).getStarttime())) {
					temp = recommendatedEventList.get(i);
					recommendatedEventList.set(i, recommendatedEventList.get(j));
					recommendatedEventList.set(j, temp);
				}
			}
		}

		// 返回
		recentEventList = null;
		pastEventList = null;
		return recommendatedEventList;
	}

	private List<User> queryUserListWhoSubscribedNews() {
		UserService userService = AppliactionContextHelper.getBean("userService", UserService.class);
		return userService.queryAllActivatedUsers();
	}
}
