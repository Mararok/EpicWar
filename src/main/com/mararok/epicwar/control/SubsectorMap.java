/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import java.util.Collection;

import org.bukkit.Chunk;

import com.mararok.epicwar.War;

/**
 * Represents all subsectors
 */
public interface SubsectorMap {

  /**
   * Returns subsector or null when isn't exists
   */
  Subsector get(Chunk chunk);

  Subsector get(int chunkX, int chunkZ);

  Subsector getLocal(int localChunkX, int localChunkZ);

  int getLocalIndex(int localChunkX, int localChunkZ);

  /**
   * Converts world chunk x coordinate to local subsector map coordinate
   */
  int getLocalChunkX(int chunkX);

  int getLocalChunkX(Subsector subsector);

  /**
   * Converts world chunk y coordinate to local subsector map coordinate
   */
  int getLocalChunkZ(int chunkZ);

  int getLocalChunkZ(Subsector subsector);

  boolean isLocalPointInner(int localChunkX, int localChunkZ);

  /**
   * @see #assignToControlPoint(int, int, ControlPoint)
   */
  Subsector assignToControlPoint(Chunk chunk, ControlPoint controlPoint) throws Exception;

  Subsector assignToControlPoint(int chunkX, int chunkZ, ControlPoint controlPoint) throws Exception;

  /**
   * @see #assignToControlPointInRadius(int, int, int, ControlPoint)
   */
  Collection<Subsector> assignToControlPointInRadius(Chunk centerChunk, int radius, ControlPoint controlPoint) throws Exception;

  /**
   * Assigns subsectors in circle with specified radius to ControlPoint, only if subsector is free(without ControlPoint)
   */
  Collection<Subsector> assignToControlPointInRadius(int centerChunkX, int centerChunkZ, int radius, ControlPoint controlPoint) throws Exception;

  void delete(Subsector subsector) throws Exception;

  int getStartX();

  int getStartZ();

  int getSizeInChunks();

  War getWar();

  int getLocalIndex(Subsector subsector);

}