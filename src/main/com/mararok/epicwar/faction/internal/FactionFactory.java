/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction.internal;

import com.mararok.epiccore.entity.EntityFactory;
import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.FactionData;

public class FactionFactory implements EntityFactory<FactionImpl, FactionData> {
  private War war;
  
  public FactionFactory(War war) {
    this.war = war;
  }
  
  @Override
  public FactionImpl create(FactionData entityData) throws Exception {
    return new FactionImpl(entityData, war);
  }
  
}
