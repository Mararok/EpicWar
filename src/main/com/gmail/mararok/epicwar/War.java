/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.mararok.bukkit.util.Disposable;

import com.gmail.mararok.epicwar.control.SectorManager;
import com.gmail.mararok.epicwar.faction.FactionManager;
import com.gmail.mararok.epicwar.player.PlayerManager;

public class War implements Disposable {
  private int id;
  private WarSettings settings;
  
  private World world;
  private WarManager wars;
  
  private PlayerManager players;
  private SectorManager sectors;
  private FactionManager factions;

  public War(int id, WarSettings settings, World world, WarManager wars, WarComponentsFactory warFactory) {
    this.id = id;
    this.settings = settings;
    
    this.world = world;
    this.wars = wars;
  
    players = warFactory.newPlayerManager(this);
    factions = warFactory.newFactionManager(this);
    sectors = warFactory.newSectorManager(this);
  }

  public void load() throws Exception {
    getPlugin().getLogger().info(String.format("Loading war: %s data", settings.name));
    sectors.load();
    factions.load();
    getPlugin().getLogger().info(String.format("War: %s data loaded", settings.name));
  }

  
  public Location getNeutralSpawn() {
    return world.getSpawnLocation();
  }
  
  public int getId() {
    return id;
  }
  
  public WarSettings getSettings() {
    return settings.clone();
  }

  public World getWorld() {
    return world;
  }

  public PlayerManager getPlayers() {
    return players;
  }

  public FactionManager getFactions() {
    return factions;
  }

  public SectorManager getSectors() {
    return sectors;
  }

  public WarManager getWars() {
    return wars;
  }

  public EpicWarPlugin getPlugin() {
    return wars.getPlugin();
  }

  @Override
  public void dispose() {
    players.dispose();
    factions.dispose();
    sectors.dispose();
  }
}
