/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import com.mararok.epiccore.Position3D;
import com.mararok.epicwar.control.internal.Occupation;

/**
 * This is a point that controls the set subsectors
 * In minecraft world its design as Beacon with glass(in faction owner color) on top.
 * Can be capture by faction player who is within a radius, when power <= 0.
 * Control point can be connected to other(bidirectional) then faction must capture one for capture next.
 * 
 */
public interface ControlPoint extends NamedControlArea {

  public Position3D getPosition();

  public int getRadius();

  /**
   * @param radius In blocks unit and must be > 2
   */
  public void setRadius(int radius);

  public ControlAreaPower getPower();

  public Occupation getOccupation();

  public Sector getSector();

  public void setSector(Sector sector);

  public ControlPoint[] getConnections();

  public void connectTo(ControlPoint controlPoint);

}
