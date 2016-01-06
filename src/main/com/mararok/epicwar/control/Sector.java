/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import com.mararok.epicwar.faction.Faction;

/**
 * Represents set of control points
 */
public interface Sector extends NamedControlArea {

  boolean canCapture(Faction other);

  void setOwner(Faction newOwner);

}
