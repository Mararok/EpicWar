/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import org.bukkit.entity.Player;

import com.gmail.mararok.bukkit.util.entity.EntityData;

public class WarPlayerData extends EntityData {
  public Player nativePlayer;
  public int factionId;
  public PlayerStats stats;
}
