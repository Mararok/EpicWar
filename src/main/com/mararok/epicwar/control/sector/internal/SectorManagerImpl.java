/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.sector.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.SectorData;
import com.mararok.epicwar.control.SectorManager;

public class SectorManagerImpl implements SectorManager {
  private Sector[] sectors;
  private SectorMapper mapper;
  private War war;

  public SectorManagerImpl(SectorMapper mapper, War war) throws Exception {
    this.mapper = mapper;
    this.war = war;

    loadAll();
  }

  private void loadAll() throws Exception {
    Collection<SectorImpl> collection = mapper.findAll();
    sectors = new Sector[collection.size() + 1];
    for (Sector sector : collection) {
      sectors[sector.getId()] = sector;
    }
  }

  @Override
  public Sector findById(int id) {
    return (id >= 0 && id < sectors.length) ? sectors[id] : null;
  }

  @Override
  public Collection<Sector> findAll() {
    Collection<Sector> collection;
    if (sectors.length == 1) {
      collection = Collections.emptyList();
    } else {
      collection = new ArrayList<Sector>(sectors.length - 1);

      for (int i = 1; i < sectors.length - 1; i++) {
        collection.add(sectors[i]);
      }
    }
    return Collections.unmodifiableCollection(collection);
  }

  @Override
  public Sector create(SectorData data) throws Exception {
    Sector sector = mapper.insert(data);
    sectors = Arrays.copyOf(sectors, sectors.length + 1);
    sectors[sector.getId()] = sector;
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
