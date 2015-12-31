/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import java.util.Collection;

import com.mararok.epicwar.faction.Faction;

/**
 * Represents set of control points
 */
public interface Sector extends NamedControlArea {

  Collection<ControlPoint> getControlPoints();

  boolean canCapture(Faction other);

  void setOwner(Faction newOwner);

}
