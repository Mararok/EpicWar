/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.internal;

import org.bukkit.Location;
import org.bukkit.World;

import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.WarManager;
import com.mararok.epicwar.WarSettings;
import com.mararok.epicwar.control.ControlPointManager;
import com.mararok.epicwar.control.SectorManager;
import com.mararok.epicwar.control.SubsectorMap;
import com.mararok.epicwar.faction.FactionManager;
import com.mararok.epicwar.player.WarPlayerManager;

public class WarImpl implements War {
  private int id;
  private WarSettings settings;

  private World world;

  private WarPlayerManager playerManager;
  private FactionManager factionManager;
  private SectorManager sectorManager;
  private ControlPointManager controlPointManager;
  private SubsectorMap subsectorMap;

  private WarManager warManager;

  public WarImpl(int id, WarSettings settings, WarComponentsFactory warFactory, WarManager warManager) throws Exception {
    this.id = id;
    this.settings = settings;
    this.world = warManager.getPlugin().getServer().getWorld(settings.world.name);

    playerManager = warFactory.newPlayerManager(this);
    factionManager = warFactory.newFactionManager(this);

    controlPointManager = warFactory.newControlPointManager(this);
    subsectorMap = warFactory.newSubsectorMap(this);
    sectorManager = warFactory.newSectorManager(this);

    this.warManager = warManager;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public WarSettings getSettings() {
    return settings.clone();
  }

  @Override
  public World getWorld() {
    return world;
  }

  @Override
  public WarPlayerManager getPlayerManager() {
    return playerManager;
  }

  @Override
  public FactionManager getFactionManager() {
    return factionManager;
  }

  @Override
  public SubsectorMap getSubsectorMap() {
    return subsectorMap;
  }

  @Override
  public ControlPointManager getControlPointManager() {
    return controlPointManager;
  }

  @Override
  public SectorManager getSectorManager() {
    return sectorManager;
  }

  @Override
  public WarManager getWarManager() {
    return warManager;
  }

  @Override
  public Location getNeutralSpawn() {
    return world.getSpawnLocation();
  }

  @Override
  public EpicWarPlugin getPlugin() {
    return warManager.getPlugin();
  }

}
