/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.internal;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;

import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.WarManager;
import com.mararok.epicwar.WarSettings;
import com.mararok.epicwar.control.SectorManager;
import com.mararok.epicwar.control.SubsectorMap;
import com.mararok.epicwar.control.internal.SubsectorMapImpl;
import com.mararok.epicwar.faction.FactionManager;
import com.mararok.epicwar.faction.internal.FactionManagerImpl;
import com.mararok.epicwar.player.PlayerManager;
import com.mararok.epicwar.player.internal.PlayerManagerImpl;

public class WarImpl implements War {
  private int id;
  private WarSettings settings;

  private World world;

  private PlayerManagerImpl playerManager;
  private FactionManagerImpl factionManager;
  private SectorManager sectorManager;
  // private ControlPointManagerImpl controlPointManager;
  private SubsectorMap subsectorMap;

  private WarManager warManager;

  public WarImpl(int id, WarSettings settings, WarComponentsFactory warFactory, WarManager warManager) {
    this.id = id;
    this.settings = settings;
    this.world = warManager.getPlugin().getServer().getWorld(settings.world.name);
    this.warManager = warManager;

    // playerManager = warFactory.newPlayerManager(this);
    // factionManager = warFactory.newFactionManager(this);
    // sectorManager = warFactory.newSectorManager(this);

    subsectorMap = new SubsectorMapImpl(settings.world.startChunkX, settings.world.startChunkZ, settings.world.sizeInChunks);
  }

  public void load() throws Exception {
    Logger log = warManager.getPlugin().getLogger();
    log.info(String.format("Loading war: %s data", settings.name));
    // sectorManager.load();
    // factionManager.load();
    log.info(String.format("War: %s data loaded", settings.name));
  }

  @Override
  public Location getNeutralSpawn() {
    return world.getSpawnLocation();
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
  public PlayerManager getPlayerManager() {
    return playerManager;
  }

  @Override
  public FactionManager getFactionManager() {
    return factionManager;
  }

  @Override
  public SectorManager getSectorManager() {
    return sectorManager;
  }

  @Override
  public SubsectorMap getSubsectorMap() {
    return null;
  }

  @Override
  public WarManager getWarManager() {
    return warManager;
  }

  @Override
  public EpicWarPlugin getPlugin() {
    return warManager.getPlugin();
  }

}
