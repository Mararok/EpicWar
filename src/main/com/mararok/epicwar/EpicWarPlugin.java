/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.mararok.epicwar;

import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.mararok.epiccore.database.DatabaseConnection;
import com.mararok.epiccore.event.DummyEventManager;
import com.mararok.epiccore.event.EventManager;
import com.mararok.epiccore.event.LoggableEventManager;
import com.mararok.epiccore.event.SimpleEventManager;
import com.mararok.epiccore.language.FileLanguageLoader;
import com.mararok.epiccore.language.Language;
import com.mararok.epicwar.command.Commands;
import com.mararok.epicwar.internal.DatabaseHelper;
import com.mararok.epicwar.internal.WarComponentsFactory;
import com.mararok.epicwar.internal.WarManagerImpl;
import com.mararok.epicwar.internal.YamlWarLoader;

public final class EpicWarPlugin extends JavaPlugin {
  private EpicWarConfig config;
  private Language language;

  private DatabaseConnection databaseConnection;
  private WarManager warManager;

  private Commands commands;

  private EventManager<EpicWarPlugin, EpicWarEvent> eventManager;

  @Override
  public void onEnable() {
    try {
      config = new EpicWarConfig(this);

      initLanguage();
      initDatabase();
      initEventManager();
      initWarManager();
      initCommands();

      checkReload();

    } catch (Exception exception) {
      getLogger().log(Level.SEVERE, "Fatal error, can't continue: ", exception);
      setEnabled(false);
    }
  }

  private void initLanguage() throws Exception {
    FileLanguageLoader loader = new FileLanguageLoader(this.getDataFolder().toPath().resolve("lang"));
    language = loader.load(config.getLanguagePrefix());
  }

  private void initDatabase() throws Exception {
    DatabaseHelper.updateSqlScripts(this);
    databaseConnection = DatabaseHelper.createConnection(config.getDatabaseConnectionConfig(), this);
  }

  private void initWarManager() throws Exception {
    WarComponentsFactory componentsFactory = new WarComponentsFactory(this);
    YamlWarLoader warLoader = new YamlWarLoader(this.getDataFolder().getPath(), componentsFactory, this);
    warManager = new WarManagerImpl(this, warLoader);
  }

  private void initEventManager() throws Exception {
    if (config.getDebugEventEnabled()) {
      eventManager = new SimpleEventManager<EpicWarPlugin, EpicWarEvent>(this);
    } else {
      eventManager = new DummyEventManager<EpicWarPlugin, EpicWarEvent>(this);
    }

    if (config.getDebugEventLogging()) {
      eventManager = new LoggableEventManager<EpicWarPlugin, EpicWarEvent>(eventManager);
    }
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

  public EpicWarConfig getPluginConfig() {
    return config;
  }

}