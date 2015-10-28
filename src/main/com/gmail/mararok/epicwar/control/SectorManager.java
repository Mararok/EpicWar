/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.util.Collection;

import com.gmail.mararok.epicwar.War;

public interface SectorManager {

  Sector findById(int id);

  Collection<Sector> findAll();

  Sector create(SectorData data) throws Exception;

  void update(Sector sector) throws Exception;

  void delete(Sector sector) throws Exception;

  War getWar();

}