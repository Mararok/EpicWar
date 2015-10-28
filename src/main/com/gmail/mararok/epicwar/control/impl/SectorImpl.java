/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.util.Collection;
import java.util.List;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.control.SectorData;
import com.gmail.mararok.epicwar.faction.Faction;

public class SectorImpl extends NamedControlAreaImpl implements Sector {
  private Faction owner;
  private List<ControlPoint> controlPoints;
  private War war;

  public SectorImpl(SectorData data, War war) {
    super(data);
    this.war = war;
  }

  @Override
  public boolean canCapture(Faction faction) {
    for (ControlPoint controlPoint : controlPoints) {
      if (controlPoint.isOwner(faction))
        return false;
    }
    return true;
  }

  public void addControlPoint(ControlPoint point) {
    point.setSector(this);
    controlPoints.add(point);
  }

  @Override
  public Collection<ControlPoint> getControlPoints() {
    return controlPoints;
  }

  public void setControlPoints(List<ControlPoint> newControlPoints) {
    controlPoints = newControlPoints;
  }

  @Override
  public Faction getOwner() {
    return owner;
  }

  @Override
  public void setOwner(Faction newOwner) {
    owner = newOwner;
  }

  @Override
  public boolean isOwner(Faction faction) {
    return owner == faction;
  }

  @Override
  public War getWar() {
    return war;
  }

}
