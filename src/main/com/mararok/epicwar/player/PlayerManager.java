/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.Faction;

public interface PlayerManager {

  public WarPlayer findByPlayer(Player nativePlayer) throws Exception;

  public WarPlayer findByUUID(UUID uuid) throws Exception;

  public WarPlayer register(Player nativePlayer, Faction faction) throws Exception;

  public void update(WarPlayer player) throws Exception;

  public void delete(WarPlayer player) throws Exception;

  public War getWar();
}
