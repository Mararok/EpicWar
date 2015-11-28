/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import org.bukkit.entity.Player;

import com.mararok.epiccore.entity.EntityFactory;
import com.mararok.epicwar.War;
import com.mararok.epicwar.player.WarPlayerData;

public class WarPlayerFactory implements EntityFactory<WarPlayerImpl, WarPlayerData> {
  private War war;

  public WarPlayerFactory(War war) {
    this.war = war;
  }

  @Override
  public WarPlayerImpl create(WarPlayerData entityData) throws Exception {
    Player nativePlayer = war.getWarManager().getPlugin().getServer().getPlayer(entityData.uuid);
    return new WarPlayerImpl(entityData, nativePlayer, war);
  }

}
