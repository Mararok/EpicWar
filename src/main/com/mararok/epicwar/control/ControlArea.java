/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.Faction;

/**
 * Base interface for all controlable area
 */
public interface ControlArea {

  public int getId();

  public boolean isOwner(Faction faction);

  public Faction getOwner();

  public War getWar();
}
