/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.subsector.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Chunk;

import com.mararok.epicwar.War;
import com.mararok.epicwar.WarSettings;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.Subsector;
import com.mararok.epicwar.control.SubsectorData;
import com.mararok.epicwar.control.SubsectorMap;

/**
 * Represents all war action terrian
 * Map can be only defined on positive chunk coordinates !!!
 */
public class SubsectorMapImpl implements SubsectorMap {
  private int startChunkX;
  private int startChunkZ;
  private int sizeInChunks;

  private Subsector[] subsectors;
  private SubsectorMapper mapper;
  private War war;

  public SubsectorMapImpl(SubsectorMapper mapper, War war) throws Exception {
    WarSettings.WorldSettings worldSettings = war.getSettings().world;
    this.startChunkX = worldSettings.startChunkX;
    this.startChunkZ = worldSettings.startChunkZ;
    this.sizeInChunks = worldSettings.sizeInChunks;

    subsectors = new SubsectorImpl[sizeInChunks * sizeInChunks];
    this.mapper = mapper;
    this.war = war;

    this.loadAll();
  }

  private void loadAll() throws Exception {
    Collection<SubsectorImpl> collection = mapper.findAll();
    for (SubsectorImpl subsector : collection) {
      set(subsector);
    }
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

  @Override
  public Subsector assignToControlPoint(int chunkX, int chunkZ, ControlPoint controlPoint) throws Exception {
    int localChunkX = getLocalChunkX(chunkX);
    int localChunkZ = getLocalChunkZ(chunkZ);

    if (isLocalPointInner(localChunkX, localChunkZ)) {
      Subsector subsector = getLocal(localChunkX, localChunkZ);
      if (subsector != null) {
        subsector.setControlPoint(controlPoint);
        mapper.update((SubsectorImpl) subsector);
      } else {
        SubsectorData data = new SubsectorData();
        data.chunkX = chunkX;
        data.chunkZ = chunkZ;
        data.controlPointId = controlPoint.getId();
        subsector = mapper.insert(data);
        set(subsector);
      }

      return subsector;
    }

    throw new Exception(String.format("Chunk[%d, %d], local[%d, %d] isn't in war area", chunkX, chunkZ, localChunkX, localChunkZ));

  }

  @Override
  public Subsector assignToControlPoint(Chunk chunk, ControlPoint controlPoint) throws Exception {
    return assignToControlPoint(chunk.getX(), chunk.getZ(), controlPoint);
  }

  @Override
  public Collection<Subsector> assignToControlPointInRadius(Chunk centerChunk, int radius, ControlPoint controlPoint) throws Exception {
    return assignToControlPointInRadius(centerChunk.getX(), centerChunk.getZ(), radius, controlPoint);
  }

  @Override
  public Collection<Subsector> assignToControlPointInRadius(int centerChunkX, int centerChunkZ, int radius, ControlPoint controlPoint) throws Exception {
    Collection<Subsector> subsectors = new ArrayList<Subsector>();
    for (int chunkX = centerChunkX - radius; chunkX <= centerChunkX + radius; ++chunkX) {
      for (int chunkZ = centerChunkZ - radius; chunkZ <= chunkZ + radius; ++chunkZ) {
        int localChunkX = getLocalChunkX(chunkX);
        int localChunkZ = getLocalChunkZ(chunkZ);

        if (isLocalPointInner(localChunkX, localChunkZ) && get(chunkX, chunkZ) == null) {
          Subsector subsector = assignToControlPoint(chunkX, chunkZ, controlPoint);
          subsectors.add(subsector);
        }
      }
    }

    return subsectors;
  }

  @Override
  public void delete(Subsector subsector) throws Exception {
    subsectors[getLocalIndex(subsector)] = null;
    mapper.delete((SubsectorImpl) subsector);
  }

  protected void set(Subsector subsector) {
    subsectors[getLocalIndex(subsector)] = subsector;
  }

  @Override
  public int getLocalIndex(Subsector subsector) {
    return getLocalIndex(getLocalChunkX(subsector), getLocalChunkZ(subsector));
  }

  @Override
  public int getLocalIndex(int localChunkX, int localChunkZ) {
    return localChunkZ * sizeInChunks + localChunkX;
  }

  @Override
  public int getLocalChunkX(Subsector subsector) {
    return getLocalChunkX(subsector.getChunkX());
  }

  @Override
  public int getLocalChunkX(int chunkX) {
    return chunkX - startChunkX;
  }

  @Override
  public int getLocalChunkZ(Subsector subsector) {
    return getLocalChunkZ(subsector.getChunkZ());
  }

  @Override
  public int getLocalChunkZ(int chunkZ) {
    return chunkZ - startChunkZ;
  }

  @Override
  public boolean isLocalPointInner(int localChunkX, int localChunkZ) {
    return (localChunkX >= 0 && localChunkX < sizeInChunks && localChunkZ >= 0 && localChunkZ < sizeInChunks);
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

  @Override
  public War getWar() {
    return war;
  }

}
