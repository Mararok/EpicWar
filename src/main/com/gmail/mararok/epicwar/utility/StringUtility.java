package com.gmail.mararok.epicwar.utility;

import org.apache.commons.lang.math.NumberUtils;

public class StringUtility {
	
	public static boolean isNumber(String value) {
		return NumberUtils.isNumber(value);
	}
	
	public static boolean isInteger(String value) {
		return isNumber(value);
	}
	
	public static boolean isFloat(String value) {
		return isNumber(value);
	}
	
	public static boolean isChar(String value) {
		return value != null && value.length() == 1;
	}
	public static boolean isBoolean(String value) {
		return value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
	}
}
