/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction.internal;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.faction.FactionData;
import com.gmail.mararok.epicwar.faction.FactionManager;

public class FactionManagerImpl implements FactionManager {
  private FactionImpl[] factions;
  private FactionMapper mapper;
  private War war;

  public FactionManagerImpl(FactionMapper mapper, War war) throws Exception {
    factions = new FactionImpl[Faction.Color.values().length];
    this.mapper = mapper;

    Collection<FactionImpl> collection = mapper.findAll();
    for (FactionImpl faction : collection) {
      factions[faction.getId()] = faction;
    }
    this.war = war;
  }

  @Override
  public Faction findById(int id) {
    return factions[id];
  }

  @Override
  public Faction findByColor(Faction.Color color) {
    return factions[color.ordinal()];
  }

  @Override
  public Faction findByName(String name) {
    for (Faction faction : factions) {
      if (faction.getName().equals(name)) {
        return faction;
      }
    }
    return null;
  }

  @Override
  public Faction findByShortcut(String shortcut) {
    for (Faction faction : factions) {
      if (faction.getShortcut().equals(shortcut)) {
        return faction;
      }
    }
    return null;
  }

  @Override
  public Collection<Faction> findAll() {
    List<Faction> collection = new LinkedList<Faction>();
    for (Faction faction : factions) {
      if (faction != null) {
        collection.add(faction);
      }
    }

    return collection;
  }

  @Override
  public Faction create(FactionData data) throws Exception {
    if (findByColor(data.color) == null) {
      FactionImpl faction = mapper.insert(data);
      factions[faction.getId()] = faction;
      return faction;
    }

    return null;
  }

  @Override
  public void update(Faction faction) throws Exception {
    FactionImpl f = (FactionImpl) findById(faction.getId());
    if (f != null) {
      mapper.update(f);
    }
  }

  /**
   * TODO remove connection with deleted faction in WarPlayer.
   */
  @Override
  public void delete(Faction faction) throws Exception {
    FactionImpl f = (FactionImpl) findById(faction.getId());
    if (f != null) {
      mapper.delete(f);
    }
  }

  @Override
  public War getWar() {
    return war;
  }

}
