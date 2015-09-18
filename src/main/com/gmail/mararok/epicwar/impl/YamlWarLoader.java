/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.WarSettings;

public class YamlWarLoader extends WarLoader {
  private static final String WAR_EXT = ".yml";
  private String basePath;
  private EpicWarPlugin plugin;

  public YamlWarLoader(String basePath, EpicWarPlugin plugin) {
    this.basePath = basePath;
    this.plugin = plugin;
  }

  @Override
  public WarImpl load(String warName) throws Exception {
    plugin.getLogger().info("Loading war: "+warName);
    
    World world = plugin.getServer().getWorld(warName);
    if (world == null) {
      throw new Exception("War "+warName+" world doesn't exists.");
    }
    
    String filename = basePath + warName + WAR_EXT;
    File f = new File(basePath + warName + WAR_EXT);
    if (f.exists()) {
      YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
      
      int id = config.getInt("id");
      if (id < 1) {
        throw new Exception("War"+warName+" id can' be less then 1.");
      }
      
      try {
        WarSettings settings = createSettingsFromConfig(config);
        return new WarImpl(id,settings,world,warManager,componentsFactory);
      } catch (Exception e) {
        throw new Exception("War "+warName+" settings error.",e);
      }
      
    } else {
      throw new FileNotFoundException("War file in path: " + filename + WAR_EXT + " not exists.");
    }
  }
  
  private WarSettings createSettingsFromConfig(YamlConfiguration config) throws Exception {
    WarSettings settings = new WarSettings();
    settings.name = config.getString("name");
    settings.description = config.getString("description");
    
    SimpleDateFormat format = new SimpleDateFormat("DD.MM.YYYY HH:MM");
    settings.startDateTime = format.parse(config.getString("startDateTime"));
    
    settings.pointsKill = config.getInt("points.kill",1);
    settings.pointsTeamkill = config.getInt("points.teamkill",5);
    settings.pointsDeath = config.getInt("points.death",3);
    
    settings.pointsCapturePoint = config.getInt("points.capture.point",10);
    settings.pointsCaptureSector = config.getInt("points.capture.sector",20);
    
    settings.powerDefender = config.getInt("power.defender",5);
    settings.powerAttacker = config.getInt("power.attacker",10);
    
    return settings;
  }

}
