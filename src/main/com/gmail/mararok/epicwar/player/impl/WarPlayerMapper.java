/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.gmail.mararok.epiccore.entity.EntityMapper;
import com.gmail.mararok.epicwar.player.WarPlayerData;

public interface WarPlayerMapper extends EntityMapper<WarPlayerImpl, WarPlayerData> {
  public WarPlayerImpl findByUUID(UUID uuid) throws Exception;

  public WarPlayerImpl findByPlayer(Player player) throws Exception;
}
