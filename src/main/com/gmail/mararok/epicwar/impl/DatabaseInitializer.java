/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.impl;

import java.sql.SQLException;

import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.database.DatabaseConnectionConfig;
import com.gmail.mararok.bukkit.util.database.DatabaseConnectionFactory;
import com.gmail.mararok.epicwar.EpicWarPlugin;

public class DatabaseInitializer {
  public static final String SQL_SCRIPTS_PATH = "sqlscripts/";
  private EpicWarPlugin plugin;
  private DatabaseConnection databaseConnection;
  
  public DatabaseInitializer(EpicWarPlugin plugin) {
    this.plugin = plugin;
  }
  
  public void init(DatabaseConnectionConfig config) throws SQLException {
    databaseConnection = DatabaseConnectionFactory.newConnection(config,plugin);
    initSQLs();
  }

  private void initSQLs() throws SQLException {
    updateSQLScript("ew_Players");
    updateSQLScript("ew_Factions");
    updateSQLScript("ew_Sectors");
    updateSQLScript("ew_ControlPoints");
    updateSQLScript("PlayerQueries");
    updateSQLScript("FactionQueries");
    updateSQLScript("SectorsQueries");
    updateSQLScript("ControlPointsQueries");
    //precompileSQL();
  }

  private void updateSQLScript(String scriptName) {
    plugin.saveResource(SQL_SCRIPTS_PATH+scriptName+".sql",true);
  }


}
