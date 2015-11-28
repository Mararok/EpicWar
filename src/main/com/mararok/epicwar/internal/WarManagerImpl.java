/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.mararok.epicwar.internal;

import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.WarManager;

import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

public class WarManagerImpl implements WarManager {
  private Map<World, WarImpl> wars;
  private EpicWarPlugin plugin;

  public WarManagerImpl(EpicWarPlugin plugin, YamlWarLoader loader) {
    wars = HashObjObjMaps.newMutableMap();
    this.plugin = plugin;
  }

  /*
   * private void load(YamlWarLoader loader) throws Exception {
   * for (String warName : getPlugin().getPluginConfig().getWarList()) {
   * WarImpl war = loader.load(warName);
   * wars.put(warName, war);
   * plugin.getLogger().info("War " + warName + " loaded");
   * }
   * }
   */

  @Override
  public War findByPlayer(Player player) {
    return findByWorld(player.getWorld());
  }

  @Override
  public War findByWorld(World world) {
    return wars.get(world);
  }

  @Override
  public EpicWarPlugin getPlugin() {
    return plugin;
  }
}
