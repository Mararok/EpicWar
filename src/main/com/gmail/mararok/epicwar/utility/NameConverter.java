/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

public class NameConverter {

	public static String convertName(final String name) {
		return name.toLowerCase().replace(' ','_');
	}
	
	public static String nameToRegionName(String regionPrefix, String name) {
		return regionPrefix+convertName(name);
	}
	
	public static String regionNameToConvertedName(String regionPrefix, String regionName) {
		return regionName.substring(regionPrefix.length());
	}
	
	public static boolean isValidRegionName(String regionPrefix, String regionName) {
		return regionName.startsWith(regionPrefix);
	}
}
