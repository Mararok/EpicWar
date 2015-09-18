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
import com.gmail.mararok.bukkit.util.EventManager;
import com.gmail.mararok.bukkit.util.PluginConfig;
import com.gmail.mararok.bukkit.util.command.CommandManager;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.database.DatabaseConnectionConfig;
import com.gmail.mararok.bukkit.util.database.DatabaseConnectionFactory;
import com.gmail.mararok.bukkit.util.database.SqlScriptLoader;
import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.command.Commands;
import com.gmail.mararok.epicwar.control.impl.SectorManager;
import com.gmail.mararok.epicwar.faction.Faction;

import com.gmail.mararok.epicwar.impl.WarComponentsFactory;
import com.gmail.mararok.epicwar.player.impl.DatabasePlayerDao;
import com.gmail.mararok.epicwar.player.impl.EnviromentListener;

public final class EpicWarPlugin extends JavaPlugin {
  private EpicWarConfig config;
  private Language language;

  private DatabaseConnection databaseConnection;
  private WarManager warManager;
  
  private CommandManager commandManager;
  private EventManager eventManager;
 
  public void onEnable() {
    try {
      config = new EpicWarConfig(this);
      initDatabase();
      initWarManager();
      
      initEventManager();
      initCommandManager();
      
      checkReload();
    } catch (Exception exception) {
      getLogger().log(Level.SEVERE, "Fatal error, can't continue: ", exception);
      setEnabled(false);
    }
  }
  
  private void initDatabase() throws Exception {
    
  }
  
  private void initWarManager() throws Exception {
    WarComponentsFactory componentsFactory = new WarComponentsFactory();s
    
    warManager = new WarManager(this);
    warManager.load();
    checkReload();
  }

  private void initEventManager() {
    
  }

  private void initCommandManager() {
    
  }
  
  private void checkReload() {
    Collection<?> playersOnline = getServer().getOnlinePlayers();
    if (playersOnline.size() > 0) {
      //getWarManager().onServerReload(playersOnline);
    }

  }

  public DatabaseConnection getDatabaseConnection() {
    return databaseConnection;
  }
  
  public WarManager getWarManager() {
    return warManager;
  }
  
  public EventManager getEventManager() {
    return eventManager;
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


}