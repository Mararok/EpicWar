/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import com.gmail.mararok.epicwar.control.ControlPointDao;
import com.gmail.mararok.epicwar.control.SectorDao;
import com.gmail.mararok.epicwar.control.SectorManager;
import com.gmail.mararok.epicwar.faction.FactionDao;
import com.gmail.mararok.epicwar.faction.FactionManager;
import com.gmail.mararok.epicwar.player.PlayerDao;
import com.gmail.mararok.epicwar.player.PlayerManager;

public class WarComponentsFactory {
  public PlayerDao playerDao;
  public FactionDao factionDao;
  public SectorDao sectorDao;
  public SubsectorDao subsectorDao;
  public ControlPointDao controlPointDao;
  
  public PlayerManager newPlayerManager(War war) {
    return new PlayerManager(playerDao,war);
  }
  
  public FactionManager newFactionManager(War war) {
    return new FactionManager(factionDao,war);
  }
  
  public SectorManager newSectorManager(War war) {
    return new SectorManager(sectorDao,controlPointDao,war);
  }
}
