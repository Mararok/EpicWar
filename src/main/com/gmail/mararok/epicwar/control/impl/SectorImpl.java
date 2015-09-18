/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.util.List;

import com.gmail.mararok.bukkit.util.UMath;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.faction.internal.FactionImpl;
import com.gmail.mararok.epicwar.impl.WarImpl;

public class SectorImpl extends NamedControlAreaImpl implements Sector {
  private Faction owner;
  private List<ControlPoint> controlPoints;

  public SectorImpl(int id, String shortName, String name) {
    super(id, shortName, name);
  }

  public static SectorImpl createFromData(SectorData data, SectorManager sectors) {
    SectorImpl sector = new SectorImpl(data.id, data.shortName, data.name);
    sector.setDescription(info.description);
    FactionImpl owner = sectors.getWar().getFactionManager().getById(info.ownerId);
    sector.setOwner(owner);

  }

  public void tryCapture(FactionImpl newOwner) {
    if (canCapture(newOwner)) {
      setOwner(newOwner);
    }
  }

  @Override
  public boolean canCapture(FactionImpl newOwner) {
    for (ControlPointImpl cp : controlPoints) {
      if (cp.getOwner() != newOwner)
        return false;
    }
    return true;
  }

  public void addControlPoint(ControlPoint point) {
    point.setSector(this);
    controlPoints.add(point);
  }

  @Override
  public List<ControlPointImpl> getControlPoints() {
    return controlPoints;
  }

  public void setControlPoints(List<ControlPoint> newControlPoints) {
    controlPoints = newControlPoints;
  }

  @Override
  public FactionImpl getOwner() {
    return owner;
  }

  @Override
  public void setOwner(Faction newOwner) {
    owner = newOwner;
  }

  @Override
  public SectorManager getSectors() {
    return sectors;
  }

  @Override
  public WarImpl getWar() {
    return sectors.getWar();
  }

}
