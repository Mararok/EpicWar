/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;


public class WildSector extends Sector {

  public WildSector(SectorInfo info, SectorManager sectors) {
    super(info, sectors);
  }

  @Override
  public void init() {
  }

  @Override
  public void tryCapture(int newOwner) {
  }

  @Override
  public boolean canCapture(int newOwner) {
    return false;
  }

  @Override
  public boolean isPointInner(int x, int z) {
    return false;
  }

  @Override
  public void addControlPoint(ControlPoint point) {
  }

}
