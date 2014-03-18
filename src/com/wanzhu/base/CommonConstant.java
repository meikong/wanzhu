package com.wanzhu.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;

/**
 * 整个应用通用的常量 <br>
 * <b>类描述:</b>
 * 
 * @see
 * @since
 */
public class CommonConstant{
	public static final int DELETED = 0;
	public static final int VALID = 1;
	
	public static final int SUCCESS = 0;
	public static final int FAILURE = 1;
	public static final String COOKIE_USER_EMAIL = "3wcoffeeuseremail";
	public static final String COOKIE_PASSWORD = "3wcoffeepassword";
	public static final String COOKIE_AUTOLOGIN = "3wcoffeeautologin";
	
	public static String CONTEXT_PATH = "/";
	/**
	 * 素材类型-视频
	 */
	public static final int SUMMARY_OF_VIDEO = 0;
	/**
	 * 素材类型-PPT
	 */
	public static final int SUMMARY_OF_PPT = 1;
	/**
	 * 素材类型-文字摘要
	 */
	public static final int SUMMARY_OF_ABSTRCT = 2;
	/**
	 * 素材类型-海报
	 */
	public static final int SUMMARY_OF_POSTER = 3;
	
	/**
	 * 活动状态：不接收报名状态
	 */
	public static final int STATUS_EVENT_UNACCEPT_SIGNUP = 0;
	/**
	 * 活动状态：接受报名状态
	 */
	public static final int STATUS_EVENT_ACCEPT_SIGNUP = 1;
	/**
	 * 活动状态：结束状态
	 */
	public static final int STATUS_EVNET_END = 2;
	
	/**
	 * [个人动态类型]报名了.....活动
	 */
	public static final int PERSONALACTIVITY_EVENT_TYPE= 1;
	/**
     * [个人动态类型]发表了....话题
     */
	public static final int PERSONALACTIVITY_PUBlLISHIED_TOPIC_TYPE = 2;
	/**
     * [个人动态类型] 赞同了.....话题
     */
	public static final int PERSONALACTIVITY_AGREE_TOPIC_TYPE = 3;
	/**
     * [个人动态类型]和.....成了好友
     */
	public static final int PERSONALACTIVITY_FRIENDRELATIONSHIP_TYPE = 4;
	/**
     * [个人动态类型]参与了.....话题
     */
	public static final int PERSONALACTIVITY_PARTICIPATE_TOPIC_TYPE = 5;
    /**
     * [好友动态类型]报名参加了...活动
     */
	public static final int FRIENDACTIVITY_EVENT_TYPE = 1;
	/**
     * [好友动态类型]参与了...话题
     */
	public static final int FRIENDACTIVITY_PARTICIPATE_TOPIC_TYPE = 2;
	/**
     * [好友动态类型]发表了...话题
     */
	public static final int FRIENDACTIVITY_PUBlLISHIED_TOPIC_TYPE  = 3;
	
	/**
	 * [通知类型]报名活动通过或没通过
	 */
	public static final int NOTIFICATION_EVENT_TYPE=1;
	/**
     * [通知类型]别人请求添加你为好友
     */
	public static final int NOTIFICATION_ADDFRIEND_TYPE=2;
	/**
     * [通知类型]别人拒绝或同意了你的好友申请
     */
	public static final int NOTIFICATION_DISORAGREE_FRIENDRELATIONSHIP_TYPE=3;
	/**
     * [通知类型]别人赞同了你的话题
     */
	public static final int NOTIFICATION_AGREE_TOPIC_TYPE =4;
	/**
     * [通知类型]别人回复了你
     */
	public static final int NOTIFICATION__REPLY_TOPIC_TYPE =5;
	
	public static final String DEFAULT_SUMMARY = "这家伙很懒，什么也没写";
	
	public static final String OAUTH_3WCOFFEE = "3wcoffee";
	public static final String OAUTH_SINA = "sina";
	public static final String OAUTH_DOUBAN = "douban";
	public static final String OAUTH_QQ = "qq";
	public static final String OAUTH_RENREN = "renren";
	public static final String OAUTH_163 = "163";
	
    public static final String LOGIN_TO_URL = "toUrl";//将登录前的URL放到Session中的键名称
    public static final String CURRENT_URL = "currentUrl";//将当前的URL放到Session中的键名称
    public static final String USER_CONTEXT = "USER_CONTEXT";//用户对象放到Session中的键名称
    public static final String ADMIN_CONTEXT = "ADMIN_CONTEXT";//用户对象放到Session中的键名称

    public static final String Default_USER_PORTRAIT = "images/icon_man.png"; //默认会员肖像
    public static final String Default_COMPANY_LOGO = "images/noPic_03.jpg"; //默认公司logo
    public static final String Default_SCHOOL_LOGO = "images/noPic_04.jpg"; //默认学校logo
    
	//public static String week_day_of_sending_recommendation_mail=null;
	//public static String time_of_sending_recommendation_mail=null;
	
	public static String smtp_server=null;
	public static String recommendation_mail_from_account=null;
	public static String recommendation_mail_from_account_password=null;
	public static String recommendation_mail_from_name=null;
	
	//public static String recommendation_mail_subject=null;
	public static String audit_mail_subject=null;
	public static String activate_mail_subject=null;
	public static String reset_password_mail_subject=null;
	
	public static String upload_file_system_path=null;
	public static String upload_http_path=null;
	public static String portait_dir_name="portrait";
	public static String ppt_dir_name="ppt";
	public static String snapshot_dir_name="snapshot";
	public static String poster_dir_name="poster";
	public static String adv_dir_name="adv";
	
    static
    {
        InputStream is = CommonConstant.class.getResourceAsStream("/base.properties");
        try
        {
            Properties props = new Properties();
            props.load(is);
            //week_day_of_sending_recommendation_mail = props.getProperty("week_day_of_sending_recommendation_mail");
            //time_of_sending_recommendation_mail = props.getProperty("time_of_sending_recommendation_mail");
            smtp_server = props.getProperty("smtp_server");
            recommendation_mail_from_account = props.getProperty("recommendation_mail_from_account");
            recommendation_mail_from_account_password = props.getProperty("recommendation_mail_from_account_password");
            recommendation_mail_from_name = props.getProperty("recommendation_mail_from_name");
            //recommendation_mail_subject = props.getProperty("recommendation_mail_subject");
            audit_mail_subject=props.getProperty("audit_mail_subject");
            activate_mail_subject=props.getProperty("activate_mail_subject");
            reset_password_mail_subject=props.getProperty("reset_password_mail_subject");
            upload_file_system_path = props.getProperty("upload_file_system_path");
            upload_http_path = props.getProperty("upload_http_path");
        }
        catch(IOException e)
        {
            LogFactory.getLog(CommonConstant.class).error("读取配置文件出错！", e);
        }
        finally
        {
        	try
        	{
        		is.close();
        	}
        	catch(Exception ex)
        	{
        		
        	}
        }
    }
}
