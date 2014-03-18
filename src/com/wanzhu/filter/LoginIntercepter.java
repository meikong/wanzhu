package com.wanzhu.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wanzhu.base.AppliactionContextHelper;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.entity.User;
import com.wanzhu.service.UserService;

public class LoginIntercepter extends HandlerInterceptorAdapter {
	private static final String FILTERED_REQUEST = "@@session_context_filtered_request";
	private static final String[] EXClUDE_HTML_PATH = {
		"/event/e_[\\d|\\w]+_[\\d|\\w]+_[\\d|\\w]+.html",
		"/event/d_[\\d|\\w]+.html",
		"/discuz/t_[\\d|\\w]+.html",
		"/event/ae_[\\d|\\w]+.html",
		"/index.html",
		"/register.html",
		"/forgetpassword.html",
		"/login.html",
		"/relationship/friends_[\\d|\\w]+.html",
		"/user/lo.html",
		"/user/f_[\\d|\\w]+.html",
		"/user/emailCheck.html",
		"/user/resetPassword.html",
		"/about.html", //xu 4.2
		"/user/qs.html",
		"/shareholderList.html"
	};
	private static final String[] EXClUDE_AJAX_PATH = {
		"/discuz/queryRemarks.json",
		"/discuz/agreeRemark.json",
		"/discuz/queryTopics.json",
		"/user/queryUserLikeName.json",
		"/user/queryCompanyLikeName.json",
		"/user/querySchoolLikeName.json",
		"/ologin/bla.json",
		"/user/login.json",
		"/user/rp.json", //xu 4.2
		"/user/suu_[\\d]+_[\\d]+_[\\d|\\w]+.json",
		"/user/siu_[\\d]+_[\\d]+_[\\d|\\w]+.json",
		"/label/lc.json",
		"/event/range.json",
		"/event/citis.json",
		"/event/signUpedEvents.json",
		"/activity/act_[\\d|\\w]+.json",
		"/relationship/u_[\\d|\\w]+.json",
		"/label/recommendLables.json",
		"/label/saveUserLabel",
		"/label/delLabel", 
		"/discuz/queryHotTopics.json",
		"/label/hotLables.json",
		"/relationship/recommendFriend.json",
		"/recommendFriend.json"
	};
	
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		User userContext = getSessionUser(request);
		String ip= (request.getHeader("X-Real-IP")==null || request.getHeader("X-Real-IP").length()==0) ? request.getRemoteAddr() : request.getHeader("X-Real-IP");
		log.info( (userContext==null?"":(userContext.getName()+"/"+userContext.getEmail())) + "------" + request.getRequestURI() + "------" + ip );
		// 保证该过滤器在一次请求中只被调用一次
		if (request.getAttribute(FILTERED_REQUEST) != null) {
			return super.preHandle(request, response, handler);
		} else {
			// 设置过滤标识，防止一次请求多次过滤
			request.setAttribute(FILTERED_REQUEST, Boolean.TRUE);			
			// 用户未登录
			if (userContext == null) {
				String toUrl = request.getRequestURL().toString();
				if (!StringUtils.isEmpty(request.getQueryString())) {
					toUrl += "?" + request.getQueryString();
				}
				//记住最后一次访问的html请求地址
				if(toUrl.indexOf(".json") < 0) {
					request.getSession().setAttribute(CommonConstant.LOGIN_TO_URL, toUrl);
				}
				
				Cookie[] cookies = request.getCookies();
				String email = null;
				String password = null;
				String autoLogin = null;
				
				if (!isFirst(cookies)) {
					for (Cookie cookie : cookies) {
						String cookieName = cookie.getName();
						if (null != cookieName && cookieName.equalsIgnoreCase(CommonConstant.COOKIE_USER_EMAIL)) {
							email = cookie.getValue();
						}
						if (null != cookieName && cookieName.equalsIgnoreCase(CommonConstant.COOKIE_PASSWORD)) {
							password = cookie.getValue();
						}
						if (null != cookieName && cookieName.equalsIgnoreCase(CommonConstant.COOKIE_AUTOLOGIN)) {
							autoLogin = cookie.getValue();
						}
					}
					//自动登录
					if (email != null && password != null && "1".equals(autoLogin)) {
						 //request.getRequestDispatcher("/user/cl").forward(request, response);
						cookieLogin(request, response);
						return false;
					} else {
						// 转发到登录页面
						return handlerRequest(request, response);
					}

				} else {
					// 转发到登录页面
					return handlerRequest(request, response);
				}
				
				
				
			} else if(request.getRequestURL().toString().indexOf(".json")<0) {
				String currentURL = request.getRequestURL().toString();
				if(!isExcludeURL(request.getRequestURI().replaceFirst(request.getContextPath(), ""), EXClUDE_HTML_PATH)) {
					currentURL = "/index.html";
				}
				if(currentURL.indexOf("lo.html")<0) {
					request.getSession().setAttribute(CommonConstant.CURRENT_URL, currentURL);
				}
			}
			
		}

