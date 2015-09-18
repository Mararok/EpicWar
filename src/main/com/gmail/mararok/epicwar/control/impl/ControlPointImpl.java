/**
 * EpicWar
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlAreaPower;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.control.Subsector;
import com.gmail.mararok.epicwar.faction.Faction;

public class ControlPointImpl extends NamedControlAreaImpl implements ControlPoint {
  private Location location;
  private int radius;
  private ControlAreaPower power;

  private Faction owner;
  private Occupiers occupiers;

  private List<Subsector> subsectors;
  private Sector sector;

  public ControlPointImpl(int id, String shortName, String name, Location location) {
    super(id, shortName, name);
    this.location = location;

    radius = 2;
    power = new ControlAreaPower(100);
    occupiers = new Occupiers(this);

    subsectors = new LinkedList<Subsector>();
  }

  @Override
  public Location getLocation() {
    return location.clone();
  }

  @Override
  public int getRadius() {
    return radius;
  }

  public void setRadius(int newRadius) {
    if (newRadius < 2) {
      throw new IllegalArgumentException("Can't set radius < 2 for ControlPoint " + location);
    }

    radius = newRadius;
  }

  @Override
  public ControlAreaPower getPower() {
    return power;
  }

  @Override
  public Faction getOwner() {
    return owner;
  }

  public void setOwner(Faction newOwner) {
    owner = newOwner;
  }

  @Override
  public Occupiers getOccupiers() {
    return occupiers;
  }

  @Override
  public void addSubsector(Subsector subsector) {
    if (subsector.getControlPoint() != null) {
      subsector.getControlPoint().removeSubsector(subsector);
    }

    subsectors.add(subsector);
    subsector.setControlPoint(this);
  }

  @Override
  public void removeSubsector(Subsector subsector) {
    if (subsectors.remove(subsector)) {
      subsector.setControlPoint(null);
    }
  }

  @Override
  public Sector getSector() {
    return sector;
  }

  public void setSector(SectorImpl newSector) {
    sector = newSector;
  }


  @Override
  public War getWar() {
    return sector.getWar();
  }

}
