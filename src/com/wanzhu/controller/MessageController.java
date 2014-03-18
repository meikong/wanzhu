package com.wanzhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wanzhu.base.BaseController;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.service.AdvService;
import com.wanzhu.service.MessageService;
import com.wanzhu.service.UserService;
import com.wanzhu.utils.ErrorHelper;
import com.wanzhu.utils.EscapeHtml;

/**
 * 私信
 * @author zhanglei
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	@Autowired
	private AdvService advService;
	
	/**
	 * 私信列表
	 * @return
	 */
	@RequestMapping("/messageList.html")
	public String messageList(Model model) {
	    model.addAttribute("adv",advService.queryByParking("2"));
		return "message/message_list";
	}
	
	/**
	 * 与某人的私信
	 * @return
	 */
	@RequestMapping("/messageInfo.html")
	public String messageInfo(String friendId, Model model) {
		try {
			User user = this.getSessionUser();
			User friend = userService.queryUserInfo(friendId);
			int messageCount = this.messageService.getMessagesCountWithAFriend(user.getUserid(), friendId);
			
			model.addAttribute("userId", user.getUserid());
			model.addAttribute("friendId", friendId);
			model.addAttribute("friendName", friend.getName());
			model.addAttribute("messageCount", messageCount);
			  model.addAttribute("adv",advService.queryByParking("2"));
		} catch(Exception e) {
			model.addAttribute(ERROR_CODE_KEY, e.getMessage());
		}
		return "message/message_info";
	}
	
	
	/**
	 * 查询我的私信
	 * @return json
	 */
	@RequestMapping("/queryMessages.json")
	public String queryMessages(int start, int size, Model model) {
		Result rs = new Result();
		try {
			User user = (User)this.getSessionUser();
			rs.setSuccess(true);
			rs.setContent(this.messageService.queryMessages(user.getUserid(), start, size));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 查询与某人的私信
	 * @return json
	 */
	@RequestMapping("/queryMessagesWithAFriend.json")
	public String queryMessagesWithAFriend(String friendId, int start, int size, Model model) {
		Result rs = new Result();
		try {
			User user = (User)this.getSessionUser();
			rs.setSuccess(true);
			rs.setContent(this.messageService.queryMessagesWithAFriend(user.getUserid(), friendId, start, size));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 发私信
	 * @return
	 */
	@RequestMapping("/sendMessage.json")
	public String sendMessage(String friendId, String content, Model model) {
		Result rs = new Result();
		try {
			//对内容转义
			content = EscapeHtml.Html2Text(content);
			content = content.replaceAll("\n", "<br/>");
			User user = this.getSessionUser();
			User friend = this.userService.getUser(friendId);
			this.messageService.sendMessage(user.getUserid(), user.getName(),  friend.getUserid(), friend.getName(), content, friend.getPersonalemail());
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
	 * 删除私信
	 * @return
	 */
	@RequestMapping("/deleteMessage.json")
	public String deleteMessage(String messageId, boolean isSelf, Model model) {
		Result rs = new Result();
		try {
			this.messageService.deleteMessage(messageId, isSelf);
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
	 * 未读私信条数
	 * @return
	 */
	@RequestMapping("/getUnreadMessageCount.json")
	public String getUnreadMessageCount(Model model) {
		Result rs = new Result();
		try {
			User user = this.getSessionUser();
			rs.setSuccess(true);
			rs.setContent(this.messageService.getUnreadMessageCount(user.getUserid()));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
}
