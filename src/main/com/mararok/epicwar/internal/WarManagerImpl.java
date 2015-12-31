/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.mararok.epicwar.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.WarManager;

public class WarManagerImpl implements WarManager {
  private Map<World, War> wars;
  YamlWarLoader loader;
  private EpicWarPlugin plugin;

  public WarManagerImpl(YamlWarLoader loader, EpicWarPlugin plugin) {
    wars = new HashMap<World, War>();
    this.loader = loader;
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
  public Collection<War> findAll() {
    return wars.values();
  }

  @Override
  public EpicWarPlugin getPlugin() {
    return plugin;
  }

}
