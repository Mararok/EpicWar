/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.mararok.epiccore.entity.ObservedEntity;
import com.mararok.epiccore.math.Vector3i;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.Subsector;
import com.mararok.epicwar.control.point.internal.ControlPointImpl;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.player.PlayerStats;
import com.mararok.epicwar.player.WarPlayer;
import com.mararok.epicwar.player.WarPlayerData;

public class WarPlayerImpl extends ObservedEntity implements WarPlayer {
  private Player nativePlayer;
  private PlayerStats stats;
  private Faction faction;

  private Vector3i position;
  private Subsector subsector;

  public WarPlayerImpl(WarPlayerData data, Player nativePlayer, War war) {
    super(data.id);
    this.nativePlayer = nativePlayer;
    this.stats = data.stats;

    this.faction = war.getFactionManager().findById(data.factionId);
    position = new Vector3i();
  }

  @Override
  public void sendMessage(String message) {
    nativePlayer.sendMessage(message);
  }

  public void updatePosition(Location nextPosition) {
    int newX = nextPosition.getBlockX();
    int newY = nextPosition.getBlockY();
    int newZ = nextPosition.getBlockZ();

    if (position.y != newY || position.x != newX || position.z != newZ) { // checks for any difference
      if (position.x != newX || position.z != newZ) { // checks for any difference in horizontal(maybe player changed subsector)
        Subsector newSubsector = getWar().getSubsectorMap().get(newX, newZ);
        if (subsector != newSubsector) {
          if (isWithinControlPointRange()) {
            ((ControlPointImpl) getControlPoint()).getOccupation().removePlayer(this);
          }
          subsector = newSubsector;
        }
      }

      position.set(newX, newY, newZ);
      if (isWithinControlPointRange()) {
        ((ControlPointImpl) getControlPoint()).getOccupation().addPlayer(this);
      }
    }
  }

  @Override
  public boolean isWithinControlPointRange() {
    return isInWarArea() && position.isWithinSphere(getControlPoint().getPosition(), getControlPoint().getRadius());
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

  public Vector3i getPosition() {
    return position.clone();
  }

  @Override
  public void teleport(Location location) {
    nativePlayer.teleport(location);
  }

  @Override
  public void teleportToFactionCapital() {
    teleport(getFaction().getSpawnLocation());
  }

  @Override
  public Player getNativePlayer() {
    return nativePlayer;
  }

  @Override
  public War getWar() {
    return faction.getWar();
  }

}
