/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.gmail.mararok.epicwar;

import java.util.Map;

import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

import org.bukkit.World;

import com.gmail.mararok.bukkit.util.Disposable;

public class WarManager implements Disposable {
  private Map<String, War> wars;
  
  private WarLoader loader;
  private EpicWarPlugin plugin;

  public WarManager(WarLoader loader, EpicWarPlugin plugin) {
    wars = HashObjObjMaps.newMutableMap();
    
    this.loader = loader;
    this.plugin = plugin;
  }

  public void load() throws Exception {
    for (String warName : getPlugin().getPluginConfig().getWarList()) {
      War war = loader.load(warName);
      wars.put(warName,war);
      plugin.getLogger().info("War "+warName+" loaded");
    }
  }

  public War get(World world) {
    return wars.get(world.getName());
  }

  public EpicWarPlugin getPlugin() {
    return plugin;
  }

  @Override
  public void dispose() {
    for (War war : wars.values()) {
      war.dispose();
    }

  }
}
