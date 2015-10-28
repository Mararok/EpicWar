/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mararok.epiccore.Position3D;
import com.gmail.mararok.epiccore.UMath;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.control.Subsector;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class PlayerPosition {
  private Position3D current = new Position3D();
  private Subsector subsector;

  private WarPlayerImpl player;

  public PlayerPosition(WarPlayerImpl player) {
    this.player = player;
  }

  public void update(PlayerMoveEvent event) {
    Location newLocation = event.getTo();
    int newX = newLocation.getBlockX();
    int newY = newLocation.getBlockY();
    int newZ = newLocation.getBlockZ();

    if (current.x != newX || current.y != newY || current.z != newZ) {
      current.set(newX, newY, newZ);
      subsector = player.getWar().getSubsectorMap().get(newLocation.getChunk());
    }
  }

  public boolean isWithinControlPointRange() {
    ControlPoint controlPoint = getControlPoint();
    Location location = controlPoint.getLocation();
    return isWithinCircle(location.getBlockX(), location.getBlockY(), location.getBlockZ(), controlPoint.getRadius());
  }

  public double getDistanceToPoint(int x, int y, int z) {
    return UMath.getDistance3D(current.x, current.y, current.z, x, y, z);
  }

  public boolean isWithinCircle(int x, int y, int z, int radius) {
    return UMath.isPointWithinCircle3D(current.x, current.y, current.z, x, y, z, radius);
  }

  public Position3D getPosition() {
    return current.clone();
  }

  public Subsector getSubsector() {
    return subsector;
  }

  public ControlPoint getControlPoint() {
    return subsector.getControlPoint();
  }

  public Sector getSector() {
    return subsector.getSector();
  }

  public WarPlayer getPlayer() {
    return player;
  }
}
