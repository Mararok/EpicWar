/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar;

import org.bukkit.Location;
import org.bukkit.World;

import com.mararok.epicwar.control.ControlPointManager;
import com.mararok.epicwar.control.SectorManager;
import com.mararok.epicwar.control.SubsectorMap;
import com.mararok.epicwar.faction.FactionManager;
import com.mararok.epicwar.player.WarPlayerManager;

public interface War {
  public int getId();

  public WarSettings getSettings();

  public World getWorld();

  public Location getNeutralSpawn();

  public WarPlayerManager getPlayerManager();

  public FactionManager getFactionManager();

  public SectorManager getSectorManager();

  public ControlPointManager getControlPointManager();

  public SubsectorMap getSubsectorMap();

  public WarManager getWarManager();

  public EpicWarPlugin getPlugin();
}
