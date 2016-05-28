/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.mararok.epicwar;

import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.mararok.epiccore.database.DatabaseConnection;
import com.mararok.epiccore.database.DatabaseConnectionConfig;
import com.mararok.epiccore.database.DatabaseConnectionFactory;
import com.mararok.epiccore.event.EventManager;
import com.mararok.epiccore.event.SimpleEventManager;
import com.mararok.epiccore.language.FileLanguageLoader;
import com.mararok.epiccore.language.Language;
import com.mararok.epicwar.command.Commands;
import com.mararok.epicwar.internal.WarComponentsFactory;
import com.mararok.epicwar.internal.WarManagerImpl;
import com.mararok.epicwar.internal.YamlWarLoader;

public final class EpicWarPlugin extends JavaPlugin {
  public static final String SQL_SCRIPTS_PATH = "sqlscripts/";

  private Language language;

  private DatabaseConnection databaseConnection;
  private EventManager<EpicWarPlugin, EpicWarEvent> eventManager;

  private WarManagerImpl warManager;
  private Commands commands;

  @Override
  public void onEnable() {
    try {
      saveDefaultConfig();
      if (getConfig().getBoolean("firstTime", true)) {
        getLogger().info("You must change 'firstTime' parameter in config to false");
        setEnabled(false);
        return;
      }

      initDatabase();
      initLanguage();
      initEventManager();
      initWarManager();
      initCommands();

      checkReload();

    } catch (Exception e) {
      getLogger().log(Level.SEVERE, "Fatal error, can't continue: ", e);
      setEnabled(false);
    }

  }

  @Override
  public void onDisable() {
    if (databaseConnection != null) {
      try {
        databaseConnection.close();
      } catch (SQLException e) {
        getLogger().log(Level.SEVERE, "Fatal error, can't continue: ", e);
      }
    }
    super.onDisable();
  }

  private void initLanguage() throws Exception {
    saveResource("lang/en.yml", true);

    FileLanguageLoader loader = new FileLanguageLoader(this.getDataFolder().toPath().resolve("lang"));
    language = loader.load(getConfig().getString("language", "en"));
  }

  private void initDatabase() throws Exception {
    updateSqlScripts();

    DatabaseConnectionConfig connectionConfig = new DatabaseConnectionConfig();
    connectionConfig.engine = getConfig().getString("database.engine");
    connectionConfig.host = getConfig().getString("database.host");
    connectionConfig.port = getConfig().getInt("database.port");
    connectionConfig.name = getConfig().getString("database.name");
    connectionConfig.user = getConfig().getString("database.user");
    connectionConfig.password = getConfig().getString("database.password");

    databaseConnection = DatabaseConnectionFactory.newConnection(connectionConfig);
  }

  private void updateSqlScripts() {
    String[] tableScriptNames = new String[] { "ew_Players", "ew_Factions", "ew_Sectors", "ew_ControlPoints", "ew_Subsectors" };
    for (String tableScriptName : tableScriptNames) {
      saveResource(SQL_SCRIPTS_PATH + tableScriptName + ".sql", true);
    }

  }

  private void initWarManager() throws Exception {
    WarComponentsFactory componentsFactory = new WarComponentsFactory(this);
    YamlWarLoader warLoader = new YamlWarLoader(this.getDataFolder().getPath(), componentsFactory, this);
    warManager = new WarManagerImpl(this);
    warManager.loadAll(warLoader);
  }

  private void initEventManager() throws Exception {
    eventManager = new SimpleEventManager<EpicWarPlugin, EpicWarEvent>(this);
  }

  private void initCommands() throws Exception {
    commands = new Commands(this);
    commands.register();
  }

  private void checkReload() throws Exception {
    Collection<?> playersOnline = getServer().getOnlinePlayers();
    if (playersOnline.size() > 0) {
      // getWarManager().onServerReload(playersOnline);
    }

  }

  public DatabaseConnection getDatabaseConnection() {
    return databaseConnection;
  }

  public WarManager getWarManager() {
    return warManager;
  }

  public EventManager<EpicWarPlugin, EpicWarEvent> getEventManager() {
    return eventManager;
  }

  public Language getLanguage() {
    return language;
  }

}