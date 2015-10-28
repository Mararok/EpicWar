/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import org.bukkit.Location;

import com.gmail.mararok.epicwar.control.impl.Occupiers;

/**
 * This is a point that controls the set subsectors
 * In minecraft world its design as Beacon with glass(in faction owner color) on top.
 * Can be capture by faction player who is within a radius, when power <= 0.
 * Control point can be connected to other(bidirectional) then faction must capture one for capture next.
 * 
 */
public interface ControlPoint extends NamedControlArea {
  public Location getLocation();

  public int getRadius();

  public void setRadius(int newRadius);

  public ControlAreaPower getPower();

  public Occupiers getOccupiers();

  public Sector getSector();

  public void setSector(Sector newSector);

  public ControlPoint[] getConnections();

}
