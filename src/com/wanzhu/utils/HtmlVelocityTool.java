package com.wanzhu.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.tools.generic.SafeConfig;
import org.apache.velocity.tools.generic.ValueParser;

public class HtmlVelocityTool extends SafeConfig {
	public static final String DEFAULT_KEY = "html";

	private String key = DEFAULT_KEY;

	protected void configure(ValueParser values) {
		String altkey = values.getString("key");
		if (altkey != null) {
			setKey(altkey);
		}
	}

	/**
	 * 把已转义过标签的html，翻译回html
	 * @param string
	 * @return
	 */
	public String parse(Object string) {
		if (string == null) {
			return null;
		}
		return StringEscapeUtils.unescapeHtml(String.valueOf(string));
	}

	protected void setKey(String key) {
		if (key == null) {
			throw new NullPointerException("EscapeTool key cannot be null");
		}
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
