package com.gmail.mararok.bukkit.util;

import org.apache.commons.lang.math.NumberUtils;

public class StringUtil {

  public static boolean isNumber(String value) {
    return NumberUtils.isNumber(value);
  }

  public static boolean isChar(String value) {
    return value != null && value.length() == 1;
  }

  public static boolean isBoolean(String value) {
    return value != null
        && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
  }
}
