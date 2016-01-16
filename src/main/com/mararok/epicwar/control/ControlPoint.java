/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import java.util.Collection;

import com.mararok.epiccore.math.Position3D;
import com.mararok.epicwar.faction.Faction;

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
   * @param radius In block unit and must be > 2
   */
  public void setRadius(int radius);

  public int getCurrentPower();

  public void setCurrentPower(int currentPower);

  public int getMaxPower();

  public void setMaxPower(int maxPower);

  public boolean canCapture();

  public boolean isFactionHasAnyConnection(Faction faction);

  public boolean isConnectedTo(ControlPoint controlPoint);

  public Collection<ControlPoint> getConnections();

  public void connectTo(ControlPoint controlPoint);

  public void disconnectFrom(ControlPoint controlPoint);

  public Sector getSector();

  public void setSector(Sector sector);

}
