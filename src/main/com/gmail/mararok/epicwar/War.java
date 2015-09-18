/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import org.bukkit.Location;
import org.bukkit.World;

public interface War {
  public int getId();
  public WarSettings getSettings();
  public World getWorld();
  public Location getNeutralSpawn();
  
  /**public PlayerManagerImpl getPlayerManager();
  public FactionManager getFactionManager();
  public SectorManager getSectorManager();
  public ControlPointManager getControlPointManager();*/
  
  public WarManager getWarManager();
}
