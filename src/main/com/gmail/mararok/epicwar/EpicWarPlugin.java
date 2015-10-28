/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.gmail.mararok.epicwar;

import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mararok.epiccore.EventManager;
import com.gmail.mararok.epiccore.command.CommandManager;
import com.gmail.mararok.epiccore.database.DatabaseConnection;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.impl.WarComponentsFactory;

public final class EpicWarPlugin extends JavaPlugin {
  private EpicWarConfig config;
  private Language language;

  private DatabaseConnection databaseConnection;
  private WarManager warManager;

  private CommandManager<EpicWarPlugin> commandManager;
  private EventManager eventManager;

  @Override
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
    WarComponentsFactory componentsFactory = new WarComponentsFactory();

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