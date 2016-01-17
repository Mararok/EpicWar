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

  /**
   * Returns sector with selected id or null when sector doesn't exists
   */
  public Sector findById(int id);

  /**
   * Returns all sectors
   */
  public Collection<Sector> findAll();

  /**
   * Creates new sector from data
   * WARNING: don't use when war isn't in editMode
   * 
   * @return sector instance
   * @throws Exception When internal storage error
   */
  public Sector create(SectorData data) throws Exception;

  /**
   * Updates properties of sector
   * WARNING: don't use when war isn't in editMode
   */
  public void update(Sector sector) throws Exception;

  public War getWar();

}