package com.wanzhu.controller.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.Page;
import com.wanzhu.entity.Company;
import com.wanzhu.entity.EducationExperience;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.entity.WorkExperience;
import com.wanzhu.service.admin.AdminUserService;
import com.wanzhu.utils.ErrorHelper;

@Controller
@RequestMapping("/adminuser")
public class AdminUserController extends BaseController{
	
	@Autowired
	private AdminUserService  adminUserService;
	/**
	 * 查询会员列表
	 * @return
	 */
	@RequestMapping(value="listuser")
	public String queryUserList(Model model,String content,Integer pageNo, Integer pageSize,String au,String ac) {
		if(this.getSessionAdmin()==null)
		{
			return "redirect:/admin/index?target=_top";
		}
		try {
			if(null == pageNo) {
				pageNo = 1;
			}
			if(null == pageSize) {
				pageSize = Page.DEFAULT_PAGE_SIZE;
			}
			Integer audit=null;
			Integer acdit=null;
			if(au!="2"&&au!=null)audit=Integer.parseInt(au);
			if(ac!="2"&&ac!=null)acdit=Integer.parseInt(ac);
			Page<User> page=adminUserService.ListUserPage(pageNo, pageSize, content,audit,acdit);
			model.addAttribute("page", page);
			model.addAttribute("content", content);
			model.addAttribute("au", au);
			model.addAttribute("ac", ac);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return "admin/memberList";
	}
	
	@RequestMapping(value="Eventuser")
	public String queryEventUserList(Model model,String content,Integer pageNo, Integer pageSize,String au,String ac,String userid) {
		if(this.getSessionAdmin()==null){
			return "redirect:/admin/index?target=_top";
		}
		try {
			if(null == pageNo) {
				pageNo = 1;
			}
			if(null == pageSize) {
				pageSize = 10;
			}
			List<String> listUser=new ArrayList<String>();
			if(userid!=null){
				String[] user=userid.split("\\|");
				for (int i = 0; i < user.length; i++) {
					listUser.add(user[i]);
				}
			}
			Integer audit=null;
			Integer acdit=null;
			if(au!="2"&&au!=null)audit=Integer.parseInt(au);
			if(ac!="2"&&ac!=null)acdit=Integer.parseInt(ac);
			Page<User> page=adminUserService.ListUserPage(pageNo, pageSize, content,audit,acdit);
			if(page.getResult().size()!=0){
				for (int i = 0; i < page.getResult().size(); i++) {
					for (int j = 0;j < listUser.size(); j++) {
						if(page.getResult().get(i).getUserid().equals(listUser.get(j))){
							page.getResult().get(i).setSource("1");
						}
					}
				}
			}
			model.addAttribute("page", page);
			model.addAttribute("content", content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return "admin/chooseMen";
	}
	
	
	@RequestMapping(value="queryListJsonList")
	@ResponseBody
	public List<User> queryEventUserListJsonList(Model model,String content,Integer pageNo, Integer pageSize) {
		if(this.getSessionAdmin()==null)
		{
			return null;
		}
		Page<User> page=null;
		try {
			if(null == pageNo) {
				pageNo = 1;
			}
			if(null == pageSize) {
				pageSize = Page.DEFAULT_PAGE_SIZE;
			}
			Integer audit=null;
			Integer acdit=null;
			String au=null;
			String ac=null;
			if(au!="2"&&au!=null)audit=Integer.parseInt(au);
			if(ac!="2"&&ac!=null)acdit=Integer.parseInt(ac);
			page=adminUserService.ListUserPage(pageNo, pageSize, content,audit,acdit);
			model.addAttribute("page", page);
			model.addAttribute("content", content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return page.getResult();
	}
	
	
	
	/**
	 * 查询会员详情
	 * @return
	 */
	@RequestMapping(value="queryuser")
	public String queryUserInDetail(Integer userId ,Model model) {
		if(this.getSessionAdmin()==null)
		{
			return "redirect:/admin/index?target=_top";
		}
		User user=adminUserService.queryUser(userId);
		
		Set<WorkExperience> listWorkExperience=user.getWorkExperiences();
		Set<EducationExperience> listEducationExperience=user.getEducationExperiences();
		model.addAttribute("user", user);
		model.addAttribute("listWorkExperience", listWorkExperience);
		model.addAttribute("listEducationExperience", listEducationExperience);
		return "admin/memberInfo";
	}
	
	/**
	 * 审核会员（更改受限值）
	 * @return
	 */
	@RequestMapping(value="audituser")
	@ResponseBody
	public int auditUser(Integer userId ,String audit,Model model) {
		if(this.getSessionAdmin()==null)
		{
			return 3;
		}
		Integer audits=Integer.parseInt(audit);
		try {
			adminUserService.auditUser(userId, audits);
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			return 1;
		}
	}
	
	@RequestMapping(value="disableUser")
	@ResponseBody
	public int disableUser(Integer userId,String disable)
	{
		if(this.getSessionAdmin()==null)
		{
			return 3;
		}
		Integer audits=Integer.parseInt(disable);
		try {
			adminUserService.disableUser(userId, audits);
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			return 1;
		}
	}
	
	/**
	 * 诱导输入查询用户
	 */
	@RequestMapping(value="queryUserByNamespy")
	public String queryUser(String name,Integer start,String eventId ,Model model)
	{
	    Result  result = new Result();
	    try
        {
            result.setSuccess(true);
            result.setContent(adminUserService.queryUserByNamespy(name,start,eventId));
        }
        catch(Exception e)
        {
            log.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setMsg(ErrorHelper.getMsg(e.getMessage()));
        }
	    model.addAttribute("result", result);
	    return "jsonview";
	}
	
	
	
}
