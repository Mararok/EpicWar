/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import java.util.UUID;

import com.gmail.mararok.epicwar.faction.Faction;

public interface PlayerDao {
  
  public void load(WarPlayer player);
  public void updateStats(WarPlayer player);
  public int registerNew(UUID uuid, Faction faction);
}
