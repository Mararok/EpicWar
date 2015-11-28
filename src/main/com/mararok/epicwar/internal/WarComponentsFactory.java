/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.internal;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.internal.ControlPointManagerImpl;
import com.mararok.epicwar.faction.internal.FactionManagerImpl;
import com.mararok.epicwar.player.internal.PlayerManagerImpl;
import com.mararok.epicwar.player.internal.WarPlayerDatabaseMapper;
import com.mararok.epicwar.player.internal.WarPlayerFactory;

public class WarComponentsFactory {
  private DMQL dmql;

  WarComponentsFactory(EpicWarPlugin plugin) {
    dmql = new DMQL(plugin.getDatabaseConnection());
  }

  public PlayerManagerImpl newPlayerManager(War war) {
    WarPlayerFactory factory = new WarPlayerFactory(war);
    String tableName = "ew" + war.getId() + "_players";
    WarPlayerDatabaseMapper mapper = new WarPlayerDatabaseMapper(dmql, tableName, factory);
    return new PlayerManagerImpl(mapper, war);
  }

  public FactionManagerImpl newFactionManager(War war) {

  }

  public ControlPointManagerImpl newControlManager(War war) {

  }
}
