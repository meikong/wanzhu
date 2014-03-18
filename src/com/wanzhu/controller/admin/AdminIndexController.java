package com.wanzhu.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wanzhu.base.BaseController;
import com.wanzhu.entity.admin.Administrator;
import com.wanzhu.service.admin.AdminIndexService;

@Controller
@RequestMapping("/lagouAdmin")
public class AdminIndexController extends BaseController {

	@Autowired 
	private AdminIndexService adminIndexService;
	
	@RequestMapping(value="index")
	public String startLogin(String target,Model model)
	{
		if(target!=null){
			model.addAttribute("target", target);
		}else
		{
			model.addAttribute("target", "");
		}
		return "admin/index";
	}
	
	@RequestMapping(value="login")
	public String endLogin()
	{
		if(this.getSessionAdmin()==null){
			return "admin/login";
		}else{
			return "admin/desktop";
		}
	}
	
	@RequestMapping(value="logins")
	public String login(Administrator administrator,Model model) {
		try {
			log.info("开始登录管理后台");
			log.info("执行登陆查询");
			//System.out.println(administrator.getAdministrator());
			//System.out.println(administrator.getPassword()+"mmmmmmmmmmmmmmmmmmmmmmmm");
			Administrator login=adminIndexService.login(administrator);
			log.info("登录返回状态");
			if(login!=null)
			{
				log.info("登录成功");
				this.setSessionAdmin(login);
				model.addAttribute(ERROR_CODE_KEY, "登录成功。");
				return "admin/desktop";
			}
			else
			{
				log.info("登录失败");
				model.addAttribute(ERROR_CODE_KEY, "登录失败。");
				return "admin/login";
			}
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute(ERROR_CODE_KEY, "登录失败。");
			return "admin/login";
		}
	}
	
	@RequestMapping(value="logout")
	public String logout() {
		log.info("退出登录");
		getRequest().getSession().invalidate();
		return "admin/index";
	}
	
}
