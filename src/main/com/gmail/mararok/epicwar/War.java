/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.mararok.epicwar.control.SectorManager;
import com.gmail.mararok.epicwar.control.SubsectorMap;
import com.gmail.mararok.epicwar.faction.FactionManager;
import com.gmail.mararok.epicwar.player.PlayerManager;

public interface War {
  public int getId();

  public WarSettings getSettings();

  public World getWorld();

  public Location getNeutralSpawn();

  public PlayerManager getPlayerManager();

  public FactionManager getFactionManager();

  public SectorManager getSectorManager();

  // public ControlPointManager getControlPointManager();
  public SubsectorMap getSubsectorMap();

  public WarManager getWarManager();

  public EpicWarPlugin getPlugin();
}
