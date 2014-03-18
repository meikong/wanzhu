package com.wanzhu.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanzhu.base.CommonConstant;
import com.wanzhu.base.Page;
import com.wanzhu.dao.CompanyDao;
import com.wanzhu.dao.EducationExperienceDao;
import com.wanzhu.dao.SchoolDao;
import com.wanzhu.dao.UserDao;
import com.wanzhu.dao.UserEventDao;
import com.wanzhu.dao.WorkExpeierenceDao;
import com.wanzhu.entity.Company;
import com.wanzhu.entity.EducationExperience;
import com.wanzhu.entity.School;
import com.wanzhu.entity.User;
import com.wanzhu.entity.WorkExperience;
import com.wanzhu.jsonvo.FriendVO;
import com.wanzhu.utils.MD5;
import com.wanzhu.utils.PinyinUtil;
import com.wanzhu.utils.SmtpClient;
import com.wanzhu.utils.TemplateHelper;

@Service
@Transactional(readOnly = true)
public class UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private EducationExperienceDao educationExperienceDao;
	@Autowired
	private WorkExpeierenceDao workexpeierenceDao;
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private UserEventDao userEventDao;
	
	/**
	 * 是否受限用户
	 * @param userId
	 * @return
	 */
	public boolean isLimitedUser(String userId) {
		return this.userDao.isLimitedUser(userId);
	}
	
	/**
	 * 登录身份验证
	 * 验证用户名、密码、邮箱帐号是否激活
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public User login(String email, String password) throws Exception {
		String hql = "from User as u where u.email=? and u.password=?";
		List<User> list = (List<User>) userDao.find(hql,new Object[]{email,password});
		if(list!=null && list.size()>0){
			User u = list.get(0);
			if(u.getActivated()==0){
				throw new Exception("20005");
			}
			return u;
		}			
		return null;
	}

	/**
	 * 密码重置
	 * @param email
	 * @throws  
	 * @throws  
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void resetPassword(String email) throws Exception{
		email = email.toLowerCase();
		String hql = "from User as u where u.email=? and u.activated=1";
		User u =  this.userDao.findEntity(hql, new Object[]{email});
		if(u==null){
			throw new Exception("20002");
		}
		Random r = new Random();
		String newPassword = ""+Math.abs(r.nextInt());
		String md5Password = MD5.convert(newPassword);
		u.setPassword(md5Password);
		this.userDao.update(u);		
		//发送新密码到对方邮箱
		//合并邮件模版passwordreset.tpl，并发送邮件
		Map<String, String> pairs =new HashMap<String, String>();
		pairs.put("name", u.getName());
		pairs.put("newPassword", newPassword);
		pairs.put("userId", u.getUserid()); //xu 4.2
		StringBuffer sb=TemplateHelper.merge("passwordreset.tpl", pairs);
		String content = sb.toString();
		SmtpClient.sendMail( CommonConstant.recommendation_mail_from_account, 
				CommonConstant.recommendation_mail_from_account,
				CommonConstant.recommendation_mail_from_name,
				CommonConstant.recommendation_mail_from_account_password,
				email,
				CommonConstant.smtp_server,
				CommonConstant.reset_password_mail_subject,
				content,true);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void editPassword(String userid, String oldpwd,String newpwd,String newrepwd) throws Exception {
		User u = this.userDao.get(userid);
		this.assertUserExist(u, userid);
		if(!u.getPassword().equals(oldpwd)){
			throw new Exception("20009");
		}
		if(!newpwd.equals(newrepwd)){
			throw new Exception("20010");
		}
		u.setPassword(newpwd);
		this.userDao.update(u);
	}
	
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void register(User user) throws Exception {
		 user.setPersonalemail(user.getEmail());
		 user.setNamespy(PinyinUtil.converterToFirstSpell(user.getName()));
		 //保存前服务端再校验下是否已经存在对应的用户
		 String hql = "from User as u where u.email=?";
		 User u = this.userDao.findEntity(hql, new Object[]{user.getEmail()});
		 if(u!=null){
			 throw new Exception("20001");
		 }
		this.userDao.save(user);		
	}

	/**
	 * @param userid
	 */
	public User queryUserInfo(String userid) {
		return this.userDao.get(userid);
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void fillUpPersonalInfo(String userid,String name,String company,String position,String phone,String personalemail) throws Exception {
		//更新用户信息
		User u = this.userDao.get(userid);
		this.assertUserExist(u, userid);
		u.setName(name);
		u.setNamespy(PinyinUtil.converterToFirstSpell(u.getName()));
		u.setPhone(phone);
		u.setPersonalemail(personalemail);
		this.userDao.update(u);
		//更新当前工作经历		
		//判断是否需要添加公司（根据公司名称）
		String hql = "from Company as c where c.company=?";
		Company c = this.companyDao.findEntity(hql, new Object[]{company});
		if(c==null){
			c = new Company();
			c.setCompany(company);
			this.companyDao.save(c);
		}
		//判断是否需要添加新的工作经历（根据会员id、公司名称和职位）
		hql = "from WorkExperience as we where we.user.userid=? and we.company.company=? and we.position=?";
		List<WorkExperience> list = this.workexpeierenceDao.find(hql, new Object[]{userid,company,position});
		boolean hasCurrent = false;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getCurrent()==1){
				hasCurrent = true;
				break;
			}
		}
		if(list==null || list.size()==0){
			//不存在工作经历，清洗当前工作，创建新的工作经历并置为当前工作
			Map<String,String> updateValue= new HashMap();
			updateValue.put("current", "0");
			Map<String,String> conditionValue = new HashMap();
			conditionValue.put("user.userid", userid);
			conditionValue.put("current","1");
			this.workexpeierenceDao.update(updateValue, conditionValue);
			WorkExperience we = new WorkExperience();
			we.setUser(u);
			we.setCompany(c);
			we.setPosition(position);
			we.setCurrent(1);
			this.workexpeierenceDao.save(we);
		}else{
			if(!hasCurrent){
				//存在相同工作经历但不是当前的，清洗当前工作，置现在的工作经历为当前的
				Map<String,String> updateValue= new HashMap();
				updateValue.put("current", "0");
				Map<String,String> conditionValue = new HashMap();
				conditionValue.put("user.userid", userid);
				conditionValue.put("current","1");
				this.workexpeierenceDao.update(updateValue, conditionValue);
				WorkExperience we = list.get(0);
				we.setCurrent(1);
				this.workexpeierenceDao.update(we);
			}
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void deleteEducationExperience(String educationexperienceid) {
		Map conditionValue = new HashMap();
		conditionValue.put("educationexperienceid", educationexperienceid);
		this.educationExperienceDao.delete(conditionValue);		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public EducationExperience addEducationExperience(String userid,String school) throws Exception {
		String hql = "from School as s where s.school = ?";		
		School s = this.schoolDao.findEntity(hql, new Object[]{school});
		if(s==null){
			s= new School();
			s.setSchool(school);
			this.schoolDao.save(s);
		}
		User u = this.userDao.get(userid);
		this.assertUserExist(u, userid);
		EducationExperience ex = new EducationExperience();
		ex.setUser(u);
		ex.setSchool(s);
		this.educationExperienceDao.save(ex);
		return ex;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public String deleteWorkExperience(String userid,String workexperienceid) {
		Map<String,String> result = new HashMap();
		//删除指定的工作经历
		Map conditionValue = new HashMap();
		conditionValue.put("workexperienceid", workexperienceid);
		this.workexpeierenceDao.delete(conditionValue);
		//重新定位当前公司，如果存在就不设置，存在就把第一设置成当前单位
		String hql = "from WorkExperience as we where we.user.userid=? order by we.workexperienceid desc";
		List<WorkExperience> list = this.workexpeierenceDao.find(hql, new Object[]{userid});
		String changeCurId = null; //需要变更成当前单位的工作经历id
		String curId = null;//当前单位的工作经历id
		for(int i=0;i<list.size();i++){
			if(((WorkExperience)list.get(i)).getCurrent()==1){
				curId =( (WorkExperience)list.get(i)).getWorkexperienceid();
				break;
			}
		}
		if(curId==null){
			//删除的是当前公司
			if(list.size()>0){
				//需要更换剩下的第一个工作经历为当前单位
				WorkExperience we = (WorkExperience)list.get(0);
				we.setCurrent(1);
				this.workexpeierenceDao.update(we);
				changeCurId = we.getWorkexperienceid();
			}
		}
		return changeCurId;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public WorkExperience addWorkExpeierence(String userid,String company,String position) throws Exception {
		String hql = "from Company as c where c.company=?";
		Company c = this.companyDao.findEntity(hql, new Object[]{company});
		if(c==null){
			c = new Company();
			c.setCompany(company);
			this.companyDao.save(c);
		}
		User u = this.userDao.get(userid);
		this.assertUserExist(u, userid);		
		WorkExperience we = new WorkExperience();
		we.setUser(u);
		we.setCompany(c);
		we.setPosition(position);
		 Set<WorkExperience>  wes = u.getWorkExperiences();
		if(wes!=null && wes.size()>0){
			we.setCurrent(0);
		}else{
			we.setCurrent(1);
		}
		this.workexpeierenceDao.save(we);		
		return we;
	}

	/**
	 * 编辑联系（电话或邮箱）
	 * 包括新增、修改、删除联系
	 * 
	 * @param userid
	 * @param type
	 * @param contact (当联系方式内容为null或空表示删除联系)
	 * @throws Exception
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void editContact(String userid,int type,String contact) throws Exception {
		User u = this.userDao.get(userid);
		this.assertUserExist(u, userid);
		if(contact==null){
			contact = "";
		}
		if(type==0){
			//电话
			u.setPhone(contact);
		}else if(type==1){
			//邮箱
			if(StringUtils.isEmpty(contact)){
				throw new Exception("邮箱不允许为空！");
			}
			u.setPersonalemail(contact);
		}
		this.userDao.update(u);		
	}
	
	public Page querySignedUpUsers(String eventid, int start,int  size) {
	String hql = "select new Map(ue.user.userid as userid,ue.user.name as name,ue.user.portrait as portrait,ue.user.weibo as weibo, " +
				"coalesce((select DISTINCT we.company.company from WorkExperience we where we.user=ue.user and we.current=1),'') as company," +
				"coalesce((select DISTINCT we.position from WorkExperience we where we.user=ue.user and we.current=1),'') as position) from UserEvent ue where ue.event.eventid=? and ue.audit=1 order by ue.user.name asc";
		List list = this.userEventDao.searchMore(hql, new Object[]{eventid}, start, size);		
		String countHql = "select count(*) from UserEvent ue where ue.event.eventid=?  and ue.audit=1";
		long total = (Long) this.userEventDao.getHibernateTemplate().find(countHql, new Object[]{eventid}).get(0);
		Page  page = new Page();
		page.setTotalCount(total);
		page.setResult(list);
		return page;
	}

	public Page querySignedInUsers(String eventid, int start,int  size) {
		//xu 4.8
		
		String hql = "select new Map(ue.user.userid as userid,ue.user.name as name,ue.user.portrait as portrait,ue.user.weibo as weibo," +
				"coalesce((select DISTINCT we.company.company from WorkExperience we where we.user=ue.user and we.current=1),'') as company," +
				"coalesce((select DISTINCT we.position from WorkExperience we where we.user=ue.user and we.current=1),'') as position) from UserEvent ue where ue.event.eventid=? and ue.signup=1 order by ue.user.name asc";
		List list = this.userEventDao.searchMore(hql, new Object[]{eventid}, start, size);
		String countHql = "select count(*) from UserEvent ue where ue.event.eventid=? and ue.signup=1";
		long total = (Long) this.userEventDao.getHibernateTemplate().find(countHql, new Object[]{eventid}).get(0);
		Page  page = new Page();
		page.setTotalCount(total);
		page.setResult(list);
		return page;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void editCurrentWork(String userid,String workexperienceid) {
		//为用户清洗当前工作
		Map updateValue = new HashMap();
		updateValue.put("current", "0");
		Map conditionValue = new HashMap();
		conditionValue.put("user.userid", userid);
		conditionValue.put("current", "1");
		this.workexpeierenceDao.update(updateValue, conditionValue);
		//为用户设置最新的当前工作
		updateValue = new HashMap();
		updateValue.put("current", "1");
		conditionValue = new HashMap();
		conditionValue.put("workexperienceid", workexperienceid);
		this.workexpeierenceDao.update(updateValue, conditionValue);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public User updateBreifUserInfo(String userid, String name,int sex,
			String summary) throws Exception {
		User u = this.userDao.get(userid);
		this.assertUserExist(u, userid);
		u.setName(name);
		u.setNamespy(PinyinUtil.converterToFirstSpell(u.getName()));
	/*	u.setPhone(phone);
		u.setPersonalemail(personalemail);*/
		u.setSex(sex);
		u.setSummary(summary);
		this.userDao.update(u);
		return u;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public String updatePortrait(String userid, String filePath, String fileName) throws Exception {
		User u = this.userDao.get(userid);
		this.assertUserExist(u, userid);
		String oldFilePath = u.getPortrait();//旧图像的web全路径
		//更新会员表
		String newFilePath = CommonConstant.upload_http_path+"/"+ CommonConstant.portait_dir_name+"/"+fileName;
		u.setPortrait(newFilePath);
		this.userDao.update(u);
		//删除旧文件			
		if(!StringUtils.isEmpty(oldFilePath)){
			String oldFileName = oldFilePath.substring(oldFilePath.lastIndexOf("/")+1);
			File oldFile = new File(filePath,oldFileName);
			if(oldFile.exists()){
				oldFile.delete();
			}
		}
		return newFilePath;
	}
	
	public User getUser(String userid){
		return this.userDao.get(userid);
	}
	/**
	 * 断言用户存在
	 * @param u
	 * @param userid
	 * @throws Exception
	 */
	private void assertUserExist(User u,String userid) throws Exception{
		if(u==null){
			throw new Exception("用户id为【"+userid+"】的用户不存在！");
		}
	}
	
	/**
	 * 获取全部处于激活状态的会员
	 */
	public List<User> queryAllActivatedUsers()
	{
		return this.userDao.queryAllActivatedUsers();
	}

	/**
	 * 激活邮箱帐号
	 * @param userid
	 * @throws Exception 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public User emailCheck(String userid) throws Exception {
		User u = this.getUser(userid);
		this.assertUserExist(u, userid);
		u.setActivated(1);
		this.userDao.update(u);
		return u;
	}
	
	/**
	 * 根据第三方帐号获取本地用户
	 * @param oauthid
	 */
	public User getUserByOauth(String oauthid) throws Exception{
		String hql = "select new User(u.userid,u.email,u.name,u.portrait) from User as u where u.oauthid=?";
		return this.userDao.findEntity(hql, new Object[]{oauthid});	
	}

	/**
	 * 第三方绑定本地帐号
	 * 1、判断是否存在对应的邮箱帐号
	 * 1.1存在邮箱帐号判断是否密码正确
	 * 1.1.1密码正确则绑定
	 * 1.1.2密码错误则提示帐号密码错误
	 * 1.2不存在邮箱帐号创建本地帐号来绑定
	 * @param email
	 * @param password
	 * @param name
	 * @param oauthid
	 * @param weibo
	 * @return 
	 * @throws Exception
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public User bindLocalAccount(String email,String password,String name,String oauthid,String weibo,String portrait) throws Exception {
		//
		String hql = "from User as u where u.email=?";
		User u = this.userDao.findEntity(hql, new Object[]{email});	
		if(u==null){
			//不存在匹配的邮箱帐号，创建本地帐号来绑定		
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			user.setName(name);
			user.setNamespy(PinyinUtil.converterToFirstSpell(user.getName()));
			user.setCreatetime(new Date());
			user.setState(1);//注册审核状态为不受限
			user.setActivated(1);//激活状态为激活
			user.setSource(CommonConstant.OAUTH_SINA);//用户来源
			user.setOauthid(oauthid);
			user.setWeibo(weibo);//绑定微博个人地址
			user.setPortrait(portrait);//绑定微博头像
			user.setSex(2);//性别默认男
			user.setPersonalemail(user.getEmail());	
			user.setEventscount(0);
			user.setFriendscount(0);
			user.setSummary(CommonConstant.DEFAULT_SUMMARY);
			this.userDao.save(user);		
			return user;
		}else{
			//存在匹配的邮箱帐号
			if(u.getPassword().equals(password)){
				//帐户匹配完全，进行绑定
				u.setName(name);
				u.setNamespy(PinyinUtil.converterToFirstSpell(u.getName()));
				u.setOauthid(oauthid);
				u.setWeibo(weibo);
				//没有头像则自动绑定微博的头像
				if(StringUtils.isEmpty(u.getPortrait())){
					u.setPortrait(portrait);
				}
				u.setActivated(1);
				u.setSource(CommonConstant.OAUTH_SINA);
				this.userDao.update(u);
				return u;
			}else{
				//密码错误，返回帐号密码错误编号
				throw new Exception("20006");
			}
		}
	}

	/**
	 * 绑定oauth帐户
	 * 以下情况不允许绑定
	 * 1、要绑定的oauth账号已存在
	 * 2、已绑定到另一个oauth账户
	 * @param oauthid
	 * @throws Exception 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public User bindOAuthAccount(String userid,String origin,String oauthid,String weibo,String portrait) throws Exception {
		String hql = "from User as u where u.oauthid=? or (u.userid=? and u.source=?)";
		List<User> list = this.userDao.find(hql, new Object[]{oauthid,userid,origin});
		if(list!=null && list.size()>0){
			throw new Exception("20012");			
		}
		hql = "from User as u where u.userid=?";
		User u = this.userDao.findEntity(hql, new Object[]{userid});
		this.assertUserExist(u, userid);
		u.setOauthid(oauthid);
		u.setSource(origin);
		u.setWeibo(weibo);
		//没有头像则自动绑定微博的头像
		if(StringUtils.isEmpty(u.getPortrait())){
			u.setPortrait(portrait);
		}
		this.userDao.update(u);
		return u;
	}

	/**
	 * 解除绑定oauth帐户
	 * @param userid
	 * @throws Exception 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void unbindOAuthAccount(String userid) throws Exception {
		String hql = "from User as u where u.userid=?";
		User u = this.userDao.findEntity(hql, new Object[]{userid});
		this.assertUserExist(u, userid);
		u.setOauthid("");
		u.setSource("3wcoffee");
		this.userDao.update(u);
	}

	public List<FriendVO> queryUserLikeName(String leftName, String userId) {
		String sql = "select cast(u.userid as char(32)), u.name, u.portrait " +
			     "from t_user u left join t_friends f on (u.userid = f.friendid or u.userid = f.userid) " +
			     "where u.name like '" + leftName + "%' and u.activated = 1 and (f.userid = '" + userId + "' or f.friendid = '" + userId + "') and u.userid != '" + userId + "'";
		List<Object[]> list = this.userDao.getSession().createSQLQuery(sql).list();
		List<FriendVO> result = new ArrayList<FriendVO>(list.size());
		for(int i = 0; i < list.size(); i++) {
			FriendVO friend = new FriendVO();
			friend.setUserId((String)list.get(i)[0]);
			friend.setUsername((String)list.get(i)[1]);
			friend.setPortrait(list.get(i)[2] == null ? "" : (String)list.get(i)[2]);
			result.add(friend);
		}
		return result;
	}

	/**
	 * 图像改为正式，并入库更新
	 * @param userid
	 * @param tempPath
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Deprecated
	public String updatePortrait(String userid, String newFilePath) throws Exception {
		User u = this.userDao.get(userid);
		this.assertUserExist(u, userid);
		String oldFilePath = u.getPortrait();//旧图像的web全路径
		//更新会员表
		u.setPortrait(newFilePath);
		this.userDao.update(u);
		//删除旧文件			
		if(!StringUtils.isEmpty(oldFilePath)){
			String oldFileName = oldFilePath.substring(oldFilePath.lastIndexOf("/")+1);
			String filePath = CommonConstant.upload_file_system_path+"/"+CommonConstant.portait_dir_name+"/";
			File oldFile = new File(filePath,oldFileName);
			if(oldFile.exists()){
				oldFile.delete();
			}
		}
		return newFilePath;
	}

	public List<User> queryCompanyLikeName(String leftName) {
		String hql = "select new Map(c.company as company) from Company as c where c.company like ?";
		return this.userDao.getHibernateTemplate().find(hql, new Object[]{leftName+"%"});
	}

	public List<User> querySchoolLikeName(String leftName) {
		String hql = "select new Map(s.school as school) from School as s where s.school like ?";
		return this.userDao.getHibernateTemplate().find(hql, new Object[]{leftName+"%"});
	}

	public List listSignUpUses(String curUserid,String eventid) {
		String hql = "";
		List list = null;
		if(curUserid != null){//用户已经登录
			hql = "select new Map(ue.event.topic as topic,ue.user.userid as userid,ue.user.name as name,ue.user.portrait as portrait,ue.user.friendscount as friendscount,ue.user.eventscount as eventscount," +
				"(select count(*)  from Friends f where (f.userByFriendid.userid=ue.user.userid and f.userByUserid.userid=?) or (f.userByFriendid.userid=? and f.userByUserid.userid=ue.user.userid)) as isfriend," +
				"coalesce((select DISTINCT we.company.company from WorkExperience we where we.user=ue.user and we.current=1),'') as company," +
				"coalesce((select DISTINCT we.position from WorkExperience we where we.user=ue.user and we.current=1),'') as position) from UserEvent ue where ue.event.eventid=?  and ue.audit=1 order by ue.user.name asc";
			list = this.userEventDao.getHibernateTemplate().find(hql, new Object[]{curUserid,curUserid,eventid});		
		}else{
			hql = "select new Map(ue.event.topic as topic,ue.user.userid as userid,ue.user.name as name,ue.user.portrait as portrait,ue.user.friendscount as friendscount,ue.user.eventscount as eventscount," +
				"(select count(*)  from Friends f where (f.userByFriendid.userid=ue.user.userid) or (f.userByUserid.userid=ue.user.userid)) as isfriend," +
				"coalesce((select DISTINCT we.company.company from WorkExperience we where we.user=ue.user and we.current=1),'') as company," +
				"coalesce((select DISTINCT we.position from WorkExperience we where we.user=ue.user and we.current=1),'') as position) from UserEvent ue where ue.event.eventid=?  and ue.audit=1 order by ue.user.name asc";
			list = this.userEventDao.getHibernateTemplate().find(hql, new Object[]{eventid});		
		}
		return list;
	}
	
	public List listSignInUses(String curUserid,String eventid) {
		String hql = "";
		List list = null;
		if(curUserid == null){//用户已经登录
			hql = "select new Map(ue.event.topic as topic,ue.user.userid as userid,ue.user.name as name,ue.user.portrait as portrait,ue.user.friendscount as friendscount,ue.user.eventscount as eventscount," +
					"(select count(*)  from Friends f where (f.userByFriendid.userid=ue.user.userid and f.userByUserid.userid=?) or (f.userByFriendid.userid=? and f.userByUserid.userid=ue.user.userid)) as isfriend," +
					"coalesce((select DISTINCT we.company.company from WorkExperience we where we.user=ue.user and we.current=1),'') as company," +
					"coalesce((select DISTINCT we.position from WorkExperience we where we.user=ue.user and we.current=1),'') as position) from UserEvent ue where ue.event.eventid=? and ue.signup=1 order by ue.user.name asc";
			list = this.userEventDao.getHibernateTemplate().find(hql, new Object[]{curUserid,curUserid,eventid});		
		}else{
			hql = "select new Map(ue.event.topic as topic,ue.user.userid as userid,ue.user.name as name,ue.user.portrait as portrait,ue.user.friendscount as friendscount,ue.user.eventscount as eventscount," +
					"(select count(*)  from Friends f where (f.userByFriendid.userid=ue.user.userid) or (f.userByUserid.userid=ue.user.userid)) as isfriend," +
					"coalesce((select DISTINCT we.company.company from WorkExperience we where we.user=ue.user and we.current=1),'') as company," +
					"coalesce((select DISTINCT we.position from WorkExperience we where we.user=ue.user and we.current=1),'') as position) from UserEvent ue where ue.event.eventid=? and ue.signup=1 order by ue.user.name asc";
			list = this.userEventDao.getHibernateTemplate().find(hql, new Object[]{eventid});		
		}
				
		
		return list;
	}
}
