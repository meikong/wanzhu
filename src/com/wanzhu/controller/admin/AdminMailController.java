package com.wanzhu.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.Page;
import com.wanzhu.entity.User;
import com.wanzhu.entity.UserEvent;
import com.wanzhu.service.admin.AdminEventService;
import com.wanzhu.service.admin.AdminUserService;
import com.wanzhu.task.MailThread;

@Controller
@RequestMapping("/adminmail")
public class AdminMailController extends BaseController
{
	@Autowired
	private AdminUserService  adminUserService;	
	@Autowired
	private AdminEventService adminEventService;
	
	/**
     * 跳转到给所有会员发邮件的页面
     */
    @RequestMapping(value = "/toSendAlls")
    public String toSendAlls(Model model)
    {
        model.addAttribute("toSendAlls",true);
        model.addAttribute("formAction","sendtoall");
        return "admin/sendMail";
    }
    
    /**
     * 跳转到给报名参加活动的会员发邮件的页面
     */
    @RequestMapping(value = "/toSendEvenUsers/{eventId}")
    public String toSendEvenUsers(Model model, @PathVariable("eventId") String eventId)
    {
        model.addAttribute("toSendAlls",false);
        model.addAttribute("eventId",eventId);
        model.addAttribute("formAction","sendtoeventuser");
        return "admin/sendMail";
    }
	
	/**
	 * 给所有会员发邮件
	 * @param subject 邮件主题
	 * @param content 富文本邮件正文
	 * @param au 是否受限：0-受限，1-不受限，2-全部
	 * @param ac 是否激活：0-未激活，1-激活，2-全部
	 * @return json
	 */
	@RequestMapping(value="/sendtoall", method=RequestMethod.POST)
	@ResponseBody
	public String sendToAll(String subject, String content, String au, String ac)
	{
		Integer state=null;
		Integer activated=null;
		if(au!="2"&&au!=null)
			state=Integer.parseInt(au);
		if(ac!="2"&&ac!=null)
			activated=Integer.parseInt(ac);
		
		Page<User> page=null;
		try
		{
			page=adminUserService.ListUserPage(1, 100000, null, state, activated);
			List<String> emailList=new ArrayList<String>();
			for(int i=0; i<page.getResult().size(); i++)
			{
				emailList.add( page.getResult().get(i).getPersonalemail() );
			}
			MailThread mailThread=new MailThread();
			mailThread.setEmailList(emailList);
			mailThread.setSubject(subject);
			mailThread.setContent(content);
			mailThread.start();
			return "{\"result\":\"success\"}";
		}
		catch(Exception e)
		{
			log.error("给会员发邮件出错！", e);
			return "{\"result\":\"failure\"}";
		}
	}
	
	/**
	 * 给报名参加活动的会员发邮件
	 * @param subject 邮件主题
	 * @param content 富文本邮件正文
	 * @param id 活动ID
	 * @param type 人员类型：空串-报名人员，1-已签到人员，2-未签到人员，3-已审核通过人员，4-未审核通过人员
	 * @return json
	 */
	@RequestMapping(value="/sendtoeventuser", method=RequestMethod.POST)
	@ResponseBody
	public String sendToEventUser(String subject, String content, String id, Integer type)
	{
		Page<UserEvent> page=null;
		try
		{
			page = adminEventService.queryUserEventPages(null, id, 1, 100000, type);
			List<String> emailList=new ArrayList<String>();
			for(int i=0; i<page.getResult().size(); i++)
			{
				emailList.add( page.getResult().get(i).getUser().getPersonalemail() );
			}
			MailThread mailThread=new MailThread();
			mailThread.setEmailList(emailList);
			mailThread.setSubject(subject);
			mailThread.setContent(content);
			mailThread.start();
			return "{\"result\":\"success\"}";
		}
		catch(Exception e)
		{
			log.error("给报名(type="+type+")的会员发邮件出错！", e);
			return "{\"result\":\"failure\"}";
		}
		finally
		{
			page=null;
		}
	}
}
