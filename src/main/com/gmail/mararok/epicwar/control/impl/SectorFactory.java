/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import com.gmail.mararok.epiccore.util.entity.EntityFactory;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.SectorData;

public class SectorFactory implements EntityFactory<SectorImpl, SectorData> {

  private War war;

  public SectorFactory(War war) {
    this.war = war;
  }

  @Override
  public SectorImpl create(SectorData entityData) throws Exception {
    return new SectorImpl(entityData, war);
  }

}
