/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.Sector;

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
  
  public Sector getCapitalSector();
  public void setCapitalSector(Sector sector);
  
  public War getWar();

}
