/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlManager;
import com.gmail.mararok.epicwar.control.SubsectorMap;

public class ControlManagerImpl implements ControlManager {
  private SubsectorMap subsectorMap;
  // private SectorRepository sectorRepository;
  private ControlPointRepository controlPointRepository;

  private ControlPointsUpdater controlPointsUpdater;

  private War war;

  ControlManagerImpl(War war) {
    this.war = war;
  }

  @Override
  public SubsectorMap getSubsectorMap() {
    return SubsectorMap;
  }
}
