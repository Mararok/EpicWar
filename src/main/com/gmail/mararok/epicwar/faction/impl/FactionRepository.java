/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction.impl;

import java.util.List;
import java.util.ArrayList;

import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.faction.FactionColor;
import com.gmail.mararok.epicwar.faction.FactionData;

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
  
  public List<Faction> findAll() {
    List<Faction> list = new ArrayList<Faction>();
    for (Faction faction : factions) {
      if (faction != null) {
        list.add(faction);
      }
    }
    
    return list;
  }
  
  public void save(int id) throws Exception {
    FactionImpl faction = factions[id];
    if (faction != null) {
      mapper.update(faction);
    }
  }

}
