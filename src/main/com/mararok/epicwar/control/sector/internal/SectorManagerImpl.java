/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.sector.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.SectorData;
import com.mararok.epicwar.control.SectorManager;

public class SectorManagerImpl implements SectorManager {
  private ArrayList<Sector> sectors;
  private SectorMapper mapper;
  private War war;

  public SectorManagerImpl(SectorMapper mapper, War war) throws Exception {
    this.mapper = mapper;
    this.war = war;

    loadAll();
  }

  private void loadAll() throws Exception {
    Collection<SectorImpl> collection = mapper.findAll();
    sectors.ensureCapacity(collection.size() + 1);
    for (Sector sector : collection) {
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
    sectors.ensureCapacity(sector.getId() + 1);
    sectors.set(sector.getId(), sector);
    return sector;
  }

  @Override
  public void update(Sector sector) throws Exception {
    SectorImpl entity = (SectorImpl) sector;
    mapper.update(entity);
    entity.clearChanges();
  }

  @Override
  public War getWar() {
    return war;
  }
}
