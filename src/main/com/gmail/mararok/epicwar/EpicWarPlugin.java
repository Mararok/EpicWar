/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.gmail.mararok.epicwar;

import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mararok.bukkit.util.DisposableManager;
import com.gmail.mararok.bukkit.util.PluginConfig;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.database.DatabaseConnectionConfig;
import com.gmail.mararok.bukkit.util.database.DatabaseConnectionFactory;
import com.gmail.mararok.bukkit.util.database.SqlScriptLoader;
import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.command.CommandManager;
import com.gmail.mararok.epicwar.command.Commands;
import com.gmail.mararok.epicwar.control.ControlPointManager;
import com.gmail.mararok.epicwar.control.SectorManager;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.EnviromentListener;
import com.gmail.mararok.epicwar.player.PlayerManager;
import com.gmail.mararok.epicwar.player.WarPlayer;

public final class EpicWarPlugin extends JavaPlugin {
  public static final String SQL_SCRIPTS_PATH = "sqlscripts/";

  private DisposableManager disposableManager;

  private EpicWarConfig config;
  private Language language;

  private DatabaseConnection databaseConnection;
  private CommandManager commandManager;
  private WarManager warManager;

  public void onEnable() {
    try {
      config = new EpicWarConfig(this);
      initDatabase();
      initWars();
      //initCommands();

    } catch (Exception exception) {
      getLogger().log(Level.SEVERE, "Fatal error, can't continue: ", exception);
      setEnabled(false);
    }
  }

  private void initDatabase() throws SQLException {
    DatabaseConnectionConfig databaseConnectionConfig = config.getDatabaseConnectionConfig();
    databaseConnection = DatabaseConnectionFactory.newConnection(databaseConnectionConfig,this);
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
    precompileSQL();
  }

  private void updateSQLScript(String scriptName) {
    this.saveResource(SQL_SCRIPTS_PATH+scriptName+".sql",true);
  }


  private void precompileSQL() throws SQLException {
    WarPlayer.precompileSQL();
    Faction.precompileSQL();
    SectorManager.precompileSQL();
    ControlPointManager.precompileSQL();
  }

  private void initWars() throws Exception {
    warManager = new WarManager(this);
    warManager.load();
    checkReload();
  }

  private void checkReload() {
    Collection<? extends Player> playersOnline = getServer().getOnlinePlayers();
    if (playersOnline.size > 0) {
      getWarManager().onServerReload(playersOnline);
    }

  }

  public void onDisable() {
    disposableManager.dispose();
  }

  public WarManager getWarManager() {
    return warManager;
  }

  public Language getLanguage() {
    return language;
  }
  
  public EpicWarConfig getPluginConfig() {
    return config;
  }
  
  public String getDataDumpPath() {
    return getDataFolder().getPath() + "/datadump";
  }

  public DatabaseConnection getDatabaseConnection() {
    return databaseConnection;
  }

}