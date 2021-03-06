/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.WarSettings;

public class YamlWarLoader {
  private static final String WAR_EXT = ".yml";
  private String basePath;
  private EpicWarPlugin plugin;

  private WarComponentsFactory componentsFactory;

  public YamlWarLoader(String basePath, WarComponentsFactory componentsFactory, EpicWarPlugin plugin) {
    this.basePath = basePath;
    this.componentsFactory = componentsFactory;
    this.plugin = plugin;
  }

  public Collection<WarImpl> loadAll(String warsPath) throws Exception {
    Collection<WarImpl> wars = new Vector<WarImpl>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(basePath, warsPath), "*" + WAR_EXT)) {
      for (Path path : stream) {
        wars.add(load(path));
      }
    } catch (DirectoryIteratorException ex) {
      throw ex.getCause();
    }
    return wars;
  }

  public WarImpl load(Path path) throws Exception {
    plugin.getLogger().info("Loading war from file: " + path);

    File file = path.toFile();
    if (file.exists()) {
      YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
      return createWarFromConfig(config);
    } else {
      throw new FileNotFoundException("War file in path: " + path + " isn't exists");
    }
  }

  private WarImpl createWarFromConfig(YamlConfiguration config) throws Exception {
    int id = config.getInt("id");
    if (id < 1) {
      throw new Exception("War id can't be less then 1");
    }

    String WorldName = config.getString("world.name");
    World world = plugin.getServer().getWorld(WorldName);
    if (world == null) {
      throw new Exception("War world doesn't exists.");
    }

    try {
      WarSettings settings = loadSettings(config);
      return new WarImpl(id, settings, componentsFactory, plugin.getWarManager());
    } catch (Exception e) {
      throw new Exception("War settings error.", e);
    }
  }

  private WarSettings loadSettings(YamlConfiguration config) throws Exception {
    Logger log = plugin.getLogger();
    WarSettings settings = new WarSettings();
    settings.name = config.getString("name");
    log.info("name: " + settings.name);
    settings.description = config.getString("description");
    log.info("description: " + settings.description);

    SimpleDateFormat format = new SimpleDateFormat("DD.MM.YYYY HH:MM");
    settings.startDateTime = format.parse(config.getString("startDateTime"));
    log.info("startDateTime: " + settings.startDateTime);

    settings.editMode = config.getBoolean("editMode");
    log.info("editMode: " + settings.editMode);

    settings.world = loadWorldSettings(config);
    settings.points = loadPointsSettings(config);
    settings.faction = loadFactionSettings(config);
    settings.controlPoint = loadControlPointSettings(config);

    return settings;
  }

  private WarSettings.WorldSettings loadWorldSettings(YamlConfiguration config) {
    Logger log = plugin.getLogger();
    WarSettings.WorldSettings world = new WarSettings.WorldSettings();
    world.name = config.getString("world.name");
    log.info("world.name: " + world.name);

    world.startChunkX = config.getInt("world.fightArea.startChunk.x");
    log.info("world.fightArea.startChunkX: " + world.startChunkX);
    world.startChunkZ = config.getInt("world.fightArea.startChunk.z");
    log.info("world.fightArea.startChunkZ: " + world.startChunkZ);
    world.sizeInChunks = config.getInt("world.fightArea.sizeInChunks");
    log.info("world.fightArea.sizeInChunks: " + world.sizeInChunks);
    return world;
  }

  private WarSettings.PointsSettings loadPointsSettings(YamlConfiguration config) {
    Logger log = plugin.getLogger();
    WarSettings.PointsSettings points = new WarSettings.PointsSettings();
    points.kill = config.getInt("points.kill");
    log.info("points.kill: " + points.kill);
    points.death = config.getInt("points.death");
    log.info("points.death: " + points.death);

    points.captureControlPoint = config.getInt("points.capture.point");
    log.info("points.captureControlPoint: " + points.captureControlPoint);
    points.captureSector = config.getInt("points.capture.sector");
    log.info("points.captureSector: " + points.captureSector);
    return points;
  }

  private WarSettings.FactionSettions loadFactionSettings(YamlConfiguration config) {
    WarSettings.FactionSettions faction = new WarSettings.FactionSettions();
    faction.onlineBalance = config.getBoolean("faction.onlineBalanceEnabled");
    faction.onlineBalancePercentPerFaction = config.getInt("faction.onlineBalancePercentPerFaction");
    return faction;
  }

  private WarSettings.ControlPointSettings loadControlPointSettings(YamlConfiguration config) {
    WarSettings.ControlPointSettings controlPoint = new WarSettings.ControlPointSettings();
    controlPoint.updateInterval = config.getInt("controlPoint.updateInterval");
    controlPoint.powerDefender = config.getInt("controlPoint.power.defender");
    controlPoint.powerAttacker = config.getInt("controlPoint.power.attacker");
    return controlPoint;
  }

}
