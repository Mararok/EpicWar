/**
 * EpicWar
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.internal;

import java.util.Arrays;

import com.mararok.epiccore.Position3D;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlAreaPower;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.ControlPointData;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.internal.NamedControlAreaImpl;
import com.mararok.epicwar.control.internal.Occupation;
import com.mararok.epicwar.faction.Faction;

public class ControlPointImpl extends NamedControlAreaImpl implements ControlPoint {
  private Position3D position;
  private int radius;
  private ControlAreaPower power;

  private Faction owner;
  private Occupation occupation;

  private ControlPoint[] connections;
  private Sector sector;

  public ControlPointImpl(ControlPointData data, War war) {
    super(data);

    position = data.position;
    radius = data.radius;
    power = data.power;
    occupation = new Occupation(this);
  }

  @Override
  public Position3D getPosition() {
    return position.clone();
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
  public Occupation getOccupation() {
    return occupation;
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
  public void connectTo(ControlPoint controlPoint) {
    if (connections == null) {
      connections = new ControlPoint[1];
      connections[0] = controlPoint;
    } else {
      int oldSize = connections.length;
      connections = Arrays.copyOf(connections, oldSize + 1);
      connections[oldSize] = controlPoint;
    }

    controlPoint.connectTo(this);
  }

  @Override
  public ControlPoint[] getConnections() {
    return connections;
  }

  public void setConnections(ControlPoint[] connections) {
    this.connections = connections;
  }

  @Override
  public War getWar() {
    return sector.getWar();
  }

}
