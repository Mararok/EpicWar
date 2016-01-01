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

public class DatabaseHelper {
  public static final String SQL_SCRIPTS_PATH = "sqlscripts/";

  public static DatabaseConnection createConnection(DatabaseConnectionConfig config, EpicWarPlugin plugin) throws SQLException {
    return DatabaseConnectionFactory.newConnection(config, plugin);
  }

  public static void updateSqlScripts(EpicWarPlugin plugin) throws SQLException {
    updateSqlScript("ew_Players", plugin);
    updateSqlScript("ew_Factions", plugin);
    updateSqlScript("ew_Sectors", plugin);
    updateSqlScript("ew_ControlPoints", plugin);
    updateSqlScript("ew_Subsectors", plugin);
  }

  private static void updateSqlScript(String scriptName, EpicWarPlugin plugin) {
    plugin.saveResource(SQL_SCRIPTS_PATH + scriptName + ".sql", true);
  }

}
