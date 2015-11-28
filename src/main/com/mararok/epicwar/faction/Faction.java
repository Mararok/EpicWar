/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

import com.mararok.epicwar.War;

/**
 * Represents faction of war
 */
public interface Faction {
  public int getId();

  public String getName();

  public void setName(String newName);

  public String getShortcut();

  public void setShortcut(String newShortcut);

  public String getDescription();

  public void setDescription(String newDescription);

  public Color getColor();

  public BannerPattern getBannerPattern();

  public void setBannerPattern(BannerPattern newBannerPattern);

  public Location getSpawnLocation();

  public void setSpawnLocation(Location location);

  public War getWar();

  public static enum Color {

    BLACK(ChatColor.BLACK, DyeColor.BLACK),
    BLUE(ChatColor.BLUE, DyeColor.BLUE),
    GRAY(ChatColor.GRAY, DyeColor.GRAY),
    GREEN(ChatColor.GREEN, DyeColor.GREEN),
    RED(ChatColor.RED, DyeColor.RED),
    WHITE(ChatColor.WHITE, DyeColor.WHITE),
    YELLOW(ChatColor.YELLOW, DyeColor.YELLOW);

    private ChatColor chatColor;
    private DyeColor dyeColor;

    private Color(ChatColor chatColor, DyeColor dyeColor) {
      this.chatColor = chatColor;
      this.dyeColor = dyeColor;
    }

    public static Color getByChar(char character) {
      for (Color color : Color.values()) {
        if (color.chatColor.getChar() == character) {
          return color;
        }
      }

      return null;
    }

    public static Color getByChatColor(ChatColor chatColor) {
      for (Color color : Color.values()) {
        if (color.chatColor == chatColor) {
          return color;
        }
      }

      return null;
    }

    public static Color getByName(String name) {
      name = name.toUpperCase();
      for (Color color : Color.values()) {
        if (color.name() == name) {
          return color;
        }
      }

      return null;
    }

    public static Color getByDyeColor(DyeColor dyeColor) {
      for (Color color : Color.values()) {
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

  public static class BannerPattern {
    private List<Pattern> patterns = new ArrayList<Pattern>();

    public void setupBanner(Banner banner, Color factionColor) {
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
      addPattern(new Pattern(color, type));
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
        sb.append(pattern.getColor().name() + ":" + pattern.getPattern().name() + ";");
      }

      return sb.toString();
    }

    public static BannerPattern createFromSerialized(String serialized) {
      BannerPattern bannerPattern = new BannerPattern();
      String[] patternStringList = serialized.split(";");
      for (String patternString : patternStringList) {
        String[] pattern = patternString.split(":");
        if (pattern.length == 2) {
          DyeColor color = DyeColor.valueOf(pattern[0]);
          PatternType type = PatternType.valueOf(pattern[1]);
          if (color != null && type != null) {
            bannerPattern.addPattern(color, type);
          }
        }
      }
      return bannerPattern;
    }

  }
}
