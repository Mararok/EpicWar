/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction;

import java.util.Collection;

import com.mararok.epicwar.War;

public interface FactionManager {

  /**
   * Returns faction with selected id or null when faction doesn't exist;
   */
  Faction findById(int id);

  /**
   * Returns faction with selected color or null when faction doesn't exist;
   */
  Faction findByColor(Faction.Color color);

  /**
   * Returns faction with selected shortcut or null when faction doesn't exist;
   */
  Faction findByShortcut(String shortcut);

  /**
   * Returns faction with selected name or null when faction doesn't exist;
   */
  Faction findByName(String factionName);

  /**
   * Returns all exists factions
   */
  Collection<Faction> findAll();

  /**
   * Creates new faction from data. Where id = colorId
   * 
   * @return faction instance or null when faction with choosen color creates before
   */
  Faction create(FactionData data) throws Exception;

  void update(Faction faction) throws Exception;

  void delete(Faction faction) throws Exception;

  War getWar();

}