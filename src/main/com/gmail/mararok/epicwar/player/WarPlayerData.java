/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import java.util.UUID;

import com.gmail.mararok.epiccore.entity.EntityData;

public class WarPlayerData extends EntityData {
  public UUID uuid;
  public int factionId;
  public PlayerStats stats;
}
