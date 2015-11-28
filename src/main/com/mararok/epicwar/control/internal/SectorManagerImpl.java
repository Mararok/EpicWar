/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.SectorData;
import com.mararok.epicwar.control.SectorManager;

public class SectorManagerImpl implements SectorManager {
  private Vector<SectorImpl> sectors;
  private SectorMapper mapper;
  private War war;

  public SectorManagerImpl(SectorMapper mapper, War war) throws Exception {
    this.mapper = mapper;
    this.war = war;
    loadAll();
  }

  private void loadAll() throws Exception {
    Collection<SectorImpl> collection = mapper.findAll();
    for (SectorImpl sector : collection) {
      sectors.set(sector.getId(), sector);
    }
  }

  @Override
  public Sector findById(int id) {
    return sectors.get(id);
  }

  @Override
  public Collection<Sector> findAll() {
    return Collections.unmodifiableCollection(sectors);
  }

  @Override
  public Sector create(SectorData data) throws Exception {
    SectorImpl sector = mapper.insert(data);
    sectors.set(sector.getId(), sector);
    return sector;
  }

  @Override
  public void update(Sector sector) throws Exception {
    SectorImpl s = (SectorImpl) findById(sector.getId());
    if (s != null) {
      mapper.update(s);
    }
  }

  /**
   * TODO unlink all references to deleted sector in control points
   */
  @Override
  public void delete(Sector sector) throws Exception {
    SectorImpl s = (SectorImpl) findById(sector.getId());
    if (s != null) {
      mapper.delete(s);
    }
  }

  @Override
  public War getWar() {
    return war;
  }
}
