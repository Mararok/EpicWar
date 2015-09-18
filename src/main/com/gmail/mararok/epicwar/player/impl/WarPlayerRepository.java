/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import java.util.Map;

import org.bukkit.entity.Player;

public class WarPlayerRepository {
  private Map<Player, WarPlayerImpl> players;
  private WarPlayerMapper mapper;
  
}
