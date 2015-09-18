/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import org.bukkit.entity.Player;

import com.gmail.mararok.epicwar.player.WarPlayer;

public interface WarPlayerMapper {
  public WarPlayer findById(int id);
  public WarPlayer findByPlayer(Player player);
}
