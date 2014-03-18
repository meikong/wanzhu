package com.wanzhu.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wanzhu.base.BaseController;
import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.base.SystemBuffer;
import com.wanzhu.base.Vcode;
import com.wanzhu.entity.EducationExperience;
import com.wanzhu.entity.Event;
import com.wanzhu.entity.Result;
import com.wanzhu.entity.User;
import com.wanzhu.entity.WorkExperience;
import com.wanzhu.jsonvo.FriendVO;
import com.wanzhu.service.AdvService;
import com.wanzhu.service.EventService;
import com.wanzhu.service.FriendsService;
import com.wanzhu.service.UserEventService;
import com.wanzhu.service.UserService;
import com.wanzhu.utils.ErrorHelper;
import com.wanzhu.utils.EscapeHtml;
import com.wanzhu.utils.JsonUtil;
import com.wanzhu.utils.MD5;
import com.wanzhu.utils.SmtpClient;
import com.wanzhu.utils.SqlFilter;
import com.wanzhu.utils.StringUtils;
import com.wanzhu.utils.TemplateHelper;
import com.wanzhu.utils.VcodeUtil;

/**
 * 会员
 * @author Keran
 */
/**
 * @author cwd
 * 
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private EventService eventService;
	@Autowired
	private AdvService advService;

	/**
	 * 登录 1、验证登录 2.1.1、登录失败跳转到登陆页 2.2.1、登录成功记录用户信息到session
	 * 2.2.2、跳转到正确的页面（主页或登录前正在处理的页面）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login.json")
	public String login(User user,Model model, HttpServletResponse response, String autoLogin) {
		Result rs = new Result();
		try {
			//MD5加密密码
			user.setPassword(MD5.convert(user.getPassword()));					
			user = this.userService.login(user.getEmail(), user.getPassword());
			String position="";
			String companyName="";
			if (user == null) {
				throw new Exception("20004");
			}
			if( user.getWorkExperiences()!=null){
				for(WorkExperience we: user.getWorkExperiences()){
					if(we.getCurrent()==1){
						position=we.getPosition();
						companyName=we.getCompany().getCompany();
					}
				}
			}
			if(StringUtils.isEmpty(user.getPortrait())){
				String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
				user.setPortrait(defaultpath);
			}
			
			this.setSessionUser(new User(user.getUserid(),user.getEmail(),user.getName(),user.getPortrait(),user.getPassword(),companyName,position,user.getState()));
			this.setCookieUser(response, user, autoLogin == null ? "0" : "1");
			rs.setSuccess(true);
			Map data = new HashMap();
			data.put("userid", user.getUserid());
			data.put("email", user.getEmail());
			data.put("name", user.getName());
			data.put("portrait", user.getPortrait());
			//xu
			data.put("password", user.getPassword());
			String loginToUrl = null;
			if( this.getSession().getAttribute(CommonConstant.LOGIN_TO_URL)!=null )
			{
				loginToUrl = (String) getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
				data.put("loginToUrl", loginToUrl);
			//	data.put("loginToUrl", "http://localhost:8080/3wcoffee/user/qs.html");
			}
			rs.setContent(data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));			
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	

	/**
	 * 注销 1、注销session 2、跳转到登陆页面
	 * 
	 * @return
	 */
	@RequestMapping(value="/lo.html")
	public String logout(HttpServletResponse response) {
		String currentUrl = (String) getSession().getAttribute(CommonConstant.CURRENT_URL);
		this.getSession().invalidate();
		this.clearCookieUser(response);
		if(null != currentUrl) {
			return "redirect:"+currentUrl;
		}
		return "redirect:/index.html";
	}

	/**
	 * 密码重置
	 *1、数据库重新初始化密码
	 *2、将新的密码发送到用户帐号邮箱
	 *3、跳转密码重置成功页面
	 * 
	 * @return
	 */
	@RequestMapping(value="resetPassword.html")
	public String resetPassword(String email,Model model) {
		try {
		    model.addAttribute("adv", advService.queryByParking("7"));
			this.userService.resetPassword(email);
		} catch (Exception e) {
			log.error(e.getMessage(), e);			
			model.addAttribute(ERROR_CODE_KEY, e.getMessage());
		}
		return "user/forgetPassword_finish";
	}
	
	/**
	 * 会员修改密码
	 * @return xu
	 */
	@RequestMapping(value="rp.json")
	public String editPassword(String oldpwd,String newpwd,String newrepwd,String userId,Model model){
		Result rs = new Result();
		try{
			User u = this.getSessionUser();
			//xu 4.2
			if(oldpwd.length()>16){
				this.userService.editPassword(userId,oldpwd,MD5.convert(newpwd),MD5.convert(newrepwd));
				model.addAttribute("http","out");
			}
			else{
				this.userService.editPassword(u.getUserid(),MD5.convert(oldpwd),MD5.convert(newpwd),MD5.convert(newrepwd));
				model.addAttribute("http","int");
			}
			//xu end
			
			rs.setSuccess(true);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		
		return "jsonview";
	}
	
	/**
	 * 生成校验码
	 * @return
	 */
	@RequestMapping(value="getValidateCode")
	@ResponseBody
	public byte[] getValidateCode() {
		try {
			Vcode vcode = VcodeUtil.generate();
			//生成校验码放入session中
			this.getSession().setAttribute("vcodeserver", vcode.getCode());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(vcode.getImage(), "JPEG", baos);
			return baos.toByteArray();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 会员注册
	 * 
	 * @return json
	 */
	@RequestMapping(value="register")
	public String register(String email,String password,String name,Model model) {
		Result rs = new Result();
		try {
			/*//验证验证码
			String vcodeserver = (String) this.getSession().getAttribute("vcodeserver");
			if(!vcodeclient.equals(vcodeserver)){
				rs.setSuccess(false);
				rs.setMsg("验证码输入错误！");
				model.addAttribute("result", rs);
				return "jsonview";
			}*/
			email = email.toLowerCase();
			User user = new User();
			//SQL过滤
			email = SqlFilter.doFilter(email);
			user.setEmail(email);
			user.setPassword(MD5.convert(password));
			//SQL过滤
			name = SqlFilter.doFilter(name);
			user.setName(name);
			user.setCreatetime(new Date());
			user.setState(1);//注册审核状态默认为不受限
			user.setActivated(0);//激活状态默认为未激活
			user.setSource("3wcoffee");//用户来源
			user.setSex(2);//性别默认男
			user.setEventscount(0);
			user.setFriendscount(0);
			user.setSummary(CommonConstant.DEFAULT_SUMMARY);
			userService.register(user);			
			//发送激活链接到对方邮箱	(该操作失败时不需要回滚数据库操作)
			try{
				String emailActivatedPath = this.getWebRoot()+"user/emailCheck.html?code="+user.getUserid();
				//合并邮件模版mailactivate.tpl，并发送邮件
				Map<String, String> pairs =new HashMap<String, String>();
				pairs.put("name", user.getName());
				pairs.put("activateUrl", emailActivatedPath);
				StringBuffer sb=TemplateHelper.merge("mailactivate.tpl", pairs);
				String content = sb.toString();
				SmtpClient.sendMail( CommonConstant.recommendation_mail_from_account, 
						CommonConstant.recommendation_mail_from_account,
						CommonConstant.recommendation_mail_from_name,
						CommonConstant.recommendation_mail_from_account_password,
						email,
						CommonConstant.smtp_server,
						CommonConstant.activate_mail_subject,
						content,true);
			}catch(Exception e){
				log.error(e.getMessage(), e);
			}
			//注册成功跳转到激活邮箱页面
			rs.setSuccess(true);
			rs.setContent(user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 *  重新发送帐号激活邮件
	 * @param userid
	 * @param email
	 * @param model
	 * @return json
	 */
	@RequestMapping(value="/resendActivatedMail")
	public String resendActivatedMail(String userid,Model model){
		Result rs = new Result();
		try{
			//发送激活链接到对方邮箱	(该操作失败时不需要回滚数据库操作)		
			String emailActivatedPath = this.getWebRoot()+"user/emailCheck.html?code="+userid;
			String subject = "3W Coffee注册确认邮件";
			//合并邮件模版mailactivate.tpl，并发送邮件
			Map<String, String> pairs =new HashMap<String, String>();
			User user = this.userService.queryUserInfo(userid);
			pairs.put("name", user.getName());
			pairs.put("activateUrl", emailActivatedPath);
			StringBuffer sb=TemplateHelper.merge("mailactivate.tpl", pairs);
			String content = sb.toString();
			SmtpClient.sendMail( CommonConstant.recommendation_mail_from_account, 
					CommonConstant.recommendation_mail_from_account,
					CommonConstant.recommendation_mail_from_name,
					CommonConstant.recommendation_mail_from_account_password,
					user.getEmail(),
					CommonConstant.smtp_server,
					subject,
					content,true);
			rs.setSuccess(true);
			rs.setMsg("邮件已经发送！");
		}catch(Exception e){
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg("邮件发送失败！");
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}

	/**
	 * 查询个人资料
	 * 
	 * @return
	 */
	@RequestMapping(value="i.html")
	public String queryUserInfo(Model model) {
		try {
			//test code
//			this.setSessionUser(new User("402881083ab07006013ab07017220001","cwd1","cwd1"));
			User user  = (User) this.getSessionUser();
			if(user==null){
				throw new Exception("20003");
			}
			user = this.userService.queryUserInfo(user.getUserid());
			this.fillDefault(user);
			model.addAttribute("user", user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
//			model.addAttribute(ERROR_CODE_KEY, e.getMessage());
			return "index";
		}
		return "user/homepage";
	}
	
	/**
	 * 查询别人资料
	 * 
	 * @return
	 */
	@RequestMapping(value="f_{userId}.html")
	public String queryUserInfo(@PathVariable("userId") String userid,Model model) {
		try {
			User user = this.userService.queryUserInfo(userid);
			this.fillDefault(user);
			model.addAttribute("user", user);
			if(null != getSessionUser())
			    model.addAttribute("isFriend", friendsService.isFriend(userid, getSessionUser().getUserid()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute(ERROR_CODE_KEY, e.getMessage());
		}
		return "user/friendpage";
	}
	
	/**
	 * 查询活动已报名所有人员(查看更多)
	 * @param eventid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sun_{eventId}.html")
	public String listSignUpUses(@PathVariable("eventId") String eventid,Model model){
		try{
			List<Map> list;
			if(this.getSessionUser() == null || this.getSessionUser().getUserid() == null || this.getSessionUser().getUserid().length() == 0){
				list = this.userService.listSignUpUses(null,eventid);
			}else{
				list = this.userService.listSignUpUses(this.getSessionUser().getUserid(),eventid);
			}
			for(Map map:list){
				if(StringUtils.isEmpty((String)map.get("portrait"))){
					String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
					map.put("portrait", defaultpath);
				}
			}
			Event event = this.eventService.queryEventDetail(eventid);			
			model.addAttribute("list", list);
			model.addAttribute("topic", event.getTopic());
		}catch(Exception e){
			log.error(e.getMessage(), e);
			model.addAttribute(ERROR_CODE_KEY, e.getMessage());
		}
		return "/user/signUpUser";
	}
	
	/**
	 * 查询活动已参加所有人员（查看更多）
	 * @param eventid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sin_{eventId}.html")
	public String listSignInUsers(@PathVariable("eventId") String eventid,Model model){
		try{
			List<Map> list = null;
			if(this.getSessionUser() == null || this.getSessionUser().getUserid() == null || this.getSessionUser().getUserid().length() == 0){
				list = this.userService.listSignInUses(null,eventid);
			}else{
				list = this.userService.listSignInUses(this.getSessionUser().getUserid(),eventid);
			}
			for(Map map:list){
				if(StringUtils.isEmpty((String)map.get("portrait"))){
					String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
					map.put("portrait", defaultpath);
				}
			}
			Event event = this.eventService.queryEventDetail(eventid);			
			model.addAttribute("list", list);
			model.addAttribute("topic", event.getTopic());
		}catch(Exception e){
			log.error(e.getMessage(), e);
			model.addAttribute(ERROR_CODE_KEY, e.getMessage());
		}
		return "/user/signInUser";
	}

	/**
	 * 查询某活动已报名人员列表
	 * 
	 * @return json
	 */
	@RequestMapping(value="suu_{strStart}_{strSize}_{eventid}.json")
	public String querySignedUpUsers(@PathVariable("eventid") String eventid,@PathVariable("strStart") String strStart,@PathVariable("strSize") String strSize, Model model) {
		Result rs = new Result();
		try {
			int start = Integer.parseInt(strStart);
			int size = Integer.parseInt(strSize);
			Page page = this.userService.querySignedUpUsers(eventid, start, size);
			List<Map> list = page.getResult();
			for(Map map:list){
				if(StringUtils.isEmpty((String)map.get("portrait"))){
					String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
					map.put("portrait", defaultpath);
				}
				if(getSessionUser() == null || getSessionUser().getUserid() == null || getSessionUser().getUserid().length() ==0){
					//如果此用户没有登录，那么默认显示所有人都是他的好友以便隐藏加好友的+号。
					map.put("isFriend", true);
				}else{
					map.put("isFriend", friendsService.isFriend(map.get("userid").toString(), getSessionUser().getUserid()));
				}
			}
			rs.setSuccess(true);			
			rs.setContent(page);			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}

	/**
	 * 查询某活动已参加人员列表
	 * 
	 * @return json
	 */
	@RequestMapping(value="siu_{strStart}_{strSize}_{eventid}.json")
	public String querySignedInUsers(@PathVariable("eventid") String eventid,@PathVariable("strStart") String strStart,@PathVariable("strSize") String strSize,Model model) {
		long star=System.currentTimeMillis();
		Result rs = new Result();
		try {
			int start = Integer.parseInt(strStart);
			int size = Integer.parseInt(strSize);
			Page page = this.userService.querySignedInUsers(eventid, start, size);
			List<Map> list  = page.getResult();
			for(Map map:list){
				if(StringUtils.isEmpty((String)map.get("portrait"))){
					String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
					map.put("portrait", defaultpath);
				}
				if(getSessionUser() == null || getSessionUser().getUserid() == null || getSessionUser().getUserid().length() ==0){
					//如果此用户没有登录，那么默认显示所有人都是他的好友以便隐藏加好友的+号。
					map.put("isFriend", true);
				}else{
					map.put("isFriend", friendsService.isFriend(map.get("userid").toString(), getSessionUser().getUserid()));
				}
			}
			rs.setSuccess(true);
			rs.setContent(page);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		System.out.println("加载某活动已参加人员列表时间：：：："+(System.currentTimeMillis()-star));
		return "jsonview";
	}

	/**
	 * 删除教育经历
	 * 
	 * @return json
	 */
	@RequestMapping(value="dee.json")
	public String deleteEducationExperience(String educationexperienceid,Model model) {
		Result rs = new Result();
		try {
			this.userService.deleteEducationExperience(educationexperienceid);
			rs.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}

	/**
	 * 添加教育经历
	 * 
	 * @return json
	 */
	@RequestMapping(value="aee.json")
	public String addEducationExperience(String school,Model model) {
		Result rs = new Result();
		try {
			school = EscapeHtml.Html2Text(school);
			User u = this.getSessionUser();
			EducationExperience  ee = this.userService.addEducationExperience(u.getUserid(),school);
			Map<String,String> map = new HashMap<String,String>();
			map.put("educationexperienceid", ee.getEducationexperienceid());
			map.put("school", ee.getSchool().getSchool());
			map.put("logo", ee.getSchool().getLogo());
			if(StringUtils.isEmpty(map.get("logo")));{
				String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_SCHOOL_LOGO;
				map.put("logo", defaultpath);
			}
			rs.setSuccess(true);
			rs.setContent(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}	
	
	/**
	 * 删除工作经历
	 * 
	 * @return json
	 */
	@RequestMapping(value="dwe.json")
	public String deleteWorkExpeierence(String workexperienceid,Model model) {
		Result rs = new Result();
		try {
			User u = this.getSessionUser();
			String changeCurId = this.userService.deleteWorkExperience(u.getUserid(),workexperienceid);
			rs.setSuccess(true);
			if(!StringUtils.isEmpty(changeCurId)){
				rs.setContent(changeCurId);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}

	/**
	 * 添加工作经历
	 * 
	 * @return json
	 */
	@RequestMapping(value="awe.json")
	public String addWorkExpeierence(String company,String position,Model model) {
		Result rs = new Result();
		try {
			company = EscapeHtml.Html2Text(company);
			position = EscapeHtml.Html2Text(position);
			User u = this.getSessionUser();			
			WorkExperience we = this.userService.addWorkExpeierence(u.getUserid(),company,position);
			rs.setSuccess(true);
			Map<String,String> map = new HashMap<String,String>();
			map.put("workexperienceid", we.getWorkexperienceid());
			map.put("company", we.getCompany().getCompany());
			map.put("logo", we.getCompany().getLogo());
			if(StringUtils.isEmpty(map.get("logo")));{
				String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_COMPANY_LOGO;
				map.put("logo", defaultpath);
			}
			map.put("position", we.getPosition());
			map.put("current", String.valueOf(we.getCurrent()));
			rs.setContent(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 
	 * @param userid
	 * @param workexperienceid
	 * @param model
	 * @return json
	 */
	@RequestMapping(value="cjob.json")
	public String editCurrentWork(String workexperienceid,Model model){
		Result rs = new Result();
		try{
			User u = this.getSessionUser();
			this.userService.editCurrentWork(u.getUserid(),workexperienceid);
			rs.setSuccess(true);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}

	/**
	 * 编辑联系方式（包括添加、删除、修改）
	 * 
	 * @return json
	 */
	@RequestMapping(value="addContact.json")
	public String editContact(String userid,String strtype,String contact,Model model) {
		Result rs = new Result();
		try {
			int type = Integer.parseInt(strtype);
			this.userService.editContact(userid, type, contact);
			rs.setSuccess(true);
			rs.setMsg("操作成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg("操作失败！");
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 编辑个人信息 
	 * 
	 * @return json
	 */
	@RequestMapping(value="eu.json")
	public String updateBreifUserInfo(String name,String sex,String summary,Model model) {
		Result rs = new Result();
		try {	
			int intsex = Integer.parseInt(sex);
			summary = EscapeHtml.Html2Text(summary);
			User u = this.userService.updateBreifUserInfo(this.getSessionUser().getUserid(),name,intsex,summary);
			//修改session中的用户名称
			this.getSessionUser().setName(name);
			rs.setSuccess(true);
			rs.setContent(u.getCompletePercent());
		} catch (Exception e) {
			log.error(e.getMessage(), e);		
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}

	/**
	 * 上传头像
	 * 
	 * @return json
	 */
	@RequestMapping(value="/up.json")
	@ResponseBody
	public String uploadPortrait(@RequestParam MultipartFile[] portrait_file,Model model) 
	{
		Result rs = new Result();
		String filePath=CommonConstant.upload_file_system_path+"/"+CommonConstant.portait_dir_name+"/";//多加目录符可以避免拼接完整路径因为少了目录符而导致错误
		String fileName=null;
		try 
		{
			//保存上传的图片或摄像头数据流
			/*CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(this.getSession().getServletContext()); 
			if (commonsMultipartResolver.isMultipart(this.getRequest())) //上传图片
			{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) this.getRequest(); 
				MultipartFile myfile = multipartRequest.getFile((String) multipartRequest.getFileNames().next()); 
				fileName = SystemBuffer.uuidHexGenerator.generateUUID()+"."+StringUtils.getFileExtension(myfile.getOriginalFilename());
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(filePath,fileName)); 
			}
			else//拍摄头像
			{
				fileName = SystemBuffer.uuidHexGenerator.generateUUID()+".jpg";
				FileUtils.copyInputStreamToFile(this.getRequest().getInputStream(), new File(filePath, fileName));//文件名规则待定
			}*/
			if(portrait_file.length>0){
				if (portrait_file[0].isEmpty()) {
					throw new Exception("20008");
				}else{
					fileName = SystemBuffer.uuidHexGenerator.generateUUID()+"."+StringUtils.getFileExtension(portrait_file[0].getOriginalFilename());
					FileUtils.copyInputStreamToFile(portrait_file[0].getInputStream(), new File(filePath,fileName)); 
					String userid = this.getSessionUser().getUserid();
					//更新会员头像信息（数据库操作成功后会删除旧头像文件）
					String newPath = this.userService.updatePortrait(userid,filePath,fileName);
					this.getSessionUser().setPortrait(newPath);
					rs.setSuccess(true);
					rs.setContent(newPath);
				} 
			}else{
				throw new Exception("20008");
			}
		} 		
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
			//操作失败尝试删除新头像文件
			File newFile = new File(filePath, fileName);
			if(newFile.exists()){
				newFile.delete();
			}
		}
		return JsonUtil.getJsonString4JavaPOJO(rs);
	}
	
	/**
	 * 上传临时头像
	 * 
	 * @return json
	 */
	@RequestMapping(value="/upt.json")
	@ResponseBody
	@Deprecated
	public String uploadPortraitTemp(@RequestParam MultipartFile[] portrait_file) 
	{
		String filePath=CommonConstant.upload_file_system_path+"/"+CommonConstant.portait_dir_name+"/";//多加目录符可以避免拼接完整路径因为少了目录符而导致错误
		String fileName=null;
		try 
		{
			if(portrait_file.length>0){
				if (portrait_file[0].isEmpty()) {
					throw new Exception("20008");
				}else{
					fileName = SystemBuffer.uuidHexGenerator.generateUUID()+"."+StringUtils.getFileExtension(portrait_file[0].getOriginalFilename());
					FileUtils.copyInputStreamToFile(portrait_file[0].getInputStream(), new File(filePath,fileName)); 
					String newPath = CommonConstant.upload_http_path+"/"+CommonConstant.portait_dir_name+"/"+fileName;
					return newPath;
				} 
			}else{
				throw new Exception("20008");
			}
		} 		
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 头像入库
	 * 
	 * @return json
	 */
	@RequestMapping(value="/upf.json")
	@Deprecated
	public String uploadPortrait(String tempPath,Model model) 
	{
		Result rs = new Result();		
		try 
		{
			//更新会员头像信息（数据库操作成功后会删除旧头像文件）
			String userid = this.getSessionUser().getUserid();
			String newPath = this.userService.updatePortrait(userid,tempPath);
			rs.setSuccess(true);
			rs.setContent(newPath);
		} 		
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
			//操作失败尝试删除新头像文件
			String filePath = CommonConstant.upload_file_system_path+"/"+CommonConstant.portait_dir_name+"/";
			String fileName = tempPath.substring(tempPath.lastIndexOf("/")+1);
			File newFile = new File(filePath, fileName);
			if(newFile.exists()){
				newFile.delete();
			}
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 邮箱激活
	 * @return
	 */
	@RequestMapping(value="emailCheck.html")
	public String emailCheck(String code,Model model){
		try {
			User u = this.userService.emailCheck(code);
			if(StringUtils.isEmpty(u.getPortrait())){
				String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
				u.setPortrait(defaultpath);
			}
			this.setSessionUser(new User(u.getUserid(),u.getEmail(),u.getName(),u.getPortrait()));
			String indexUrl = this.getWebRoot()+"index.html";
			model.addAttribute("url", indexUrl);
//			model.addAttribute("message", "帐号激活成功，点击<a href='"+indexUrl+"'>这里</a>返回。");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute(ERROR_CODE_KEY, "20011");
		}
		return "user/emailCheck_finish";
	}
	
	public static void main(String[] args) {
		String s = "BasiS@15.com";
		System.out.println(s.toLowerCase());
	}
	
	/**
	 * 完善个人资料
	 * 废弃的功能 已经由提交报名接口实现
	 * @return
	 */
	@RequestMapping(value="fillUp")
	public String fillUpPersonalInfo() {
		return null;
	}
	
	
	/**
	 * 模糊查询用户
	 * @return json
	 */
	@RequestMapping(value="queryUserLikeName.json")
	public String queryUserLikeName(String leftName,Model model){
		Result rs = new Result();
		try {
			List<FriendVO> list = this.userService.queryUserLikeName(leftName, this.getSessionUser().getUserid());
			rs.setSuccess(true);
			rs.setContent(list);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 模糊查询公司
	 * @return json
	 */
	@RequestMapping(value="queryCompanyLikeName.json")
	public String queryCompanyLikeName(String leftName,Model model){
		Result rs = new Result();
		try{
			List<User> list = this.userService.queryCompanyLikeName(leftName);
			rs.setSuccess(true);
			rs.setContent(list);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 模糊查询学校
	 * @return json
	 */
	@RequestMapping(value="querySchoolLikeName.json")
	public String querySchoolLikeName(String leftName,Model model){
		Result rs = new Result();
		try{
			List<User> list = this.userService.querySchoolLikeName(leftName);
			rs.setSuccess(true);
			rs.setContent(list);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			rs.setSuccess(false);
			rs.setMsg(ErrorHelper.getMsg(e.getMessage()));
		}
		model.addAttribute("result", rs);
		return "jsonview";
	}
	
	/**
	 * 填充用户默认信息
	 * @param user
	 */
	private void fillDefault(User user){
		if(StringUtils.isEmpty(user.getPortrait())){
			String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_USER_PORTRAIT;
			user.setPortrait(defaultpath);
		}
		List<WorkExperience> welist = new ArrayList<WorkExperience>(user.getWorkExperiences());
		for(WorkExperience we:welist){
			if(StringUtils.isEmpty(we.getCompany().getLogo())){
				String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_COMPANY_LOGO;
				we.getCompany().setLogo(defaultpath);
			}
		}
		List<EducationExperience> eelist = new ArrayList<EducationExperience>(user.getEducationExperiences());
		for(EducationExperience ee:eelist){
			if(StringUtils.isEmpty(ee.getSchool().getLogo())){
				String defaultpath = this.getWebRoot()+"/"+ CommonConstant.Default_SCHOOL_LOGO;
				ee.getSchool().setLogo(defaultpath);
			}
		}	
	}
	
	@RequestMapping(value="qs.html")
	public String querySetting(Model model,String userId){
		try{
			User u = this.getSessionUser();
			if(u==null){
				model.addAttribute("http","out");
				u = this.userService.getUser(userId);
			}
			else{
				model.addAttribute("http","int");
				u = this.userService.getUser(u.getUserid());
			}
			model.addAttribute("user", u);
			model.addAttribute("adv",advService.queryByParking("5"));
		}catch(Exception e){
			log.error(e.getMessage(), e);
			model.addAttribute(ERROR_CODE_KEY, e.getMessage());
		}
		return "setting/setting";
	}
	
	@RequestMapping("/adv")
	public String getAdv(Model model,Integer type)
	{
	    Result result = new Result();
	    try
        {
	        type=null==type?-1:type;
	        result.setContent(advService.queryByParking(""+type));
	        result.setSuccess(true);
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
            result.setSuccess(false);
        }
	    model.addAttribute("result", result);
	    return "jsonview";
	}
}
