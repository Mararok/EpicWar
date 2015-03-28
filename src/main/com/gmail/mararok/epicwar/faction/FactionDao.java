/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

public interface FactionDao {
  void updateInfo(Faction faction) throws Exception;
  FactionInfo[] findAll() throws Exception;
  
}
