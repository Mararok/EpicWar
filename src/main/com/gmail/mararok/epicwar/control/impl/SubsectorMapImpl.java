/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import org.bukkit.Chunk;

import com.gmail.mararok.epicwar.control.Subsector;
import com.gmail.mararok.epicwar.control.SubsectorMap;

/**
 * Represents all war action terrian
 * Map can be only defined on positive chunk coordinates !!!
 */
public class SubsectorMapImpl implements SubsectorMap {
  private int startChunkX;
  private int startChunkZ;
  private int sizeInChunks;

  private Subsector[] subsectors;

  public SubsectorMapImpl(int startChunkX, int startChunkZ, int sizeInChunks) {
    this.startChunkX = startChunkX;
    this.startChunkZ = startChunkZ;
    this.sizeInChunks = sizeInChunks;

    subsectors = new SubsectorImpl[sizeInChunks * sizeInChunks];
  }

  @Override
  public Subsector get(Chunk chunk) {
    return get(chunk.getX(), chunk.getZ());
  }

  @Override
  public Subsector get(int chunkX, int chunkZ) {
    return getLocal(getLocalChunkX(chunkX), getLocalChunkZ(chunkZ));
  }

  @Override
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

  @Override
  public int getLocalIndex(int localChunkX, int localChunkZ) {
    return localChunkX * sizeInChunks + localChunkX;
  }

  @Override
  public int getLocalChunkX(int chunkX) {
    return chunkX - startChunkX;
  }

  @Override
  public int getLocalChunkZ(int chunkZ) {
    return chunkZ - startChunkZ;
  }

  @Override
  public boolean isLocalPointInner(int localChunkX, int localchunkZ) {
    return (localChunkX >= 0 && localChunkX < sizeInChunks && localchunkZ >= 0 && localchunkZ < sizeInChunks);
  }

  @Override
  public int getStartX() {
    return startChunkX;
  }

  @Override
  public int getStartZ() {
    return startChunkZ;
  }

  @Override
  public int getSizeInChunks() {
    return sizeInChunks;
  }
}
