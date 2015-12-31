/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.subsector.internal;

import com.mararok.epiccore.entity.EntityFactory;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.SubsectorData;

public class SubsectorFactory implements EntityFactory<SubsectorImpl, SubsectorData> {

  private War war;

  public SubsectorFactory(War war) {
    this.war = war;
  }

  @Override
  public SubsectorImpl create(SubsectorData entityData) throws Exception {
    return new SubsectorImpl(entityData, war);
  }

}
