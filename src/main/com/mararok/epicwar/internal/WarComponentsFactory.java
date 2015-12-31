/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.internal;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.point.internal.ControlPointDatabaseMapper;
import com.mararok.epicwar.control.point.internal.ControlPointFactory;
import com.mararok.epicwar.control.point.internal.ControlPointManagerImpl;
import com.mararok.epicwar.control.sector.internal.SectorDatabaseMapper;
import com.mararok.epicwar.control.sector.internal.SectorFactory;
import com.mararok.epicwar.control.sector.internal.SectorManagerImpl;
import com.mararok.epicwar.control.subsector.internal.SubsectorDatabaseMapper;
import com.mararok.epicwar.control.subsector.internal.SubsectorFactory;
import com.mararok.epicwar.control.subsector.internal.SubsectorMapImpl;
import com.mararok.epicwar.faction.internal.FactionDatabaseMapper;
import com.mararok.epicwar.faction.internal.FactionFactory;
import com.mararok.epicwar.faction.internal.FactionManagerImpl;
import com.mararok.epicwar.player.internal.WarPlayerDatabaseMapper;
import com.mararok.epicwar.player.internal.WarPlayerFactory;
import com.mararok.epicwar.player.internal.WarPlayerManagerImpl;

public class WarComponentsFactory {
  private DMQL queries;

  public WarComponentsFactory(EpicWarPlugin plugin) {
    queries = new DMQL(plugin.getDatabaseConnection());
  }

  public WarPlayerManagerImpl newPlayerManager(War war) throws Exception {
    WarPlayerFactory factory = new WarPlayerFactory(war);
    String tableName = createTableName(war, "players");
    WarPlayerDatabaseMapper mapper = new WarPlayerDatabaseMapper(queries, tableName, factory);

    return new WarPlayerManagerImpl(mapper, war);
  }

  public FactionManagerImpl newFactionManager(War war) throws Exception {
    FactionFactory factory = new FactionFactory(war);
    String tableName = createTableName(war, "factions");
    FactionDatabaseMapper mapper = new FactionDatabaseMapper(queries, tableName, factory);

    return new FactionManagerImpl(mapper, war);
  }

  public SectorManagerImpl newSectorManager(War war) throws Exception {
    SectorFactory factory = new SectorFactory(war);
    String tableName = createTableName(war, "sectors");
    SectorDatabaseMapper mapper = new SectorDatabaseMapper(queries, tableName, factory);

    return new SectorManagerImpl(mapper, war);
  }

  public ControlPointManagerImpl newControlPointManager(War war) throws Exception {
    ControlPointFactory factory = new ControlPointFactory(war);
    String tableName = createTableName(war, "controlPoints");
    ControlPointDatabaseMapper mapper = new ControlPointDatabaseMapper(queries, tableName, factory);

    return new ControlPointManagerImpl(mapper, war);
  }

  public SubsectorMapImpl newSubsectorMap(War war) throws Exception {
    SubsectorFactory factory = new SubsectorFactory(war);
    String tableName = createTableName(war, "subsectors");
    SubsectorDatabaseMapper mapper = new SubsectorDatabaseMapper(queries, tableName, factory);

    return new SubsectorMapImpl(mapper, war);
  }

  private String createTableName(War war, String baseTableName) {
    return "ew" + war.getId() + "_" + baseTableName;
  }
}
