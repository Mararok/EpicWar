/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import com.mararok.epiccore.entity.EntityFactory;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.SectorData;

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
