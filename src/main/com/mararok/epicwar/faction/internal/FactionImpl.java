/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction.internal;

import org.bukkit.Location;

import com.mararok.epiccore.entity.ObservedEntity;
import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.faction.FactionData;

public class FactionImpl extends ObservedEntity implements Faction {
  private String name;
  private String shortcut;
  private String description;
  private Color color;
  private BannerPattern bannerPattern;
  private Location spawnLocation;

  private War war;

  public FactionImpl(FactionData data, War war) {
    super(data.id);
    name = data.name;
    shortcut = data.shortcut;
    description = data.description;
    color = data.color;
    bannerPattern = data.bannerPattern;
    spawnLocation = new Location(war.getWorld(), data.spawnPosition.x, data.spawnPosition.y, data.spawnPosition.z);

    this.war = war;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String newName) {
    name = newName;
    onChangeProperty("name", newName);
  }

  @Override
  public String getShortcut() {
    return shortcut;
  }

  @Override
  public void setShortcut(String newShortcut) {
    shortcut = newShortcut;
    onChangeProperty("shortcut", newShortcut);
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String newDescription) {
    description = newDescription;
    onChangeProperty("description", newDescription);
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public BannerPattern getBannerPattern() {
    return bannerPattern;
  }

  @Override
  public void setBannerPattern(BannerPattern newBannerPattern) {
    bannerPattern = newBannerPattern;
    onChangeProperty("bannerPattern", newBannerPattern.serialize());
  }

  @Override
  public War getWar() {
    return war;
  }

  @Override
  public Location getSpawnLocation() {
    return spawnLocation;
  }

  @Override
  public void setSpawnLocation(Location location) {
    spawnLocation = location;
    onChangeProperty("spawnX", spawnLocation.getBlockX());
    onChangeProperty("spawnY", spawnLocation.getBlockY());
    onChangeProperty("spawnZ", spawnLocation.getBlockZ());
  }

  @Override
  public boolean isNeutral() {
    return false;
  }

}
