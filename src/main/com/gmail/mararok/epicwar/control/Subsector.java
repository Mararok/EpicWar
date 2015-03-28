/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import org.bukkit.Chunk;

public class Subsector {
  public static final int SIZE = 16;
  
  private int x;
  private int z;
  
  private ControlPoint controlPoint;
  
  public Subsector(int x, int z, ControlPoint controlPoint) {
    this.x = x;
    this.z = z;
    this.controlPoint = controlPoint;
  }
  
  public int getX() {
    return x;
  }
  
  public int getZ() {
    return z;
  }
  
  public ControlPoint getControlPoint() {
    return controlPoint;
  }
  
  public Sector getSector() {
    return controlPoint.getSector();
  }
  
  public Chunk getChunk() {
    return getSector().getSectors().getWar().getWorld().getChunkAt(x,z);
  }
  
}
