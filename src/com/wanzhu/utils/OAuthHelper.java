package com.wanzhu.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OAuthHelper {
	private static Log log = LogFactory.getLog(OAuthHelper.class);
	
	public static String RESPONSE_TYPE;
	public static String GRANT_TYPE;
	public static String REDIRECT_URL;
	public static String REDIRECT_BIND_URL;
	
	public static String DOUBAN_AUTH_URL;
	public static String DOUBAN_TOKEN_URL;
	public static String DOUBAN_CLIENT_ID;
	public static String DOUBAN_CLIENT_SECRET;
	public static String DOUBAN_USER_INFO_URL;
	
	public static String SINA_AUTH_URL;
	public static String SINA_TOKEN_URL;
	public static String SINA_CLIENT_ID;
	public static String SINA_CLIENT_SECRET;
	public static String SINA_USER_INFO_URL;
	
	public static String QQ_AUTH_URL;
	public static String QQ_TOKEN_URL;
	public static String QQ_CLIENT_ID;
	public static String QQ_CLIENT_SECRET;
	public static String QQ_USER_INFO_URL;
	public static String QQ_USER_INFO_URL_Z;
	
	public static String RENREN_AUTH_URL;
	public static String RENREN_TOKEN_URL;
	public static String RENREN_CLIENT_ID;
	public static String RENREN_CLIENT_SECRET;
	public static String RENREN_USER_INFO_URL;
	
	public static String WANGYI_AUTH_URL;
	public static String WANGYI_TOKEN_URL;
	public static String WANGYI_CLIENT_ID;
	public static String WANGYI_CLIENT_SECRET;
	public static String WANGYI_USER_INFO_URL;
	
	static {
        InputStream is = ErrorHelper.class.getResourceAsStream("/oauth.properties");
        try {
            Properties props = new Properties();
            props.load(is);
            RESPONSE_TYPE = props.getProperty("response_type");
        	GRANT_TYPE = props.getProperty("grant_type");
        	REDIRECT_URL = props.getProperty("redirect_uri");
        	REDIRECT_BIND_URL = props.getProperty("redirect_bind_uri");
        	
        	DOUBAN_AUTH_URL = props.getProperty("douban_auth_url");
        	DOUBAN_TOKEN_URL = props.getProperty("douban_token_url");
        	DOUBAN_CLIENT_ID = props.getProperty("douban_client_id");
        	DOUBAN_CLIENT_SECRET = props.getProperty("douban_client_secret");
        	DOUBAN_USER_INFO_URL = props.getProperty("douban_user_info_url");
        	
        	SINA_AUTH_URL = props.getProperty("sina_auth_url");
        	SINA_TOKEN_URL = props.getProperty("sina_token_url");
        	SINA_CLIENT_ID = props.getProperty("sina_client_id");
        	SINA_CLIENT_SECRET = props.getProperty("sina_client_secret");
        	SINA_USER_INFO_URL = props.getProperty("sina_user_info_url");
        	
        	QQ_AUTH_URL = props.getProperty("qq_auth_url");
        	QQ_TOKEN_URL = props.getProperty("qq_token_url");
        	QQ_CLIENT_ID = props.getProperty("qq_client_id");
        	QQ_CLIENT_SECRET = props.getProperty("qq_client_secret");
        	QQ_USER_INFO_URL = props.getProperty("qq_user_info_url");
        	QQ_USER_INFO_URL_Z=props.getProperty("qq_user_info_url_Z");
        	
        	RENREN_AUTH_URL = props.getProperty("renren_auth_url");
        	RENREN_TOKEN_URL = props.getProperty("renren_token_url");
        	RENREN_CLIENT_ID = props.getProperty("renren_client_id");
        	RENREN_CLIENT_SECRET = props.getProperty("renren_client_secret");
        	RENREN_USER_INFO_URL = props.getProperty("renren_user_info_url");
        	
        	WANGYI_AUTH_URL = props.getProperty("163_auth_url");
        	WANGYI_TOKEN_URL = props.getProperty("163_token_url");
        	WANGYI_CLIENT_ID = props.getProperty("163_client_id");
        	WANGYI_CLIENT_SECRET = props.getProperty("163_client_secret");
        	WANGYI_USER_INFO_URL = props.getProperty("163_user_info_url");
        } catch(Exception e) {
        	
        }
	}

	/**
	 * 获取未授权的code
	 * @param url 调用url
	 * @return
	 */
	public static String auth(String url) {
		if (url.endsWith("/"))
			url = url.substring(0, url.length() - 1);

		URLConnection urlConnection = null;
		BufferedReader br = null;
		try {
			// 组装调用URL
			// 执行调用
			urlConnection = new URL(url).openConnection();
			log.info("Invoke HTTP interface: " + url);
			urlConnection.setConnectTimeout(10 * 1000);

			br = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("Failed to invoke HTTP interface: " + url, e);
			return null;
		} finally {
			urlConnection = null;
			try {
				br.close();
			} catch (Exception e1) {
			}
			br = null;
		}
	}
	
	/**
	 * 获取access token
	 * @param url 
	 * @param paramMap
	 * @return
	 */
	public static String getAccessToken(String url,
			Map<String, String[]> paramMap) {
		if (url.endsWith("/"))
			url = url.substring(0, url.length() - 1);

		TrustManager[] tm = null;
		SSLContext sslContext = null;
		SSLSocketFactory ssf = null;
		HttpsURLConnection httpsConn = null;
		BufferedReader br = null;
		BufferedOutputStream bos = null;
		try {
			tm = new TrustManager[]{ new MyX509TrustManager() };
			sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	        sslContext.init(null, tm, new java.security.SecureRandom());
	        ssf = sslContext.getSocketFactory();
			// 执行调用
			httpsConn = (HttpsURLConnection) new URL(url).openConnection();
			httpsConn.setSSLSocketFactory(ssf);
			httpsConn.setDoInput(true);
			httpsConn.setDoOutput(true);
			httpsConn.setUseCaches(false);
			bos = new BufferedOutputStream(httpsConn.getOutputStream());
			// 组装调用参数
			int count = 0;
			Iterator<String> paramNameIter = paramMap.keySet().iterator();
			String paramName = null;
			String[] paramValues = null;
			while (paramNameIter.hasNext()) {
				paramName = paramNameIter.next();
				paramValues = paramMap.get(paramName);
				if (count > 0)
					bos.write("&".getBytes());
				bos.write((paramName + "=").getBytes());
				if (paramValues != null && paramValues.length > 0) {
					for (int i = 0; i < paramValues.length; i++) {
						if (i > 0)
							bos.write(",".getBytes());
						bos.write(paramValues[i].getBytes());
					}
				}
				count++;
			}
			log.info("Invoke HTTP interface: " + url);
			httpsConn.setConnectTimeout(10 * 1000);
			bos.flush();

			InputStreamReader read = new InputStreamReader(httpsConn.getInputStream(), "utf-8");
			br = new BufferedReader(read);
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("Failed to invoke HTTP interface: " + url, e);
			return null;
		} finally {
			tm = null;
			sslContext = null;
			ssf = null;
			httpsConn = null;
			try {
				br.close();
				bos.close();
			} catch (Exception e1) {

			}
			br = null;
			bos = null;
		}
	}
	
	/**
	 * 通过获取的access token，取得开放平台中用户的信息
	 * @param url 调用url
	 * @param accessToken 
	 * @return
	 */
	public static String getUserInfo(String url, String accessToken,Map<String, String[]> paramMap) {
		if (url.endsWith("/"))
			url = url.substring(0, url.length() - 1);

		TrustManager[] tm = null;
		SSLContext sslContext = null;
		SSLSocketFactory ssf = null;
		HttpsURLConnection httpsConn = null;
		BufferedReader br = null;
		String uri="";
		try {
			tm = new TrustManager[]{ new MyX509TrustManager() };
			sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	        sslContext.init(null, tm, new java.security.SecureRandom());
	        ssf = sslContext.getSocketFactory();
			// 组装调用URL
			// 执行调用
			if(paramMap!=null)
			{
				// 组装调用参数
				int count = 0;
				Iterator<String> paramNameIter = paramMap.keySet().iterator();
				String paramName = null;
				String[] paramValues = null;
				while (paramNameIter.hasNext()) {
					paramName = paramNameIter.next();
					paramValues = paramMap.get(paramName);
					if (count > 0)
						uri+="&";
						uri+=paramName + "=";
					if (paramValues != null && paramValues.length > 0) {
						for (int i = 0; i < paramValues.length; i++) {
							if (i > 0)uri+=",";
							uri+=paramValues[i];
						}
					}
					count++;
				}
				httpsConn = (HttpsURLConnection) new URL(url+"?"+uri).openConnection();
				httpsConn.setSSLSocketFactory(ssf);
			}else
			{
				httpsConn = (HttpsURLConnection) new URL(url).openConnection();
				httpsConn.setSSLSocketFactory(ssf);
				log.info("Invoke HTTP interface: " + url);
				httpsConn.setConnectTimeout(10 * 1000);
				httpsConn.addRequestProperty("Authorization", "Bearer " + accessToken);
			}
			
			InputStreamReader read = new InputStreamReader(httpsConn.getInputStream(), "utf-8");
			br = new BufferedReader(read);
			
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("Failed to invoke HTTP interface: " + url, e);
			return null;
		} finally {
			tm = null;
			sslContext = null;
			ssf = null;
			httpsConn = null;
			try {
				br.close();
			} catch (Exception e1) {
			}
			br = null;
		}
	}

}
