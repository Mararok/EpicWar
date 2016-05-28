/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2016 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction.internal;

import org.bukkit.Location;

import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.FactionData;

/**
 * Represents faction with id 0
 */
public class NeutralFaction extends FactionImpl {

  public NeutralFaction(FactionData data, War war) {
    super(data, war);
  }

  @Override
  public void setName(String newName) {
  }

  @Override
  public void setShortcut(String newShortcut) {
  }

  @Override
  public void setDescription(String newDescription) {
  }

  @Override
  public void setBannerPattern(BannerPattern newBannerPattern) {
  }

  @Override
  public void setSpawnLocation(Location location) {
  }

  @Override
  public boolean isNeutral() {
    return true;
  }

}
