/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.mararok.epiccore.Position3D;
import com.mararok.epiccore.entity.ObservedEntity;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.Subsector;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.player.PlayerStats;
import com.mararok.epicwar.player.WarPlayer;
import com.mararok.epicwar.player.WarPlayerData;

public class WarPlayerImpl extends ObservedEntity implements WarPlayer {
  private Player nativePlayer;
  private PlayerStats stats;
  private Faction faction;

  private Position3D position = new Position3D();
  private Subsector subsector;

  public WarPlayerImpl(WarPlayerData data, Player nativePlayer, War war) {
    super(data.id);
    this.nativePlayer = nativePlayer;
    this.stats = data.stats;

    this.faction = war.getFactionManager().findById(data.factionId);
    position = new Position3D();
  }

  public void update(Location nextPosition) {
    int newX = nextPosition.getBlockX();
    int newY = nextPosition.getBlockY();
    int newZ = nextPosition.getBlockZ();

    if (position.y != newY || position.x != newX || position.z != newZ) {
      position.set(newX, newY, newZ);
      if (position.x != newX || position.z != newZ) {
        subsector = getWar().getSubsectorMap().get(nextPosition.getChunk());
      }
    }
  }

  public boolean isWithinControlPointRange() {
    return position.isWithinSphere(getControlPoint().getPosition(), getControlPoint().getRadius());
  }

  @Override
  public Player getNativePlayer() {
    return nativePlayer;
  }

  @Override
  public PlayerStats getStats() {
    return stats;
  }

  @Override
  public void addKill() {
    stats.kills++;
    onChangeProperty("kills", stats.kills);
    addPoints(getWar().getSettings().points.kill);
  }

  @Override
  public void addDeath() {
    stats.deaths++;
    onChangeProperty("deaths", stats.deaths);
    addPoints(getWar().getSettings().points.death);
  }

  @Override
  public void addPoints(int points) {
    stats.points += points;
    onChangeProperty("points", stats.points);
  }

  @Override
  public Faction getFaction() {
    return faction;
  }

  public void setFaction(Faction newFaction) {
    faction = newFaction;
    onChangeProperty("factionId", faction.getId());
  }

  @Override
  public Sector getSector() {
    return getSubsector().getSector();
  }

  @Override
  public ControlPoint getControlPoint() {
    return getSubsector().getControlPoint();
  }

  @Override
  public Subsector getSubsector() {
    return subsector;
  }

  @Override
  public boolean isInWarArea() {
    return subsector != null;
  }

  public Position3D getPosition() {
    return position.clone();
  }

  @Override
  public War getWar() {
    return faction.getWar();
  }

}
