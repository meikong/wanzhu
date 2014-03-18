package com.wanzhu.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.wanzhu.base.CommonConstant;

/**
 * User entity. 
 */
@Entity
@Table(name = "t_user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 9003499418758757253L;
	private String companyName;
	private String position;
	private String userid;
	private String name;
	private String email;
	private String password;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	private String source;
	private String oauthid;
	private Integer state;
	private Integer sex;//0-保密；1-女；2-男
	private String portrait;
	private String summary;
	private Integer activated;//是否激活 0-非；1-是
	@Column(name = "phone", nullable = true, length = 32)
	private String phone;
	@Column(name = "personalemail", nullable = true, length = 128)
	private String personalemail;
	@Column(name = "weibo", nullable = true, length = 128)
	private String weibo;
	private Integer friendscount;
	private Integer eventscount;
	private String namespy;
	
	
	private Set<EducationExperience> educationExperiences = new HashSet<EducationExperience>(0);
	private Set<Friends> friendsesForFriendid = new HashSet<Friends>(0);
	private Set<Message> messagesForFriendid = new HashSet<Message>(0);
	private Set<Topic> topics = new HashSet<Topic>(0);
	private Set<Friends> friendsesForUserid = new HashSet<Friends>(0);
	private Set<PersonalActivity> personalActivities = new HashSet<PersonalActivity>(
			0);
	private Set<Message> messagesForUserid = new HashSet<Message>(0);
	private Set<Lectrue> lectrues = new HashSet<Lectrue>(0);
	private Set<WorkExperience> workExperiences = new HashSet<WorkExperience>(0);
	private Set<UserEvent> userEvents = new HashSet<UserEvent>(0);
	private Set<FriendActivity> friendActivitiesForUserid = new HashSet<FriendActivity>(
			0);
	private Set<DeclareLog> declareLogs = new HashSet<DeclareLog>(0);
	private Set<FriendLog> frientLogsForFriendid = new HashSet<FriendLog>(0);
	private Set<FriendLog> frientLogsForUserid = new HashSet<FriendLog>(0);
	private Set<Notification> notifications = new HashSet<Notification>(0);
	private Set<Remark> remarks = new HashSet<Remark>(0);
	
	public User(){		
	}
	
	public User(String userid) {
		this.userid = userid;
	}
	public User(String userid,String email){
		this.userid=userid;
		this.email=email;
	}
	public User(String userid,String email,String name){
		this.userid=userid;
		this.email=email;
		this.name=name;
	}
	
	public User(String userid,String email,String name,String portrait){
		this.userid=userid;
		this.email=email;
		this.name=name;
		this.portrait=portrait;
	}
	// xu 4.2
	public User(String userid,String email,String name,String portrait,String password){
		this.userid=userid;
		this.email=email;
		this.name=name;
		this.portrait=portrait;
		this.password=password;
	}
	// xu 4.2
		public User(String userid,String email,String name,String portrait,String password,String companyName,String position){
			this.userid=userid;
			this.email=email;
			this.name=name;
			this.portrait=portrait;
			this.password=password;
			this.companyName=companyName;
			this.position=position;
		}
		
	// zhang 5.24
	public User(String userid,String email,String name,String portrait,String password,String companyName,String position, Integer state){
		this.userid=userid;
		this.email=email;
		this.name=name;
		this.portrait=portrait;
		this.password=password;
		this.companyName=companyName;
		this.position=position;
		this.state = state;
	}
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "userid", unique = true, nullable = false, length = 32)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", unique = true, nullable = false, length = 128)
	public String getEmail() {
		if(email!=null){
			email = email.toLowerCase();
		}
		return this.email;
	}

	public void setEmail(String email) {
		if(email!=null){
			email = email.toLowerCase();
		}
		this.email = email;
	}

	@Column(name = "password", nullable = false, length = 128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "createtime", nullable = false, length = 29)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "source", nullable = false, length = 24)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "oauthid", length = 64)
	public String getOauthid() {
		return this.oauthid;
	}

	public void setOauthid(String oauthid) {
		this.oauthid = oauthid;
	}

	@Column(name = "state", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "sex", nullable = false)
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "portrait", length = 256)
	public String getPortrait() {
		return this.portrait;
		/*if(StringUtils.isEmpty(this.portrait)){
			return CommonConstant.Default_USER_PORTRAIT;
		}else{
			
		}	*/	
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	@Column(name = "summary")
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Column(name = "friendscount", nullable = false)
	public Integer getFriendscount() {
		return this.friendscount;
	}

	public void setFriendscount(Integer friendscount) {
		this.friendscount = friendscount;
	}
	
	@Column(name = "eventscount", nullable = false)
	public Integer getEventscount() {
		return this.eventscount;
	}

	public void setEventscount(Integer eventscount) {
		this.eventscount = eventscount;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	@OrderBy(value="educationexperienceid desc")
	public Set<EducationExperience> getEducationExperiences() {
		return this.educationExperiences;
	}

	public void setEducationExperiences(
			Set<EducationExperience> educationExperiences) {
		this.educationExperiences = educationExperiences;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFriendid")
	public Set<Friends> getFriendsesForFriendid() {
		return this.friendsesForFriendid;
	}

	public void setFriendsesForFriendid(Set<Friends> friendsesForFriendid) {
		this.friendsesForFriendid = friendsesForFriendid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFriendid")
	public Set<Message> getMessagesForFriendid() {
		return this.messagesForFriendid;
	}

	public void setMessagesForFriendid(Set<Message> messagesForFriendid) {
		this.messagesForFriendid = messagesForFriendid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByUserid")
	public Set<Friends> getFriendsesForUserid() {
		return this.friendsesForUserid;
	}

	public void setFriendsesForUserid(Set<Friends> friendsesForUserid) {
		this.friendsesForUserid = friendsesForUserid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<PersonalActivity> getPersonalActivities() {
		return this.personalActivities;
	}

	public void setPersonalActivities(Set<PersonalActivity> personalActivities) {
		this.personalActivities = personalActivities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByUserid")
	public Set<Message> getMessagesForUserid() {
		return this.messagesForUserid;
	}

	public void setMessagesForUserid(Set<Message> messagesForUserid) {
		this.messagesForUserid = messagesForUserid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Lectrue> getLectrues() {
		return this.lectrues;
	}

	public void setLectrues(Set<Lectrue> lectrues) {
		this.lectrues = lectrues;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	@OrderBy(value="current desc,workexperienceid desc")
	public Set<WorkExperience> getWorkExperiences() {
		return this.workExperiences;
	}

	public void setWorkExperiences(Set<WorkExperience> workExperiences) {
		this.workExperiences = workExperiences;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserEvent> getUserEvents() {
		return this.userEvents;
	}

	public void setUserEvents(Set<UserEvent> userEvents) {
		this.userEvents = userEvents;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByUserid")
	public Set<FriendActivity> getFriendActivitiesForUserid() {
		return this.friendActivitiesForUserid;
	}

	public void setFriendActivitiesForUserid(
			Set<FriendActivity> friendActivitiesForUserid) {
		this.friendActivitiesForUserid = friendActivitiesForUserid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<DeclareLog> getDeclareLogs() {
		return this.declareLogs;
	}

	public void setDeclareLogs(Set<DeclareLog> declareLogs) {
		this.declareLogs = declareLogs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFriendid")
	public Set<FriendLog> getFrientLogsForFriendid() {
		return this.frientLogsForFriendid;
	}

	public void setFrientLogsForFriendid(Set<FriendLog> frientLogsForFriendid) {
		this.frientLogsForFriendid = frientLogsForFriendid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByUserid")
	public Set<FriendLog> getFrientLogsForUserid() {
		return this.frientLogsForUserid;
	}

	public void setFrientLogsForUserid(Set<FriendLog> frientLogsForUserid) {
		this.frientLogsForUserid = frientLogsForUserid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Notification> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Remark> getRemarks() {
		return this.remarks;
	}

	public void setRemarks(Set<Remark> remarks) {
		this.remarks = remarks;
	}

	@Column(name = "activated", nullable = false)
	public Integer getActivated() {
		return activated;
	}

	public void setActivated(Integer activated) {
		this.activated = activated;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPersonalemail() {
		if(personalemail!=null){
			personalemail = personalemail.toLowerCase();
		}
		return personalemail;
	}

	public void setPersonalemail(String personalemail) {
		if(personalemail!=null){
			personalemail = personalemail.toLowerCase();
		}
		this.personalemail = personalemail;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	
	@Transient
	public String getSexName(){
		if(this.sex==0){
			return "保密";
		}else if(this.sex==1){
			return "女";
		}else if(this.sex==2){
			return "男";
		}else{
			return "未知";
		}
	}
	
	/**
	 * 获取资料完善度
	 * 基础分：40分——姓名、账户和密码、个人邮箱、性别。
	 * 其他分：60分——头像、一句话介绍、工作经历、教育经历、手机、微博。
	 * @return
	 */
	@Transient
	public String getCompletePercent(){
		int point = 40;//姓名、帐户和密码、个人邮箱、性别
		if(!StringUtils.isEmpty(this.portrait)){
			if(!this.portrait.contains(CommonConstant.Default_USER_PORTRAIT)){
				point+=10;
			}
		}
		if(!StringUtils.isEmpty(this.summary)){
			point+=10;
		}
		if(!StringUtils.isEmpty(this.phone)){
			point+=10;
		}
		if(this.workExperiences!=null && this.workExperiences.size()>0){
			point+=10;
		}
		if(this.educationExperiences!=null && this.educationExperiences.size()>0){
			point+=10;
		}
		if(!StringUtils.isEmpty(this.weibo)){
			point+=10;
		}
		return point+"%";
	}
	@Column(name = "namespy", nullable = true, length = 64)
    public String getNamespy()
    {
        return namespy;
    }

    public void setNamespy(String namespy)
    {
        this.namespy = namespy;
    }
    @Transient
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Transient
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	

}