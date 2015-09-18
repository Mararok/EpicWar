/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.util.List;

import com.gmail.mararok.bukkit.util.QueryString;
import com.gmail.mararok.epicwar.impl.WarImpl;

public interface SectorDAO {
  public SectorData create(WarImpl war, String name);
  public List<SectorData> findAll(WarImpl war);
  public void updateOne(SectorImpl sector, QueryString fields);
}
