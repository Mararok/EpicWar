/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction;

import java.util.Collection;

import com.mararok.epicwar.War;

/**
 * Manages all factions in one war
 */
public interface FactionManager {

  /**
   * Returns faction with selected id or null when faction doesn't exists
   */
  public Faction findById(int id);

  /**
   * Returns faction with selected color or null when faction doesn't exists
   */
  public Faction findByColor(Faction.Color color);

  /**
   * Returns faction with selected shortcut or null when faction doesn't exists
   */
  public Faction findByShortcut(String shortcut);

  /**
   * Returns faction with selected name or null when faction doesn't exists
   */
  public Faction findByName(String factionName);

  /**
   * Returns all exists factions
   */
  public Collection<Faction> findAll();

  /**
   * Creates new faction from data
   * WARNING: don't use when war isn't in editMode
   * 
   * @return faction instance
   * @throws Exception When faction with the same color exists or when internal storage error
   */
  public Faction create(FactionData data) throws Exception;

  /**
   * Updates changed properties of faction
   * WARNING: don't use when war isn't in editMode
   */
  public void update(Faction faction) throws Exception;

  public War getWar();

}