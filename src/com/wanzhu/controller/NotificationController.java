package com.wanzhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wanzhu.base.BaseController;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.service.AdvService;
import com.wanzhu.service.NotificationService;
import com.wanzhu.utils.ErrorHelper;

/**
 * 通知
 * @author zhanglei
 *
 */
@Controller
@RequestMapping("/notification")
public class NotificationController extends BaseController {

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private AdvService advService;
	
	/**
	 * 通知列表
	 * @return
	 */
	@RequestMapping("/notificationList.html")
	public String notificationList(Model model) {
	    model.addAttribute("adv",advService.queryByParking("3"));
		return "notification/notification_list";
	}
	
	/**
	 * 查询通知列表
	 * @return json
	 */
	@RequestMapping("/queryNotifications.json")
	public String queryNotifications(int start, int size, Model model) {
		Result rs = new Result();
		try {
			User user = this.getSessionUser();
			rs.setSuccess(true);
			rs.setContent(this.notificationService.queryNotifications(user.getUserid(), start, size));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 删除全部通知
	 * @return
	 */
	@RequestMapping("/deleteAllNotifications.json")
	public String deleteAllNotifications(Model model) {
		Result rs = new Result();
		try {
			User user = this.getSessionUser();
			this.notificationService.deleteAllNotifications(user.getUserid());
			rs.setSuccess(true);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 删除通知
	 * @return
	 */
	@RequestMapping("/deleteNotification.json")
	public String deleteNotification(String notificationid, Model model) {
		Result rs = new Result();
		try {
			this.notificationService.deleteNotification(notificationid);
			rs.setSuccess(true);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 未读通知条数
	 * @return
	 */
	@RequestMapping("/getUnreadNotificationCount.json")
	public String getUnreadNotificationCount(Model model) {
		Result rs = new Result();
		try {
			User user = this.getSessionUser();
			rs.setSuccess(true);
			rs.setContent(this.notificationService.getUnreadNotificationCount(user.getUserid()));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
}
