 /**  
 *@Description:     
 */ 
package com.wanzhu.utils;  
  
public class SqlFilter {
	public static String doFilter(String source){
		if(source == null || source.length() == 0)//为空不需要过滤
			return source;
		source = source.trim();
        //单引号替换成两个单引号
        source = source.replaceAll("'", "‘");
        source = source.replaceAll("--", "——");
        //半角封号替换为全角封号，防止多语句执行
        source = source.replaceAll(";", "；");

        //半角括号替换为全角括号
        source = source.replaceAll("\\(", "（");
        source = source.replaceAll("\\)", "）");

        //防止16进制注入
        source = source.replaceAll("0x", "0 x");
		return source;
	}
	
	public static void main(String[] args) {
		String source = "*/'  0x -- or 1=1 @qq.com ()";
		System.out.println(SqlFilter.doFilter(source));
		
		//System.out.println("%".equals("%"));
	}
}
