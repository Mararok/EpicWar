/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public class ColorConverter {
	private static HashMap<ChatColor, DyeColor> Chat2DyeMap;
	private static HashMap<DyeColor,ChatColor> Dye2ChatMap;
	
	static {
		
		Chat2DyeMap = new HashMap<ChatColor, DyeColor>();
		
		Chat2DyeMap.put(ChatColor.BLACK,DyeColor.BLACK);
		Chat2DyeMap.put(ChatColor.BLUE,DyeColor.BLUE);
		Chat2DyeMap.put(ChatColor.GRAY,DyeColor.GRAY);
		Chat2DyeMap.put(ChatColor.GREEN,DyeColor.GREEN);
		Chat2DyeMap.put(ChatColor.RED,DyeColor.RED);
		Chat2DyeMap.put(ChatColor.WHITE,DyeColor.WHITE);
		Chat2DyeMap.put(ChatColor.YELLOW,DyeColor.YELLOW);
		
		Dye2ChatMap = new HashMap<DyeColor,ChatColor>();
		
		Dye2ChatMap.put(DyeColor.BLACK,ChatColor.BLACK);
		Dye2ChatMap.put(DyeColor.BLUE,ChatColor.BLUE);
		Dye2ChatMap.put(DyeColor.GRAY,ChatColor.GRAY);
		Dye2ChatMap.put(DyeColor.GREEN,ChatColor.GREEN);
		Dye2ChatMap.put(DyeColor.RED,ChatColor.RED);
		Dye2ChatMap.put(DyeColor.WHITE,ChatColor.WHITE);
		Dye2ChatMap.put(DyeColor.YELLOW,ChatColor.YELLOW);
	}
	
	public static DyeColor getDyeColor(ChatColor chatColor) {
		DyeColor dyeColor = Chat2DyeMap.get(chatColor);
		return (dyeColor != null)?dyeColor:DyeColor.PINK;
	}
	
	public static ChatColor getChatColor(DyeColor dyeColor) {
		ChatColor chatColor = Dye2ChatMap.get(dyeColor);
		return (chatColor != null)?chatColor:ChatColor.MAGIC;
	}
	
	public static boolean isSupported(ChatColor color) {
		return getDyeColor(color) != null;
	}
	
	public static List<ChatColor> getColorsList() {
		return (List<ChatColor>)Collections.list(Collections.enumeration(Chat2DyeMap.keySet()));
	}
}

