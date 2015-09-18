/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import com.gmail.mararok.bukkit.util.Disposable;
import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.impl.WarImpl;

public class SectorManager implements Disposable {
  private Vector<SectorImpl> sectors;
  private SubsectorMap subsectorMap;
  
  private SectorDAO sectorDao;
  private ControlPointMapper controlPointDao;
  private SubsectorDAO subsectorDao;
  
  private WarImpl war;
  
  public SectorManager(SectorDAO sectorDAO, ControlPointMapper controlPointDAO, SubsectorDAO subsectorDAO, WarImpl war) {
    this.sectorDao = sectorDAO;
    this.controlPointDao = controlPointDAO;
    this.subsectorDao = subsectorDAO;
    this.war = war;
  }

  public void load() throws Exception {
    SectorImpl wildSector = createWildSector();
    sectors.set(0,wildSector);
    
    Logger log = getWar().getPlugin().getLogger();
    log.info("Loading sectors: ");
    List<SectorData> infoList = sectorDao.findAll(getWar());
    for (SectorData info : infoList) {
      sectors.set(info.id,SectorImpl.createFromInfo(info,this));
      log.info(" Loaded: "+info.id+":"+info.name);
    }
  }

  private SectorImpl createWildSector() {
    Language lang = getWar().getPlugin().getLanguage();
    SectorImpl wildSector = new SectorImpl(0,lang.getString("sector.wild.name"),this);
    wildSector.setDescription(lang.getString("sector.wild.description"));
    return wildSector;
  }

  public void create(String name) throws Exception {
    SectorData info = sectorDao.create(getWar(), name);
    if (info.id != 0) {
      sectors.set(info.id, SectorImpl.createFromInfo(info,this));
    }
  }

  public SectorImpl getById(int id) {
    return sectors.get(id);
  }

  @Override
  public void dispose() {
    
  }

  public WarImpl getWar() {
    return war;
  }
}
