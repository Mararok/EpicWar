/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import java.util.Collection;

import com.mararok.epicwar.War;

/**
 * Manage all war sectors
 */
public interface SectorManager {

  Sector findById(int id);

  Collection<Sector> findAll();

  Sector create(SectorData data) throws Exception;

  void update(Sector sector) throws Exception;

  void delete(Sector sector) throws Exception;

  War getWar();

}