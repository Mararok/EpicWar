/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import com.gmail.mararok.epicwar.control.SectorInfo;

public interface SectorsGenerator {
  public String getName();

  public void setConfig(ConfigurationSection config)
      throws SectorsGeneratorConfigException;

  public List<SectorInfo> generate(Location creatorLocation);
}
