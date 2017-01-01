package com.lachesis.support.common.util.text;

import org.apache.commons.lang3.StringUtils;

public final class TextUtils {
	public static boolean isBlank(String text){
		return StringUtils.isBlank(text);
	}
	
	public static boolean isNotBlank(String text){
		return !isBlank(text);
	}
	
	private TextUtils(){}
}
