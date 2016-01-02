/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.subsector.internal;

import org.bukkit.Chunk;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.Subsector;
import com.mararok.epicwar.control.SubsectorData;
import com.mararok.epicwar.control.internal.ControlAreaImpl;
import com.mararok.epicwar.faction.Faction;

public class SubsectorImpl extends ControlAreaImpl implements Subsector {
  public static final int SIZE = 16;

  private int chunkX;
  private int chunkZ;

  private ControlPoint controlPoint;

  public SubsectorImpl(SubsectorData data, War war) {
    super(data);
    controlPoint = war.getControlPointManager().findById(data.controlPointId);
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
  public void setControlPoint(ControlPoint controlPoint) {
    this.controlPoint = controlPoint;
    onChangeProperty("controlPointId", controlPoint.getId());
  }

  @Override
  public Sector getSector() {
    return getControlPoint().getSector();
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
