/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.mararok.epiccore.entity.EntityMapper;
import com.mararok.epicwar.player.WarPlayerData;

public interface WarPlayerMapper extends EntityMapper<WarPlayerImpl, WarPlayerData> {

  public WarPlayerImpl findByUUID(UUID uuid) throws Exception;

  public WarPlayerImpl findByPlayer(Player player) throws Exception;
}
