/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

public class FactionBannerPattern {
  private List<Pattern> patterns = new ArrayList<Pattern>();
  
  public void setupBanner(Banner banner, FactionColor factionColor) {
    banner.setBaseColor(factionColor.getDyeColor());
    banner.setPatterns(patterns);
  }
  
  public Pattern getPattern(int index) {
    return patterns.get(index);
  }
  
  public void addPattern(Pattern pattern) {
    patterns.add(pattern);
  }
  
  public void addPattern(DyeColor color, PatternType type) {
    addPattern(new Pattern(color,type));
  }
  
  public Pattern removePattern(int index) {
    return patterns.remove(index);
  }
  
  public void removePattern(Pattern pattern) {
    patterns.remove(pattern);
  }
  
  /**
   * Serialize Format:
   * <color>:<type>;[<color>:<type>;]
   */
  public String serialize() {
    StringBuilder sb = new StringBuilder();
    
    for (Pattern pattern : patterns) {
      sb.append(pattern.getColor().name()+":"+pattern.getPattern().name()+";");
    }
    
    return sb.toString();
  }
  
  public void deserialize(String serialized) {
    String[] patternStringList = serialized.split(";");
    for (String patternString : patternStringList) {
      String[] pattern = patternString.split(":");
      if (pattern.length == 2) {
        DyeColor color = DyeColor.valueOf(pattern[0]);
        PatternType type = PatternType.valueOf(pattern[1]);
        if (color != null && type != null) {
          addPattern(color,type);
        }
      }
    }
  }
  
}
