package com.wanzhu.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wanzhu.base.CommonConstant;
import com.wanzhu.entity.User;
import com.wanzhu.utils.JsonUtil;
import com.wanzhu.utils.OAuthHelper;

@Service
public class OAuthService {
	protected Log log = LogFactory.getLog(this.getClass());
//	@Autowired
//	private RegisterService registerService;
	
	/**
	 * 根据用户来源和回调地址，组织授权url地址及参数
	 * @param origin
	 * @return
	 */
	public String getAuthUrlByOrigin(String origin,String redirectUrl) {
		StringBuffer url = new StringBuffer();
		if(CommonConstant.OAUTH_DOUBAN.equals(origin)) {
			url = formateAuthUrl(OAuthHelper.DOUBAN_AUTH_URL, OAuthHelper.DOUBAN_CLIENT_ID, origin,redirectUrl);
		} else if(CommonConstant.OAUTH_SINA.equals(origin)) {
			url = formateAuthUrl(OAuthHelper.SINA_AUTH_URL, OAuthHelper.SINA_CLIENT_ID, origin,redirectUrl);
		} else if(CommonConstant.OAUTH_QQ.equals(origin)) {
			url = formateAuthUrl(OAuthHelper.QQ_AUTH_URL, OAuthHelper.QQ_CLIENT_ID, origin,redirectUrl);
		} else if(CommonConstant.OAUTH_RENREN.equals(origin)) {
			url = formateAuthUrl(OAuthHelper.RENREN_AUTH_URL, OAuthHelper.RENREN_CLIENT_ID, origin,redirectUrl);
		} else if(CommonConstant.OAUTH_163.equals(origin)) {
			url = formateAuthUrl(OAuthHelper.WANGYI_AUTH_URL, OAuthHelper.WANGYI_CLIENT_ID, origin,redirectUrl);
		}
		return url.toString();
	}

	
	/**
	 * 根据code获取token, 并由此得到开放平台的用户信息并持久化
	 * @param code
	 * @param origin
	 * @return TCustomer
	 * @throws Exception
	 */
	public User token(String code, String origin) throws Exception {
		String userId = null;
		String userName = null;
		String profileUrl = null;
		String profileImageUrl = null;
		String clientId=null;
		if(CommonConstant.OAUTH_DOUBAN.equals(origin)) {
			Map tokenInfo = fetchTokenInfo(OAuthHelper.DOUBAN_TOKEN_URL, 
					formateTokenParamMap(code, OAuthHelper.DOUBAN_CLIENT_ID, OAuthHelper.DOUBAN_CLIENT_SECRET));
			String token = (String) tokenInfo.get("access_token");
			userId = (String) tokenInfo.get("douban_user_id");
			userName = parseDoubanUserName(fetchUserInfo(OAuthHelper.DOUBAN_USER_INFO_URL, token,null));
		} else if(CommonConstant.OAUTH_SINA.equals(origin)) {
			Map tokenInfo = fetchTokenInfo(OAuthHelper.SINA_TOKEN_URL, 
					formateTokenParamMap(code, OAuthHelper.SINA_CLIENT_ID, OAuthHelper.SINA_CLIENT_SECRET));
			String token = (String) tokenInfo.get("access_token");
			userId = (String) tokenInfo.get("uid");
			String userJsonInfo = fetchUserInfo(OAuthHelper.SINA_USER_INFO_URL, token,formateSinaUserParamMap(token,userId));
			userName = parseSinaUserName(userJsonInfo);
			profileUrl = parseSinaProfileUrl(userJsonInfo);
			profileImageUrl = (String) JsonUtil.getMap4Json(userJsonInfo).get("profile_image_url");
		} else if(CommonConstant.OAUTH_QQ.equals(origin)) {
			Map tokenInfo = fetchQqTokenInfo(OAuthHelper.QQ_TOKEN_URL, 
					formateTokenParamMap(code, OAuthHelper.QQ_CLIENT_ID, OAuthHelper.QQ_CLIENT_SECRET));
			String token = (String) tokenInfo.get("access_token");
			userId = parseQqUserName(fetchUserInfo(OAuthHelper.QQ_USER_INFO_URL, token,formateQqUserParamMap(token)));
			clientId= parseQqClientId(fetchUserInfo(OAuthHelper.QQ_USER_INFO_URL, token,formateQqUserParamMap(token)));
			userName = parseQqnickname(fetchUserInfo(OAuthHelper.QQ_USER_INFO_URL_Z, token,formateQqUserParamMap(token,clientId,userId)));;
		} else if(CommonConstant.OAUTH_RENREN.equals(origin)) {
			Map tokenInfo = fetchTokenInfo(OAuthHelper.RENREN_TOKEN_URL, 
					formateTokenParamMap(code, OAuthHelper.RENREN_CLIENT_ID, OAuthHelper.RENREN_CLIENT_SECRET));
			String token = (String) tokenInfo.get("access_token");
			Map user=JsonUtil.getMap4Json((String) tokenInfo.get("user"));
			userId = (String) user.get("id");
			userName = (String) user.get("name");
		} else if(CommonConstant.OAUTH_163.equals(origin)) {
			Map tokenInfo = fetchTokenInfo(OAuthHelper.WANGYI_TOKEN_URL, 
					formateTokenParamMap(code, OAuthHelper.WANGYI_CLIENT_ID, OAuthHelper.WANGYI_CLIENT_SECRET));
			String token = (String) tokenInfo.get("access_token");
			userId = (String) tokenInfo.get("uid");
			userName = parseWangyiUserName(fetchUserInfo(OAuthHelper.WANGYI_USER_INFO_URL, token,formateQqUserParamMap(token)));
		}
		
		log.info("userId: " + userId + " userName: "+userName);
		User u = new User();
		u.setOauthid(origin+"_"+userId);
		u.setName(userName);
		u.setWeibo(profileUrl);
		u.setPortrait(profileImageUrl);
		return u;
	}
	
