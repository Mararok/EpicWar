/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import org.bukkit.Chunk;

public interface SubsectorMap {

  /** Returns subsector or null when not exists */
  Subsector get(Chunk chunk);

  Subsector get(int chunkX, int chunkZ);

  Subsector getLocal(int localChunkX, int localChunkZ);

  int getLocalIndex(int localChunkX, int localChunkZ);

  /** Converts world chunk x coordinate to local subsector map coordinate */
  int getLocalChunkX(int chunkX);

  /** Converts world chunk y coordinate to local subsector map coordinate */
  int getLocalChunkZ(int chunkZ);

  boolean isLocalPointInner(int localChunkX, int localChunkZ);

  int getStartX();

  int getStartZ();

  int getSizeInChunks();

}