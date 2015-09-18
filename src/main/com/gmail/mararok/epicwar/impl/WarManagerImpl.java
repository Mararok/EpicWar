/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.gmail.mararok.epicwar.impl;

import java.util.Map;

import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

import org.bukkit.World;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.War;

public class WarManagerImpl {
  private Map<String, WarImpl> wars;
  private EpicWarPlugin plugin;

  public WarManagerImpl(EpicWarPlugin plugin) {
    wars = HashObjObjMaps.newMutableMap();
    this.plugin = plugin;
  }

  public void load(WarLoader loader) throws Exception {
    for (String warName : getPlugin().getPluginConfig().getWarList()) {
      WarImpl war = loader.load(warName);
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
}
