/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gmail.mararok.epicwar.faction.internal.FactionImpl;
import com.gmail.mararok.epicwar.faction.internal.FactionMapper;

public class FactionRepository {
  private FactionImpl[] factions;
  private FactionMapper mapper;

  public FactionRepository(FactionMapper mapper) throws Exception {
    factions = new FactionImpl[FactionColor.values().length];
    this.mapper = mapper;

    loadAll();
  }

  private void loadAll() throws Exception {
    for (FactionImpl faction : mapper.findAll()) {
      factions[faction.getId()] = faction;
    }
  }

  public Faction create(FactionData data) throws Exception {
    FactionImpl faction = mapper.create(data);
    factions[faction.getId()] = faction;
    return faction;
  }

  public Faction findById(int id) {
    return factions[id];
  }

  public Faction findByColor(FactionColor color) {
    return factions[color.ordinal()];
  }

  public Collection<Faction> findAll() {
    List<Faction> list = new ArrayList<Faction>();
    for (Faction faction : factions) {
      if (faction != null) {
        list.add(faction);
      }
    }

    return list;
  }

  public void update(Faction faction) throws Exception {
    FactionImpl factionToDelete = factions[faction.getId()];
    if (factionToDelete != null) {
      mapper.update(factionToDelete);
    }
  }

  public void delete(Faction faction) throws Exception {
    FactionImpl factionToDelete = factions[faction.getId()];
    if (factionToDelete != null) {
      mapper.delete(factionToDelete);
      factions[faction.getId()] = null;
    }
  }

}