	/**
	 * 获取token信息
	 * @param tokenUrl
	 * @param paramMap
	 * @return
	 */
	private Map fetchTokenInfo(String tokenUrl, Map<String, String[]> paramMap) {
		String tokenJsonInfo = OAuthHelper.getAccessToken(tokenUrl, paramMap);
		return JsonUtil.getMap4Json(tokenJsonInfo);
	}

	/**
	 * 获取token信息
	 * @param tokenUrl
	 * @param paramMap
	 * @return
	 */
	private Map fetchQqTokenInfo(String tokenUrl, Map<String, String[]> paramMap) {
		String tokenJsonInfo = OAuthHelper.getAccessToken(tokenUrl, paramMap);
		String t2=null;
		Map map=new HashMap();
		if(tokenJsonInfo!=null)
		{
			if(tokenJsonInfo.indexOf("error")==-1){
				String t=tokenJsonInfo;
				String t1=t.substring(13);
				t2=t1.substring(0,t1.indexOf("&"));
				map.put("access_token", t2);
				getRequest().getSession().setAttribute("qq_access_token", t2);
			}else
			{
				String token=(String) this.getRequest().getSession().getAttribute("qq_access_token");
				map.put("access_token", token);
			}
		}
		return map;
	}
	
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获取用户信息
	 * @param tokenUrl
	 * @param paramMap
	 * @return
	 */
	private String fetchUserInfo(String url, String token,Map<String, String[]> paramMap) {
		return OAuthHelper.getUserInfo(url, token,paramMap);
	}
	

	/**
	 * 解析douban用户昵称
	 * @param userXmlInfo
	 * @return
	 * @throws DocumentException
	 */
	private String parseDoubanUserName(String userXmlInfo) throws DocumentException {
		Document doc = DocumentHelper.parseText(userXmlInfo);
		Element root = doc.getRootElement();
		//用户id（不可靠）
		Element uid = root.element("uid");
		//用户昵称
		Element titleElement = root.element("title");
		return titleElement.getText();
	}
	
	/**
	 * 解析sina用户昵称
	 * @param userJsonInfo
	 * @return
	 */
	private String parseSinaUserName(String userJsonInfo) {
		return (String) JsonUtil.getMap4Json(userJsonInfo).get("name");
	}
	
	private String parseSinaProfileUrl(String userJsonInfo) {
		return "http://www.weibo.com/"+(String) JsonUtil.getMap4Json(userJsonInfo).get("profile_url");
	}
	
	/**
	 * 解析qq用户昵称
	 * @param userJsonInfo
	 * @return
	 */
	private String parseQqUserName(String userJsonInfo) {
		userJsonInfo=userJsonInfo.substring(9);
		userJsonInfo=userJsonInfo.substring(0,userJsonInfo.indexOf(")"));
		return (String) JsonUtil.getMap4Json(userJsonInfo).get("openid");
	}
	
