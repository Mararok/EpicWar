/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;

/**
 * Base interface for all controlable area
 */
public interface ControlArea {
  public int getId();

  public boolean isOwner(Faction faction);

  public Faction getOwner();

  public War getWar();
}
