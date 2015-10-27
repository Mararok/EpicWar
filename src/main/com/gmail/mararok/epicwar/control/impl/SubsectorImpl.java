/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import org.bukkit.Chunk;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Subsector;
import com.gmail.mararok.epicwar.control.SubsectorData;
import com.gmail.mararok.epicwar.faction.Faction;

public class SubsectorImpl extends ControlAreaImpl implements Subsector {
  public static final int SIZE = 16;

  private int chunkX;
  private int chunkZ;

  private ControlPoint controlPoint;

  public SubsectorImpl(SubsectorData data, ControlPoint controlPoint) {
    super(data);
  }

  @Override
  public int getChunkX() {
    return chunkX;
  }

  @Override
  public int getChunkZ() {
    return chunkZ;
  }

  @Override
  public Chunk getChunk() {
    return getWar().getWorld().getChunkAt(chunkX, chunkZ);
  }

  @Override
  public ControlPoint getControlPoint() {
    return controlPoint;
  }

  @Override
  public void setControlPoint(ControlPoint newControlPoint) {
    controlPoint = newControlPoint;
  }

  @Override
  public boolean isOwner(Faction faction) {
    return controlPoint.isOwner(faction);
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
