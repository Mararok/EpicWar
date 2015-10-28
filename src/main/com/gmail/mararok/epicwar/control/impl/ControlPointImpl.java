/**
 * EpicWar
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import org.bukkit.Location;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlAreaPower;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.ControlPointData;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.faction.Faction;

public class ControlPointImpl extends NamedControlAreaImpl implements ControlPoint {
  private Location location;
  private int radius;
  private ControlAreaPower power;

  private Faction owner;
  private Occupiers occupiers;

  private ControlPoint[] connections;
  private Sector sector;

  public ControlPointImpl(ControlPointData data, Sector sector) {
    super(data);
    this.location = new Location(sector.getWar().getWorld(), data.position.x, data.position.y, data.position.z);

    radius = data.radius;
    power = data.power;
    occupiers = new Occupiers(this);
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public int getRadius() {
    return radius;
  }

  @Override
  public void setRadius(int newRadius) {
    if (newRadius < 2) {
      throw new IllegalArgumentException("Can't set radius < 2 for ControlPoint " + getName());
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
  public boolean isOwner(Faction faction) {
    return owner == faction;
  }

  @Override
  public Occupiers getOccupiers() {
    return occupiers;
  }

  @Override
  public Sector getSector() {
    return sector;
  }

  @Override
  public void setSector(Sector newSector) {
    sector = newSector;
  }

  @Override
  public ControlPoint[] getConnections() {
    return connections;
  }

  @Override
  public War getWar() {
    return sector.getWar();
  }

}