	private String parseQqClientId(String userJsonInfo) {
		userJsonInfo=userJsonInfo.substring(9);
		userJsonInfo=userJsonInfo.substring(0,userJsonInfo.indexOf(")"));
		return (String) JsonUtil.getMap4Json(userJsonInfo).get("client_id");
	}
	
	private String parseQqnickname(String userJsonInfo) {
		return (String) JsonUtil.getMap4Json(userJsonInfo).get("nickname");
	}
	/**
	 * 解析renren用户昵称
	 * @param userJsonInfo
	 * @return
	 */
	private String parseRenrenUserName(String userJsonInfo) {
		return (String) JsonUtil.getMap4Json(userJsonInfo).get("username");
	}
	
	/**
	 * 解析163用户昵称
	 * @param userJsonInfo
	 * @return
	 */
	private String parseWangyiUserName(String userJsonInfo) {
		return (String) JsonUtil.getMap4Json(userJsonInfo).get("name");
	}
	
//	private TCustomer addOrUpdateUserInfo(String oauthUserId, String oauthUserNickName, String origion) {
//		String localCustomerId = iLogin.checkOAuthAccount(oauthUserId, origion);
//		TCustomer customer = null;
//		if(StringUtils.isEmpty(localCustomerId)) {
//			customer = new TCustomer();
//			customer.setCustomer(origion+"_"+oauthUserId);
//			customer.setReservedfield1(oauthUserId);
//			customer.setReservedfield2(customer.getCustomer());
//			customer.setReservedfield3(origion);
//			customer.setReservedfield4(oauthUserNickName);
//			customer.setSex(2);
//			int resonCode = registerService.register(customer);
//			log.info("持久化第三方用户：返code：" + resonCode);
//			String customerId = iLogin.checkOAuthAccount(oauthUserId, origion);
//			customer = iCustomer.getCustomer(ecpToken, customerId);
//		} else {
//			customer = iCustomer.getCustomer(ecpToken, localCustomerId);
//		}
//		return customer;
//	}
	
	/**
	 * 组织授权请求的url及传输参数
	 * @param authUrl
	 * @param clientId
	 * @param origin
	 * @return
	 */
	private StringBuffer formateAuthUrl(String authUrl, String clientId, String origin,String redirectUrl) {
		return new StringBuffer().append(authUrl)
				.append("?client_id=").append(clientId)
				.append("&redirect_uri=").append(redirectUrl)
				.append("&response_type=").append(OAuthHelper.RESPONSE_TYPE)
				.append("&state=").append(origin);
	}
	
	/**
	 * 组织请求access token的传输参数
	 * @param code
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	private Map<String, String[]> formateTokenParamMap(String code, String clientId, String clientSecret) {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		paramMap.put("client_id", new String[]{clientId});
		paramMap.put("client_secret", new String[]{clientSecret});
		paramMap.put("redirect_uri", new String[]{OAuthHelper.REDIRECT_URL});
		paramMap.put("grant_type", new String[]{OAuthHelper.GRANT_TYPE});
		paramMap.put("code", new String[]{code});
		return paramMap;
	}
	
	
	/**
	 * 组织请求Sina取 用户的传输参数
	 * @param code
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	private Map<String, String[]> formateSinaUserParamMap(String token, String uid) {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		paramMap.put("source", new String[]{OAuthHelper.SINA_CLIENT_ID});
		paramMap.put("access_token", new String[]{token});
		paramMap.put("uid", new String[]{uid});
		return paramMap;
	}
	
	/**
	 * 组织请求QQ取 用户的传输参数
	 * @param code
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	private Map<String, String[]> formateQqUserParamMap(String token) {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		paramMap.put("access_token", new String[]{token});
		return paramMap;
	}
	
	/**
	 * 组织请求QQ取 用户的传输参数
	 * @param code
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	private Map<String, String[]> formateQqUserParamMap(String token,String oauth_consumer_key,String openid) {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		paramMap.put("access_token", new String[]{token});
		paramMap.put("oauth_consumer_key", new String[]{oauth_consumer_key});
		paramMap.put("openid", new String[]{openid});
		return paramMap;
	}
	
	
	public static void main(String[] args) {
//		String userInfo = OAuthHelper.getUserInfo(OAuthHelper.SINA_USER_INFO_URL, "2.00i7MOiBRqE1OD870c96d3150lucWT");
//		System.out.println(userInfo);
//		String t="access_token=793B6554831EEC46BA9377645ACA695E&expires_in=7776000";
//		String t1=t.substring(13);
//		String t2=t1.substring(0,t1.indexOf("&"));
//		System.out.println(t2);
	}
	
}
