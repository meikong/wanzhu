package com.wanzhu.utils;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class XX {
	public static void main(String arg[]){
		XX.http("http://www.baidu.com", null);
	}
	public static String http(String url, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;
		//构建请求参数
		StringBuffer sb = new StringBuffer();
		if(params!=null){
		for (Entry<String, String> e : params.entrySet()) {
		sb.append(e.getKey());
		sb.append("=");
		sb.append(e.getValue());
		sb.append("&");
		}
		sb.substring(0, sb.length() - 1);
		}
		System.out.println("send_url:"+url);
		System.out.println("send_data:"+sb.toString());
		//尝试发送请求
		try {
		u = new URL(url);
		con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
		osw.write(sb.toString());
		osw.flush();
		osw.close();
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		if (con != null) {
		con.disconnect();
		}
		}
		 
		
		 
		return null;
		}
		
}
