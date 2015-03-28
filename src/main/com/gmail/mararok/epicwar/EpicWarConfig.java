/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.Configuration;

import com.gmail.mararok.bukkit.util.PluginConfig;
import com.gmail.mararok.bukkit.util.database.DatabaseConnectionConfig;

public class EpicWarConfig extends PluginConfig {

  public EpicWarConfig(EpicWarPlugin plugin) {
    super(plugin);
  }
  
  public String getLanguagePrefix() {
    return getConfig().getString("language","en");
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
  
  public List<String> getWarList() {
    List<String> list = getConfig().getStringList("wars");
    return (list != null)? list : new LinkedList<String>();
  }

}
