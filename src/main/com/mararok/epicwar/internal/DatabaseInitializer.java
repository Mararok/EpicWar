/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.internal;

import java.sql.SQLException;

import com.mararok.epiccore.database.DatabaseConnection;
import com.mararok.epiccore.database.DatabaseConnectionConfig;
import com.mararok.epiccore.database.DatabaseConnectionFactory;
import com.mararok.epicwar.EpicWarPlugin;

public class DatabaseInitializer {
  public static final String SQL_SCRIPTS_PATH = "sqlscripts/";
  private EpicWarPlugin plugin;

  public DatabaseInitializer(EpicWarPlugin plugin) {
    this.plugin = plugin;
  }

  public DatabaseConnection createConnection(DatabaseConnectionConfig config) throws SQLException {
    initSQLs();
    return DatabaseConnectionFactory.newConnection(config, plugin);
  }

  private void initSQLs() throws SQLException {
    updateSQLScript("ew_Players");
    updateSQLScript("ew_Factions");
    updateSQLScript("ew_Sectors");
    updateSQLScript("ew_ControlPoints");
  }

  private void updateSQLScript(String scriptName) {
    plugin.saveResource(SQL_SCRIPTS_PATH + scriptName + ".sql", true);
  }

}
