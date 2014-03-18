package com.wanzhu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final long daytime_milliseconds = 86400000L;
	
	private static final Calendar calendar = Calendar.getInstance();
	//TODO sdf线程不安全,频繁调用每次需重新new
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 取得一个月前的时间值，用于列表中按一个月内及一个月前的查询条件准备。
	 * @return
	 */
	public static Date getDateBeforeAnMonth() {
		long now = Calendar.getInstance().getTimeInMillis();
		Calendar before = Calendar.getInstance();
		before.setTimeInMillis(now-(31*daytime_milliseconds));
		return before.getTime();
	}
	
	public static String getShowDatetimeFormat(String datetimeStr) {
		try {
			Date date = string2Date(datetimeStr, "yyyy-MM-dd HH:mm");
			Date now  = new Date();
			String result = "";
			if(date.getYear() != now.getYear()) {
				return date2String(date, "yyyy年MM月dd日 HH时mm分");
			} else {
				if(date.getMonth() != now.getMonth()) {
					return date2String(date, "MM月dd日 HH时mm分");
				} else {
					if(date.getDate() != now.getDate()) {
						return date2String(date, "dd日 HH时mm分");
					} else {
						if(date.getHours() != now.getHours()) {
							return date2String(date, "HH时mm分");
						} else {
							if(date.getMinutes() != now.getMinutes()) {
								return date2String(date, "HH时mm分");
							} else {
								if(date.getSeconds() != now.getSeconds()) {
									return now.getSeconds() - date.getSeconds() + "秒前";
								}
							}
						}
					}
				}
				
			}
			
			return result;
		} catch(Exception e) {
			return datetimeStr;
		}
	}
	
	/**
	 * 字符串转换成日期
	 * @param date 字符串
	 * @param pattern 
	 * @return
	 * @throws ParseException
	 */
	public static Date string2Date(String date, String pattern) throws ParseException {
		String oldPattern = sdf.toPattern();
		sdf.applyPattern(pattern);
		Date d = sdf.parse(date);
		sdf.applyPattern(oldPattern);
		return d;
	}
	
	/**
	 * 字符串转换成日期
	 * @param date 以yyyy-MM-dd HH:mm:ss的pattern进行转换
	 * @return
	 * @throws ParseException
	 */
	public static Date string2Date(String date) throws ParseException {
		return sdf.parse(date);
	}
	
	/**
	 * 将日期转换为字符串
	 * @param date
	 * @param pattern 转化的格式，如yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2String(Date date, String pattern) {
		String oldPattern = sdf.toPattern();
		sdf.applyPattern(pattern);
		String s = sdf.format(date);
		sdf.applyPattern(oldPattern);
		return s;
	}
	
	/**
	 * 将日期转换为字符串
	 * @param date 默认转化为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2String(Date date) {
		return sdf.format(date);
	}
	
    /**
     * 将传入的日期向前（或向后）滚动|amount|月
     * @param date
     * @param amount
     * @return
     */
    public static Date rollByMonth(Date date, int amount) {
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return new Date(calendar.getTimeInMillis());
    }
    
    /**
     * 将传入的日期向前（或向后）滚动|amount|天
     * 
     * @param date
     * @param amount
     * @return
     */
    public static long rollByDay(Date date, int amount) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        return calendar.getTimeInMillis();
    }
    
    /**
     * 获取本周几的日期对象
     * @param args
     */
	public static Date dayOfWeek2Date(int dayOfWeek) {
		int today_of_week=calendar.get(Calendar.DAY_OF_WEEK)-1;
		return new Date( rollByDay(new Date(), dayOfWeek-today_of_week) );
	}
	
	/**
	 * 获取最近的周几的日期对象
	 * @param args
	 */
	public static Date dayOfWeek2NearestDate(int dayOfWeek) {
		int today_of_week=calendar.get(Calendar.DAY_OF_WEEK)-1;
		if( dayOfWeek==today_of_week ) {
			return new Date();
		} else if( dayOfWeek>today_of_week ) {
			return dayOfWeek2Date(dayOfWeek);
		} else {
			return new Date( rollByDay(dayOfWeek2Date(7), dayOfWeek) );
		}
	}
    
    public static void main(String[] args) throws ParseException {
    	//int dayOfWeek=Integer.parseInt( CommonConstant.week_day_of_sending_recommendation_mail );
		//Date date = DateUtil.string2Date( DateUtil.date2String( DateUtil.dayOfWeek2NearestDate( dayOfWeek ), "yyyy-MM-dd" ) + " " + CommonConstant.time_of_sending_recommendation_mail + ":ss" );
		//System.out.println( DateUtil.date2String(date) );
    	
    	System.out.println(getShowDatetimeFormat("2012-11-29 16:48:12"));
    	
    }
}
