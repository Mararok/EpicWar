/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.impl;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.mararok.bukkit.util.Disposable;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.WarManager;
import com.gmail.mararok.epicwar.WarSettings;
import com.gmail.mararok.epicwar.control.impl.SectorManager;
import com.gmail.mararok.epicwar.faction.internal.FactionManager;
import com.gmail.mararok.epicwar.player.impl.PlayerManagerImpl;

public class WarImpl implements War, Disposable {
  private int id;
  private WarSettings settings;

  private World world;
  private WarManager warManager;

  private PlayerManagerImpl playerManager;
  private FactionManager factionManager;
  private SectorManager sectorManager;

  public WarImpl(int id, WarSettings settings, World world, WarManager warManager, WarComponentsFactory warFactory) {
    this.id = id;
    this.settings = settings;

    this.world = world;
    this.warManager = warManager;

    playerManager = warFactory.newPlayerManager(this);
    factionManager = warFactory.newFactionManager(this);
    sectorManager = warFactory.newSectorManager(this);
  }

  public void load() throws Exception {
    Logger log = warManager.getPlugin().getLogger();
    log.info(String.format("Loading war: %s data", settings.name));
    sectorManager.load();
    factionManager.load();
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
  public PlayerManagerImpl getPlayerManager() {
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
  public WarManager getWarManager() {
    return warManager;
  }

  @Override
  public void dispose() {
    playerManager.dispose();
    factionManager.dispose();
    sectorManager.dispose();
  }
}
