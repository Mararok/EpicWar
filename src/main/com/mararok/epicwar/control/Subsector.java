/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import org.bukkit.Chunk;

/**
 * Represents fragment of sector(in minecraft world its one chunk), must be connected to Control Point
 */
public interface Subsector extends ControlArea {
  public int getChunkX();

  public int getChunkZ();

  public Chunk getChunk();

  public ControlPoint getControlPoint();

  public void setControlPoint(ControlPoint newControlPoint);

  public Sector getSector();
}
