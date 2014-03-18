package com.wanzhu.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wanzhu.entity.User;
import com.wanzhu.entity.admin.Administrator;
import com.wanzhu.utils.DatePropertyEditor;

/**
 * 
 * <br>
 * <b>类描述:</b>
 * <pre>
 * 所有Controller的基类
 * </pre>
 * 
 * @see
 * @since
 */
public class BaseController { 
	protected Log log = LogFactory.getLog(this.getClass());
	
	protected static final String ERROR_CODE_KEY = "errorCode";
	protected static final String ERROR_MESSAGE_KEY = "errorMessage";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor());
	}

	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected HttpSession getSession() {
		return this.getRequest().getSession();
	}
	
	/**
	 * 获取web项目根路径
	 * @return
	 */
	protected String getWebRoot() {
		HttpServletRequest request = this.getRequest();
		// 得到协议如：http
		String scheme = request.getScheme();
		//得到服务器名称如：127.0.0.1
		String serverName = request.getServerName(); 
		//得到端口号如8080
		int serverPort = request.getServerPort();
		//得到当前上下文路径，也就是安装后的文件夹位置。
		String contextPath = request.getContextPath();
		//连起来拼成完整的url
		String webRoot = scheme+"://"+serverName+":"+serverPort+contextPath+"/";
		return webRoot;
	}
	
	/**
	 * 获取保存在Session中的用户对象
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	protected User getSessionUser() throws Exception {
		User u = (User) this.getSession().getAttribute(CommonConstant.USER_CONTEXT);
		return u;
	}
	
	/**
	 * 获取保存在Session中的管理员对象
	 * 
	 * @param request
	 * @return
	 */
	protected Administrator getSessionAdmin() {
		return (Administrator) this.getSession().getAttribute(CommonConstant.ADMIN_CONTEXT);
	}
   
	/**
	 * 保存用户对象到Session中
	 * @param request
	 * @param user
	 */
	protected void setSessionUser(User user) {
		this.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
	}
	
	/**
	 * 保存用户对象到Session中
	 * @param request
	 * @param user
	 */
	protected void setSessionAdmin(Administrator admin) {
		this.getSession().setAttribute(CommonConstant.ADMIN_CONTEXT, admin);
	}
	
	/**
	 * 从session中删除用户对象
	 */
	protected void removeSessionUser(){
		this.getSession().removeAttribute(CommonConstant.USER_CONTEXT);
	}
	
	/**
	 * 保存用户对象到cookie中
	 * 记住帐号 或者自动登录
	 * @param user 
	 * @param response
	 * @param autoLogin 0-不自动登录，1-自动登录
	 */
	protected void setCookieUser(HttpServletResponse response, User user, String autoLogin) {
		Cookie emailCookie = new Cookie(CommonConstant.COOKIE_USER_EMAIL, user.getEmail());
		emailCookie.setMaxAge(365 * 24 * 60 * 60);
		emailCookie.setPath("/");
		response.addCookie(emailCookie);
		
		if("1".equals(autoLogin)) {
			Cookie autologinCookie = new Cookie(CommonConstant.COOKIE_AUTOLOGIN, autoLogin);
			autologinCookie.setMaxAge(365 * 24 * 60 * 60);
			autologinCookie.setPath("/");
			response.addCookie(autologinCookie);
			Cookie userIdCookie = new Cookie(CommonConstant.COOKIE_PASSWORD, user.getPassword());
			userIdCookie.setMaxAge(365 * 24 * 60 * 60);
			userIdCookie.setPath("/");
			response.addCookie(userIdCookie);
		}
	}
	
	protected void clearCookieUser(HttpServletResponse response) {
		Cookie userIdCookie = new Cookie(CommonConstant.COOKIE_PASSWORD, null);
		userIdCookie.setMaxAge(0);
		userIdCookie.setPath("/");
		response.addCookie(userIdCookie); 
	}

	/**
	 * 获取基于应用程序的url绝对路径
	 * 
	 * @param request
	 * @param url
	 *            以"/"打头的URL地址
	 * @return 基于应用程序的url绝对路径
	 */
	public final String getAppbaseUrl(HttpServletRequest request, String url) {
		Assert.hasLength(url, "url不能为空");
		Assert.isTrue(url.startsWith("/"), "必须以/打头");
		return request.getContextPath() + url;
	}
	
	
	protected Map<String, String> loadArguments(String[] names) {
		Map<String, String> map = new HashMap<String, String>();
		int len = names.length;
		for (int i = 0; i < len; i++) {
			if (getRequestParam(names[i]) != null) {
				map.put(names[i], getRequestParam(names[i]).trim());
			} else {
				map.put(names[i], getRequestParam(names[i]));
			}
		}
		return map;
	}
	
	/**
	 * 获取参数值
	 * @param paramName
	 * @return
	 */
	protected  String getRequestParam(String paramName) {		
		return getRequest().getParameter(paramName);
	}
	
}

