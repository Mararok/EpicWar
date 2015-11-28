/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import com.mararok.epiccore.entity.EntityFactory;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPointData;

public class ControlPointFactory implements EntityFactory<ControlPointImpl, ControlPointData> {

  private War war;

  public ControlPointFactory(War war) {
    this.war = war;
  }

  @Override
  public ControlPointImpl create(ControlPointData entityData) throws Exception {
    return new ControlPointImpl(entityData, war);
  }

}
