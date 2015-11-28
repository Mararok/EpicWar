/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.mararok.epicwar;

import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.mararok.epiccore.EventManager;
import com.mararok.epiccore.database.DatabaseConnection;
import com.mararok.epiccore.language.Language;
import com.mararok.epicwar.command.Commands;
import com.mararok.epicwar.internal.WarComponentsFactory;
import com.mararok.epicwar.internal.WarManagerImpl;
import com.mararok.epicwar.internal.YamlWarLoader;

public final class EpicWarPlugin extends JavaPlugin {
  private EpicWarConfig config;
  private Language language;

  private DatabaseConnection databaseConnection;
  private WarManager warManager;

  private Commands commands;
  private EventManager eventManager;

  @Override
  public void onEnable() {
    try {
      config = new EpicWarConfig(this);
      initDatabase();
      initWarManager();

      initEventManager();
      initCommands();

      checkReload();

    } catch (Exception exception) {
      getLogger().log(Level.SEVERE, "Fatal error, can't continue: ", exception);
      setEnabled(false);
    }
  }

  private void initDatabase() throws Exception {

  }

  private void initWarManager() throws Exception {
    WarComponentsFactory componentsFactory = new WarComponentsFactory();
    warManager = new WarManagerImpl(this, new YamlWarLoader(this.getDataFolder().getPath(), componentsFactory, this););
  }

  private void initEventManager() {
    eventManager = new EventManager(this);
    eventManager.setEnabled(config.getDebugEventEnabled());
    eventManager.setLogging(config.getDebugEventLogging());
  }

  private void initCommands() {
    commands = new Commands(this);
  }

  private void checkReload() {
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