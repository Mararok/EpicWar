/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;

public interface PlayerManager {

  public WarPlayer findByPlayer(Player nativePlayer) throws Exception;

  public WarPlayer findByUUID(UUID uuid) throws Exception;

  public WarPlayer register(Player nativePlayer, Faction faction);

  public void update(WarPlayer player);

  public void delete(WarPlayer player);

  public War getWar();
}
