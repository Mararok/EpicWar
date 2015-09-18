/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import org.bukkit.entity.Player;

import com.gmail.mararok.bukkit.util.entity.EntityFactory;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.internal.FactionImpl;
import com.gmail.mararok.epicwar.player.WarPlayerData;

public class WarPlayerFactory implements EntityFactory<WarPlayerImpl, WarPlayerData> {
  private War war;
  
  public WarPlayerFactory(War war) {
    this.war = war;
  }
  
  @Override
  public WarPlayerImpl create(WarPlayerData entityData) throws Exception {
    return new WarPlayerImpl(entityData, war);
  }

}