		return super.preHandle(request, response, handler);
	}

	private boolean handlerRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if(request.getRequestURL().toString().indexOf(".json")>-1) {
			if(!isExcludeURL(request.getRequestURI().replaceFirst(request.getContextPath(), ""), EXClUDE_AJAX_PATH)) {
				response.getWriter().write("{\"success\": false, \"msg\":\"20003\"}");
			} else return true;
		} else {
			if(!isExcludeURL(request.getRequestURI().replaceFirst(request.getContextPath(), ""), EXClUDE_HTML_PATH)) {
				request.getRequestDispatcher("/login.html").forward(request, response);
			} else {
				return true;
			}
		}
		return false;
	}
	
	private boolean isExcludeURL(String shortUrl, String[] excludePath) {
		if("/".equals(shortUrl)) return true;
		for(String excludeUrl : excludePath) {
			if(shortUrl.matches(excludeUrl)) {
				return true;
			}
		}
		return false;
	}

	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT);
	}

	/**
	 * 通过cookie检查是否是第一次访问
	 * 
	 * @param cookies
	 * @return true为第一次false 为非第一次
	 */
	public static boolean isFirst(Cookie cookies[]) {
		boolean flag = false;
		if (null == cookies) {
			flag = true;
		} else {
			for (Cookie cookie : cookies) {
				String key = cookie.getName();
				if (key.equalsIgnoreCase("jsessionid")) {
					break;
				}
			}
		}
		return flag;
	}
	
	public void cookieLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cookieUserEmail = null;
		String cookiePassword = null;
		for (Cookie cookie : request.getCookies()) {
			String cookieName = cookie.getName();
			if (null != cookieName && cookieName.equalsIgnoreCase(CommonConstant.COOKIE_USER_EMAIL)) {
				cookieUserEmail = cookie.getValue();
			}
			if (null != cookieName && cookieName.equalsIgnoreCase(CommonConstant.COOKIE_PASSWORD)) {
				cookiePassword = cookie.getValue();
			}
		}
		if(!StringUtils.isEmpty(cookieUserEmail) && !StringUtils.isEmpty(cookiePassword)) {
			UserService userService = AppliactionContextHelper.getBean(UserService.class);
			User user = null;
			try {
				user = userService.login(cookieUserEmail, cookiePassword);
			} catch (Exception e) {
				Cookie userIdCookie = new Cookie(CommonConstant.COOKIE_PASSWORD, null);
				userIdCookie.setMaxAge(0);
				userIdCookie.setPath("/");
				response.addCookie(userIdCookie); 
			}
			if(user != null) {
				if(StringUtils.isEmpty(user.getPortrait())){
					String defaultpath = getWebRoot(request)+"/"+ CommonConstant.Default_USER_PORTRAIT;
					user.setPortrait(defaultpath);
				}
				request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
			}
		}
		String loginToUrl = (String) request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
		if(StringUtils.isEmpty(loginToUrl)) {
			response.sendRedirect(getWebRoot(request)+"index.html");
		} else {
			response.sendRedirect(loginToUrl);
		}
	}
	
	private String getWebRoot(HttpServletRequest request) {
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

}
