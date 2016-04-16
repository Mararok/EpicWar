/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction.internal;

import java.util.ArrayList;
import java.util.Collection;

import com.mararok.epiccore.language.Language;
import com.mararok.epiccore.math.Vector3i;
import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.faction.FactionData;
import com.mararok.epicwar.faction.FactionManager;

public class FactionManagerImpl implements FactionManager {
  public final static int MAX_FACTIONS = Faction.Color.values().length;
  private Faction[] factions;
  private FactionMapper mapper;
  private War war;

  public FactionManagerImpl(FactionMapper mapper, War war) throws Exception {
    factions = new Faction[MAX_FACTIONS];
    this.mapper = mapper;
    this.war = war;

    loadAll();
  }

  private void loadAll() throws Exception {
    Collection<FactionImpl> collection = mapper.findAll();
    for (FactionImpl faction : collection) {
      factions[faction.getId()] = faction;
    }

    factions[0] = createNeutralFaction();

  }

  private NeutralFaction createNeutralFaction() {
    Language lang = war.getPlugin().getLanguage();
    FactionData neturalFactionData = new FactionData();
    neturalFactionData.id = 0;
    neturalFactionData.name = lang.getText("constants.faction.neutral.name");
    neturalFactionData.shortcut = lang.getText("constants.faction.neutral.shortcut");
    neturalFactionData.description = lang.getText("constants.faction.neutral.description");
    neturalFactionData.color = Faction.Color.WHITE;
    neturalFactionData.spawnPosition = new Vector3i(0, 0, 0);

    return new NeutralFaction(neturalFactionData, war);
  }

  @Override
  public Faction findById(int id) {
    return (id >= 0 && id < MAX_FACTIONS) ? factions[id] : null;
  }

  @Override
  public Faction findByColor(Faction.Color color) {
    return factions[color.ordinal()];
  }

  @Override
  public Faction findByName(String name) {
    for (Faction faction : factions) {
      if (faction != null && faction.getName().equalsIgnoreCase(name)) {
        return faction;
      }
    }
    return null;
  }

  @Override
  public Faction findByShortcut(String shortcut) {
    for (Faction faction : factions) {
      if (faction != null && faction.getShortcut().equalsIgnoreCase(shortcut)) {
        return faction;
      }
    }
    return null;
  }

  @Override
  public Collection<Faction> findAll() {
    Collection<Faction> collection = new ArrayList<Faction>();
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
      Faction faction = mapper.insert(data);
      factions[faction.getId()] = faction;
      return faction;
    }

    throw new Exception("Can't create faction with the same color");
  }

  @Override
  public void update(Faction faction) throws Exception {
    if (!faction.isNeutral()) {
      FactionImpl entity = (FactionImpl) faction;
      mapper.update(entity);
      entity.clearChanges();
    }
  }

  @Override
  public War getWar() {
    return war;
  }

}
