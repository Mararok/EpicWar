/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar;

import org.bukkit.configuration.Configuration;

import com.mararok.epiccore.PluginConfig;
import com.mararok.epiccore.database.DatabaseConnectionConfig;

public class EpicWarConfig extends PluginConfig {

  public EpicWarConfig(EpicWarPlugin plugin) {
    super(plugin);
  }

  public String getLanguagePrefix() {
    return getConfig().getString("language", "en");
  }

  public DatabaseConnectionConfig getDatabaseConnectionConfig() {
    Configuration config = getConfig();
    DatabaseConnectionConfig db = new DatabaseConnectionConfig();
    db.engine = config.getString("database.engine");
    db.host = config.getString("database.host");
    db.dbName = config.getString("database.dbName");
    db.user = config.getString("database.user");
    db.password = config.getString("database.password");

    return db;
  }

  public boolean getDebugEventEnabled() {
    return getConfig().getBoolean("debug.event.enabled", false);
  }

  public boolean getDebugEventLogging() {
    return getConfig().getBoolean("debug.event.logging", false);
  }

}