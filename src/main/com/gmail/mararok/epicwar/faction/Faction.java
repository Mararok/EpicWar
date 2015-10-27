/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import org.bukkit.Location;

import com.gmail.mararok.epicwar.War;

/**
 * Represents faction of war
 */
public interface Faction {
  public int getId();

  public String getName();

  public void setName(String newName);

  public String getShortcut();

  public void setShortcut(String newShortcut);

  public String getDescription();

  public void setDescription(String newDescription);

  public FactionColor getColor();

  public FactionBannerPattern getBannerPattern();

  public void setBannerPattern(FactionBannerPattern newBannerPattern);

  public Location getSpawnLocation();

  public void setSpawnLocation(Location location);

  public War getWar();

}
