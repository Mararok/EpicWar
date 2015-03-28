/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

public class NeutralFaction extends Faction {

  public NeutralFaction(FactionInfo info, FactionManager factions) {
    super(info, factions);
  }

  @Override
  public void init() {
    setSpawnLocation(getFactions().getWar().getNeutralSpawn());
  }

}
