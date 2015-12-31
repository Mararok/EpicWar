/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.mararok.epicwar.internal;

import java.util.Collection;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.WarManager;

public class WarManagerImpl implements WarManager {
  private Map<World, War> wars;
  private EpicWarPlugin plugin;

  public WarManagerImpl(EpicWarPlugin plugin, YamlWarLoader loader) throws Exception {
    this.plugin = plugin;

    for (War war : loader.loadAll("wars")) {
      wars.put(war.getWorld(), war);
    }

  }

  @Override
  public War findByPlayerWorld(Player player) {
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
