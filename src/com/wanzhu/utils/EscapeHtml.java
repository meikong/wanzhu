package com.wanzhu.utils;


public class EscapeHtml {

	public EscapeHtml() {

	}

	// 纯文本编辑器转义
	public static String Html2Text(String content) {
		if (content == null)
			return "";
		String html = content;
		html = org.springframework.util.StringUtils.replace(html, "'", "&apos;");
		html = org.springframework.util.StringUtils.replace(html, "\"", "&quot;");
		// html = org.springframework.util.StringUtils.replace(html, "\t",
		// "&nbsp;&nbsp;");// 替换跳格
		// xu add
		html = org.springframework.util.StringUtils.replace(html, "\t", "");
		// xu add
		html = org.springframework.util.StringUtils.replace(html, "<", "&lt;");
		html = org.springframework.util.StringUtils.replace(html, ">", "&gt;");
		return html;

	}
}