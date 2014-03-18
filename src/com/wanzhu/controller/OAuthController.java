package com.wanzhu.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.service.AdvService;
import com.wanzhu.service.OAuthService;
import com.wanzhu.service.UserService;
import com.wanzhu.utils.ErrorHelper;
import com.wanzhu.utils.MD5;
import com.wanzhu.utils.OAuthHelper;
import com.wanzhu.utils.StringUtils;

/**
 * 开放授权登录
 * @author Keran
 *
 */
@Controller
@RequestMapping("/ologin")
public class OAuthController extends BaseController {
	
	@Autowired
	private OAuthService service;
	@Autowired
	private UserService userService;
	@Autowired
	private AdvService advService;
	
	/**
	 * 请求开发登录平台未授权的code
	 * @param origin 用户来源，值为:douban, qq, weibo等
	 * @return
	 */
	@RequestMapping("/auth/{origin}")
	public String auth(@PathVariable("origin") String origin,String operate,String toUrl,Model model) {
		//处理ajax的登录前地址
		if(!StringUtils.isEmpty(toUrl)){
		this.getSession().setAttribute(CommonConstant.LOGIN_TO_URL, toUrl);
		}
		String url = null;
		if("bindOauth".equals(operate)){
			url = service.getAuthUrlByOrigin(origin,OAuthHelper.REDIRECT_BIND_URL);
			if(StringUtils.isEmpty(url)) return "setting";
		}else{
			url = service.getAuthUrlByOrigin(origin,OAuthHelper.REDIRECT_URL);
			if(StringUtils.isEmpty(url)) return "login/login";
		}
		model.addAttribute("url", url);
		return "auth";
	}
	
	/**
	 * 授权回调
	 * @param request
	 * @return
	 */
	@RequestMapping("/callback")
	public String authCallback(HttpServletRequest request,Model model) {
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		String origin = request.getParameter("state");
		
		log.info(code +" "+ error);
		
		if(!StringUtils.isEmpty(error)) 
			return "index";
		//第三方帐号平台登录成功后跳转
		try {
			//获取第三方平台用户信息
			User oauth = service.token(code, origin);
			User user = userService.getUserByOauth(oauth.getOauthid());
			if(user==null){
				//尚未绑定本地帐户，跳转到绑定本地会员页面
				model.addAttribute("oauth", oauth);
				model.addAttribute("adv", advService.queryByParking("10"));
				return "user/oauth2local";
			}else{
				if(StringUtils.isEmpty(user.getPortrait())){
					String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
					user.setPortrait(defaultpath);
				}
				//已经绑定了本地帐户，记录本地帐号到session中
				this.setSessionUser(user);
				//根据是否存在登录前的请求页面跳转到主页或登录前的请求页
				String loginToUrl = null;
				if( this.getSession().getAttribute(CommonConstant.LOGIN_TO_URL)!=null )
				{
					loginToUrl = (String) getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
					this.getRequest().setAttribute("loginToUrl", loginToUrl);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "使用第三方帐号登录异常!");
		}
		return "redirect:/index.html";
	}
	
	
	/**
	 * 绑定本地帐户
	 * @param user
	 * @param bindType create=创建本地帐户，bind=绑定已有本地帐号
	 * @param model
	 * @return json
	 */
	@RequestMapping(value="bla.json")
	public String bindLocalAccount(User u,String bindType,Model model) {
		Result rs = new Result();
		try{
			u.setEmail(u.getEmail().toLowerCase());
			User user = this.userService.bindLocalAccount(u.getEmail(),MD5.convert(u.getPassword()),u.getName(),u.getOauthid(),u.getWeibo(),u.getPortrait());
			this.setSessionUser(new User(user.getUserid(),user.getEmail(),user.getName(),user.getPortrait()));	
			rs.setSuccess(true);
			String loginToUrl = null;
			if( this.getSession().getAttribute(CommonConstant.LOGIN_TO_URL)!=null )
			{
				loginToUrl = (String) getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
				rs.setContent(loginToUrl);
			}			
		}catch(Exception e){
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));			
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 绑定第三方帐户
	 * 添加微博地址至联系方式中
	 * @return
	 */
	@RequestMapping(value="/callback2boa.html")
	public String bindOAuthAccount(HttpServletRequest request,Model model) {
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		String origin = request.getParameter("state");		
		log.info(code +" "+ error);
		if(!StringUtils.isEmpty(error)) {
			model.addAttribute("errorMessage", "绑定新浪微博失败!");
			return "setting";
		}
		//登录成功后跳转
		try {
			//获取第三方平台用户信息
			User oauth = service.token(code, origin);
			//绑定oauth帐户
			User u = this.getSessionUser();
			if(u==null){
				throw new Exception("20007");				
			}
			User user = this.userService.bindOAuthAccount(u.getUserid(), origin,oauth.getOauthid(),oauth.getWeibo(),oauth.getPortrait());
			u.setPortrait(user.getPortrait());
			model.addAttribute("user", user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute(ERROR_CODE_KEY, e.getMessage());
		}
		return "setting/setting";
	}
	
	
	/**
	 * 解除绑定第三方帐户
	 * @param model
	 * @return
	 */
	@RequestMapping(value="unbindOAuthAccount.html")
	public String unbindOAuthAccount(Model model) {
		try{
			User u = this.getSessionUser();
			if(u==null){
				throw new Exception("你尚未登录，无法进行该操作!");
			}
			this.userService.unbindOAuthAccount(u.getUserid());
		}catch(Exception e){
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", "解除绑定失败!");
		}		
		return "setting";		
	}
	
	/**
	 * 分享视频、活动、话题等 。。。
	 * 该功能废弃，分享第三方平台由js实现
	 * @return
	 */
	public String share() {
		return null;
	}
}
