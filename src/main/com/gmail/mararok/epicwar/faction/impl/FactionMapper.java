/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction.impl;

import java.util.List;

import com.gmail.mararok.epicwar.faction.FactionData;

public interface FactionMapper {
  
  public FactionImpl create(FactionData data) throws Exception;
  public FactionImpl findById(int id) throws Exception;
  public List<FactionImpl> findAll() throws Exception;
  public void update(FactionImpl faction) throws Exception;
}
