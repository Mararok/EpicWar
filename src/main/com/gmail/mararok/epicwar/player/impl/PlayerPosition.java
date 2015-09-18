/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.bukkit.util.UMath;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.control.Subsector;
import com.gmail.mararok.epicwar.control.impl.ControlPointImpl;
import com.gmail.mararok.epicwar.control.impl.SectorImpl;
import com.gmail.mararok.epicwar.control.impl.SubsectorImpl;

public class PlayerPosition {
   private Position3D current = new Position3D();
   private SubsectorImpl subsector;
   
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
      Chunk newChunk = newLocation.getChunk();
      SubsectorImpl newSubsector = player.getWar().getControlAreaManager().getSubsector(newChunk.getX(),newChunk.getZ());
    }
  }
  
  public double getDistanceToPoint(long x, long y, long z) {
    return UMath.getDistance3D(current.x, current.y, current.z,x,y,z);
  }
  
  public boolean isWithinCircle(long x, long y, long z, long radius) {
    return UMath.isPointWithinCircle3D(current.x, current.y, current.z,x,y,z,radius);
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
  
  public WarPlayerImpl getPlayer() {
    return player;
  }
}

