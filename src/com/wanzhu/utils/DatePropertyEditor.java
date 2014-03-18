package com.wanzhu.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

public class DatePropertyEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		this.setValue(parser(text));
	}

	private Object parser(String text) {
		if(StringUtils.isEmpty(text)) return null;
		Date result = null;
		try {
			if(text.length() == 10) {
				result = DateUtil.string2Date(text, "yyyy-MM-dd");
			} else {
				result =  DateUtil.string2Date(text);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
