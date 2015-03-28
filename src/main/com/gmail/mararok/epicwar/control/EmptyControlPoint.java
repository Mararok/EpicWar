/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import com.gmail.mararok.epicwar.player.WarPlayer;

public class EmptyControlPoint extends ControlPoint {

  public EmptyControlPoint(ControlPointInfo info, ControlPointManager controlPoints) {
    super(info, controlPoints);
  }

  @Override
  public void init() {
  }

  @Override
  public void update() {
  }

  @Override
  public void addOccupant(WarPlayer occupant) {
  }

  @Override
  public void removeOccupant(WarPlayer occupant) {
  }

  @Override
  public boolean isUnderSige() {
    return false;
  }

  @Override
  public boolean canCapture() {
    return false;
  }

  @Override
  public boolean isWithin(WarPlayer warPlayer) {
    return false;
  }

}
