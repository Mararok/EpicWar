/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.control.SectorData;

public class SectorManager {
  private Vector<SectorImpl> sectors;
  private SectorMapper mapper;
  private War war;

  public SectorManager(SectorMapper mapper, War war) throws Exception {
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

  public Sector findById(int id) {
    return sectors.get(id);
  }

  public Collection<Sector> findAll() {
    return Collections.unmodifiableCollection(sectors);
  }

  public Sector create(SectorData data) throws Exception {
    SectorImpl sector = mapper.insert(data);
    sectors.set(sector.getId(), sector);
    return sector;
  }

  public void update(Sector sector) throws Exception {
    SectorImpl s = (SectorImpl) findById(sector.getId());
    if (s != null) {
      mapper.update(s);
    }
  }

  /**
   * TODO unlink all references to deleted sector in control points
   */
  public void delete(Sector sector) throws Exception {
    SectorImpl s = (SectorImpl) findById(sector.getId());
    if (s != null) {
      mapper.delete(s);
    }
  }

  public War getWar() {
    return war;
  }
}
