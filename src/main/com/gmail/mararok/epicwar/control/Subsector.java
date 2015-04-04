/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import org.bukkit.Chunk;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;

public class Subsector extends ControlArea {
  public static final int SIZE = 16;
  
  private int chunkX;
  private int chunkZ;
  
  private ControlPoint controlPoint;
  
  public Subsector(int id, int chunkX, int chunkZ, ControlPoint controlPoint) {
    super(id);
    this.chunkX = chunkX;
    this.chunkZ = chunkZ;
    this.controlPoint = controlPoint;
  }
  
  public int getChunkX() {
    return chunkX;
  }
  
  public int getChunkZ() {
    return chunkZ;
  }
  
  public ControlPoint getControlPoint() {
    return controlPoint;
  }
  
  public void setControlPoint(ControlPoint newControlPoint) {
    controlPoint = newControlPoint;
  }
  
  public Sector getSector() {
    return controlPoint.getSector();
  }
  
  public Chunk getChunk() {
    return getWar().getWorld().getChunkAt(chunkX,chunkZ);
  }


  @Override
  public Faction getOwner() {
    return getControlPoint().getOwner();
  }
  
  @Override
  public War getWar() {
    return getControlPoint().getWar();
  }
  
}
