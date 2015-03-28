/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

public interface PlayerDao {
  
  public void load(WarPlayer player);
  public void updateStats(WarPlayer player);
  public void addNew(WarPlayer player);
}
