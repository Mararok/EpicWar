/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum FactionColor {
  
  BLACK(ChatColor.BLACK,DyeColor.BLACK),
  BLUE(ChatColor.BLUE,DyeColor.BLUE),
  GRAY(ChatColor.GRAY,DyeColor.GRAY),
  GREEN(ChatColor.GREEN,DyeColor.GREEN),
  RED(ChatColor.RED,DyeColor.RED),
  WHITE(ChatColor.WHITE,DyeColor.WHITE),
  YELLOW(ChatColor.YELLOW,DyeColor.YELLOW);
  
  private ChatColor chatColor;
  private DyeColor dyeColor;
  
  private FactionColor(ChatColor chatColor, DyeColor dyeColor) {
    this.chatColor = chatColor;
    this.dyeColor = dyeColor;
  }
  
  public static FactionColor getByChar(char character) {
    for (FactionColor color : FactionColor.values()) {
      if (color.chatColor.getChar() == character) {
        return color;
      }
    }
    
    return null;
  }
  
  public static FactionColor getByChatColor(ChatColor chatColor) {
    for (FactionColor color : FactionColor.values()) {
      if (color.chatColor == chatColor) {
        return color;
      }
    }
    
    return null;
  }
  
  public static FactionColor getByDyeColor(DyeColor dyeColor) {
    for (FactionColor color : FactionColor.values()) {
      if (color.dyeColor == dyeColor) {
        return color;
      }
    }
    
    return null;
  }
  
  public ChatColor getChatColor() {
    return chatColor;
  }
  
  public DyeColor getDyeColor() {
    return dyeColor;
  }
  
}
