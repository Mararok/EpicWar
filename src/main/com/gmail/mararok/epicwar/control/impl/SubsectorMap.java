/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import org.bukkit.Chunk;

import com.gmail.mararok.epicwar.control.Subsector;

/**
 * Represents all war action terrian
 * Map can be only defined on positive chunk coordinates !!!
 */
public class SubsectorMap {
  private int startChunkX;
  private int startChunkZ;

  private int sizeInChunks;

  private Subsector[] subsectors;

  public SubsectorMap(int startChunkX, int startChunkZ, int sizeInChunks) {
    this.startChunkX = startChunkX;
    this.startChunkZ = startChunkZ;
    this.sizeInChunks = sizeInChunks;

    subsectors = new SubsectorImpl[sizeInChunks * sizeInChunks];
  }

  /** Return subsector or null when not exists */
  public Subsector get(Chunk chunk) {
    return get(chunk.getX(), chunk.getZ());
  }

  public Subsector get(int chunkX, int chunkZ) {
    return getLocal(getLocalChunkX(chunkX), getLocalChunkZ(chunkZ));
  }

  public Subsector getLocal(int localChunkX, int localChunkZ) {
    return (isLocalPointInner(localChunkX, localChunkX)) ? subsectors[getLocalIndex(localChunkX, localChunkZ)] : null;
  }

  public void set(Subsector subsector) {
    int localChunkX = getLocalChunkX(subsector.getChunkX());
    int localChunkZ = getLocalChunkX(subsector.getChunkZ());

    if (isLocalPointInner(localChunkX, localChunkX)) {
      subsectors[getLocalIndex(localChunkX, localChunkZ)] = subsector;
    }
  }

  public final int getLocalIndex(int localChunkX, int localChunkZ) {
    return localChunkX * sizeInChunks + localChunkX;
  }

  public final int getLocalChunkX(int chunkX) {
    return chunkX - startChunkX;
  }

  public final int getLocalChunkZ(int chunkZ) {
    return chunkZ - startChunkZ;
  }

  public final boolean isLocalPointInner(int chunkX, int chunkZ) {
    return (chunkX >= 0 && chunkX < sizeInChunks && chunkZ >= 0 && chunkZ < sizeInChunks);
  }

  public int getStartX() {
    return startChunkX;
  }

  public int getStartZ() {
    return startChunkZ;
  }

  public int getSizeInChunks() {
    return sizeInChunks;
  }
}
