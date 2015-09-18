/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;

public interface ControlArea {
  public int getId();

  public Faction getOwner();

  public War getWar();
}
