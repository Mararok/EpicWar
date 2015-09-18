/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.impl;

import com.gmail.mararok.epicwar.control.impl.ControlPointMapper;
import com.gmail.mararok.epicwar.control.impl.SectorDAO;
import com.gmail.mararok.epicwar.control.impl.SectorManager;
import com.gmail.mararok.epicwar.control.impl.SubsectorDAO;
import com.gmail.mararok.epicwar.player.impl.PlayerDAO;
import com.gmail.mararok.epicwar.player.impl.PlayerManagerImpl;

public class WarComponentsFactory {
  public PlayerDAO playerDao;
  public FactionDao factionDao;
  public SectorDAO sectorDao;
  public SubsectorDAO subsectorDao;
  public ControlPointMapper controlPointDao;
  
  public PlayerManagerImpl newPlayerManager(War war) {
    return new PlayerManagerImpl(playerDao,war);
  }
  
  public FactionManager newFactionManager(War war) {
    return new FactionManagerImpl(factionDao,war);
  }
  
  public ControlManager newControlManager(War war) {
    
  }
  
  public SectorManager newSectorManager(War war) {
    return new SectorManager(sectorDao,controlPointDao,subsectorDao,war);
  }
  
  public ControlPointRepository newControlPointManager(ControlManager controlManager) {
    return new ControlPointRepository(controlPointDAO, controlManager);
  }
}
