package com.wanzhu.utils;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {
	/**
	 * 截取文件名的扩展名
	 */
	public static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") > 0 && fileName.lastIndexOf(".") < fileName.length() - 1) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return null;
		}
	}

	/**
	 * 功能简述 :把字符串放入一个数组 系统会自动执行删除字符串首尾的多余的间隔符。如 ,abc,123,, 将自动变成 abc,123
	 * 
	 * @param PaStr_Source
	 *            要被放入的字符串
	 * @param paCha_seq
	 *            间隔符
	 * @param PaBoo_SeqTrim
	 *            自动删除首尾的间隔符
	 * @return 转换后的数组
	 */
	public static String[] split(String paStr_Source, char paCha_seq, boolean paBoo_SeqTrim) {
		if (paStr_Source == null)
			return null;
		if (paStr_Source.equals(""))
			return null;
		// 开头的间隔符去掉
		while (paStr_Source.charAt(0) == paCha_seq) {
			paStr_Source = paStr_Source.substring(1);
		}
		// 尾部的间隔符去掉
		while (paStr_Source.charAt(paStr_Source.length() - 1) == paCha_seq) {
			paStr_Source = paStr_Source.substring(0, paStr_Source.length() - 1);
		}

		return split(paStr_Source, "" + paCha_seq);
	}

	/**
	 * 功能简述 :把字符串放入一个数组 系统会自动执行删除字符串首尾的多余的间隔符。如 ,abc,123,, 将自动变成 abc,123
	 * 
	 * @param PaStr_Source
	 *            要被放入的字符串
	 * @param paStr_seq
	 *            间隔符
	 * @param PaBoo_SeqTrim
	 *            自动删除首尾的间隔符
	 * @return 转换后的数组
	 */
	public static String[] split(String paStr_Source, String paStr_seq, boolean paBoo_SeqTrim) {
		if (paStr_Source == null || paStr_seq == null)
			return null;
		// 开头的间隔符去掉
		while (paStr_Source.indexOf(paStr_seq) == 0) {
			paStr_Source = paStr_Source.substring(paStr_seq.length());
		}
		// 尾部的间隔符去掉
		while (paStr_Source.indexOf(paStr_seq, paStr_Source.length() - paStr_seq.length()) > -1) {
			paStr_Source = paStr_Source.substring(0, paStr_Source.length() - paStr_seq.length());
		}

		if (paStr_Source.equals("") || paStr_seq.equals(""))
			return null;
		return split(paStr_Source, paStr_seq);
	}

	/**
	 * 功能简述 :把字符串放入一个数组
	 * 
	 * @param PaStr_Source
	 *            要被放入的字符串
	 * @param paCha_seq
	 *            间隔符
	 * @return 转换后的数组
	 */
	public static String[] split(String paStr_Source, char paCha_seq) {
		return split(paStr_Source, "" + paCha_seq);
	}

	/**
	 * 功能简述 :把字符串放入一个数组
	 * 
	 * @param PaStr_Source
	 *            要被放入的字符串
	 * @param paStr_seq
	 *            间隔符
	 * @return 转换后的数组,失败返回 null
	 */
	public static String[] split(String paStr_Source, String paStr_seq) {
		if (paStr_Source == null || paStr_seq == null) {
			return new String[] {};
		}
		if (paStr_Source.equals("") || paStr_seq.equals("")) {
			return new String[] {};
		}
		int int_ArraySize = subStringCount(paStr_Source, paStr_seq);
		// 如果为-1则返回
		if (int_ArraySize == -1)
			return new String[] {};

		paStr_Source += paStr_seq;

		String[] str_RetArr = new String[int_ArraySize + 1];
		int int_pos = paStr_Source.indexOf(paStr_seq);
		int int_ArrayPos = 0;
		while (int_pos != -1) {
			str_RetArr[int_ArrayPos++] = paStr_Source.substring(0, int_pos);
			paStr_Source = paStr_Source.substring(int_pos + paStr_seq.length());
			int_pos = paStr_Source.indexOf(paStr_seq);
		}

		return str_RetArr;
	}

	/**
	 * 功能简述 :在一个字符串中查找字符个数
	 * 
	 * @param paStr_Source
	 *            要被查询的字符串
	 * @param paCha_seq
	 *            要查找的字符
	 * @return 找到的个数
	 * */
	public static int subStringCount(String paStr_Source, char paCha_seq) {
		// 如果是空指针则返回-1
		if (paStr_Source == null)
			return -1;
		if (paStr_Source.equals("") || paCha_seq == 32 || paCha_seq == 0)
			return -1;

		int int_ret = 0;
		int int_pos = paStr_Source.indexOf(paCha_seq);
		while (int_pos != -1) {
			int_ret++;
			int_pos = paStr_Source.indexOf(paCha_seq, int_pos + 1);
		}

		return int_ret;
	}

	/**
	 * 功能简述 :在一个字符串中查找字符串个数(不区分大小写)
	 * 
	 * @param paStr_Source
	 *            要被查询的字符串
	 * @param paStr_seq
	 *            要查找的字符串
	 * @return 找到的个数
	 * */
	public static int subStringCount(String paStr_Source, String paStr_seq) {
		// 如果是空指针则返回-1
		if (paStr_Source == null || paStr_seq == null)
			return -1;
		if (paStr_Source.equals("") || paStr_seq.equals(""))
			return -1;

		int int_ret = 0;
		int int_pos = paStr_Source.toUpperCase().indexOf(paStr_seq.toUpperCase());
		while (int_pos != -1) {
			int_ret++;
			int_pos = paStr_Source.toUpperCase().indexOf(paStr_seq.toUpperCase(), int_pos + paStr_seq.length());
		}

		return int_ret;
	}

	/**
	 * 功能简述 :在一个字符串中查找字符串个数(区分大小写)
	 * 
	 * @param paStr_Source
	 *            要被查询的字符串
	 * @param paStr_seq
	 *            要查找的字符串
	 * @param paBoo_case
	 *            区分大小写
	 * @return 找到的个数
	 * */
	public static int subStringCount(String paStr_Source, String paStr_seq, boolean paBoo_case) {
		// 如果是空指针则返回-1
		if (paStr_Source == null || paStr_seq == null)
			return -1;
		if (paStr_Source.equals("") || paStr_seq.equals(""))
			return -1;

		int int_ret = 0;
		int int_pos = paStr_Source.indexOf(paStr_seq);
		while (int_pos != -1) {
			int_ret++;
			int_pos = paStr_Source.indexOf(paStr_seq, int_pos + paStr_seq.length());
		}
		return int_ret;
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static String stringArray2String(String[] a, String sepa) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			if (i > 0)
				sb.append(sepa);
			sb.append(a[i]);
		}
		return sb.toString();
	}

	// 功能函数。将变量值不为空的参数组成字符串
	public static String appendParam(String returnStr, String paramId, String paramValue) {
		if (!returnStr.equals("")) {
			if (paramValue != null && !paramValue.equals("")) {
				returnStr = returnStr + "&" + paramId + "=" + paramValue;
			}
		} else {
			if (paramValue != null && !paramValue.equals("")) {
				returnStr = paramId + "=" + paramValue;
			}
		}
		return returnStr;
	}

	/*
	 * 以元为单位的数据转化为以分为单位的数据
	 */
	public static String formatYuanToFen(String input) {
		String out = "";
		NumberFormat ft = NumberFormat.getInstance();
		Number nbInput;
		try {
			nbInput = ft.parse(input);

			double fInput = nbInput.doubleValue() * 100.0;

			ft.setGroupingUsed(false);
			ft.setMaximumFractionDigits(0);

			out = ft.format(fInput);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return out;
	}

	/*
	 * 以分为单位的数据转化为以元为单位的数据
	 */
	public static String formatFenToYuan(String input) {
		String out = "";
		NumberFormat ft = NumberFormat.getInstance();
		Number nbInput;
		try {
			nbInput = ft.parse(input);

			double fInput = nbInput.doubleValue() / 100.0;

			ft.setGroupingUsed(false);
			// ft.setMaximumFractionDigits(0);
			out = ft.format(fInput);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return out;
	}

	// 随机字符串
	public static String getRandomString(int size) {
		char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a',
				's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random(); // 初始化随机数产生器
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(c[Math.abs(random.nextInt()) % c.length]);
		}
		return sb.toString();
	}

	// 随机字符串(数字)
	public static String getRandomNumber(int size) {
		char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		Random random = new Random(); // 初始化随机数产生器
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(c[Math.abs(random.nextInt()) % c.length]);
		}
		return sb.toString();
	}

	/*
	 * 验证指定的字串是否IP地址 ip形如192.168.0.1则返回true 否则返回false
	 */
	public static boolean isAIP(String ip) {
		Pattern patt = Pattern.compile("^([01]?[0-9][0-9]|[01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])"
				+ "\\.([01]?[0-9][0-9]|[01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])"
				+ "\\.([01]?[0-9][0-9]|[01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])"
				+ "\\.([01]?[0-9][0-9]|[01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])$");
		Matcher mat = patt.matcher(ip);
		return mat.matches();
	}

	/*
	 * 返回IP的指定部分字串 如调用getOnePartOfIPDomain("192.168.0.1", 2) 返回168
	 */
	public static String getOnePartOfIP(String ip, int index) {
		String result = null;
		if (!isAIP(ip))
			return null;
		String[] ipArr = ip.split("\\.");
		if (index <= ipArr.length)
			result = ipArr[index - 1];
		return result;
	}

	/*
	 * 分析快照字符串 如调用getValueFromSnapshot("aa=11;bb=22;cc=33", bb) 返回22
	 */
	public static String getValueFromSnapshot(String snapshot, String key) {
		String[] snapshots = null;
		String pairs[] = null;
		String result = "";

		if (snapshot == null || snapshot.trim().length() == 0) {
			return "";
		}
		if (key == null || key.trim().length() == 0) {
			return "";
		}
		snapshots = StringUtils.split(snapshot, ';');
		for (int i = 0; i < snapshots.length; i++) {
			pairs = StringUtils.split(snapshots[i], '=');
			if (pairs.length == 2) {
				if (pairs[0].trim().equals(key.trim())) {
					result = pairs[1];
					break;
				}
			}
		}

		snapshots = null;
		pairs = null;
		return result;
	}

	/*
	 * 判断给定的字符串是否为空
	 */
	public static boolean isEmpty(String value) {
		if (value == null || value.trim().equals("")) {
			return true;
		}
		return false;
	}

	/*
	 * 将字符串去掉前后空格并将 字串中的英文均转为小写字母
	 */
	public static String treatStringTrimAndLowerCase(String str) {
		if (str == null)
			return null;
		str = str.trim().toLowerCase();
		return str;
	}

	/*
	 * 将字符串去掉前后空格并将 字串中的英文均转为大写字母
	 */
	public static String treatStringTrimAndUpperCase(String str) {
		if (str == null)
			return null;
		str = str.trim().toUpperCase();
		return str;
	}

	/*
	 * 判断字符串中的字符是否均为数字 如"123990"返回true,"123a"返回false
	 */
	public static boolean isAllNumbric(String str) {
		if (str == null)
			return false;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9')
				return false;
		}
		return true;
	}

	/*
	 * 返回域名或IP地址的最后部分 如调用getLastPartOfDomain("192.168.0.1")
	 * 返回1，调用getLastPartOfDomain("www.163.com.cn")返回cn
	 */
	public static String getLastPartOfDomain(String domain) {
		if (domain == null || domain.indexOf(".") == -1)
			return "";
		String[] domainArr = domain.split("\\.");
		return domainArr[domainArr.length - 1];
	}

	public static Map<String, String> getMap4KvString(String kvString, char splitor) {
		String[] snapshots = StringUtils.split(kvString, splitor);
		String[] pairs = null;
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < snapshots.length; i++) {
			pairs = StringUtils.split(snapshots[i], '=');
			if (pairs.length == 2) {
				result.put(pairs[0], pairs[1]);
			}
		}
		return result;
	}

	public static String getKvString4Map(Map<String, String> map, char splitor) {
		Iterator<String> iter = map.keySet().iterator();
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			String key = iter.next();
			sb.append(key);
			sb.append("=");
			sb.append(map.get(key));
			sb.append(splitor);
		}
		return sb.toString();
	}

	public static String getSafeString(String str) {
		String result = str;
		result = result.replace("{", "");
		result = result.replace("}", "");
		result = result.replace("[", "");
		result = result.replace("]", "");
		result = result.replace(",", "");
		result = result.replace("\"", "");
		result = result.replace("'", "");
		result = result.replace(":", "");
		result = result.replaceAll("\r\n", "\\\\r\\\\n");
		return result;
	}

	/**
	 * 格式化字符串，如： 我是{0},今年{1}岁---->我是张三,今年34岁
	 * 
	 * @param key
	 *            目标字符串
	 * @param values
	 *            需要在key中动态指定的值集
	 * @return
	 */
	public static String getText(String key, Object[] values) {
		try {
			if (isEmpty(key))
				return key;
			return new MessageFormat(key).format(values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	public static String[][] toArray(String string, String[] separator) {
		if (StringUtils.isEmpty(string) || null == separator || separator.length != 2 || StringUtils.isEmpty(separator[0])
				|| StringUtils.isEmpty(separator[1]) || string.indexOf(separator[1]) == -1)
			return new String[0][0];
		List<String> list = new ArrayList<String>();
		for (String str : string.split(separator[0]))
			if (str.indexOf(separator[1]) > -1 && str.indexOf(separator[1]) == str.lastIndexOf(separator[1]))
				list.add(str);
		if (list.isEmpty())
			return new String[0][0];
		String[][] array = new String[list.size()][2];
		for (int i = 0; i < array.length; i++)
			array[i] = list.get(i).split(separator[1]);
		return array;
	}

	public static void main(String[] args) {
		System.out.println(getText("赞同了话题<a href=\"/event/{0}.html#activityRemark\">{1}</a>", new Object[] { "张三", 34 }));
	}
}