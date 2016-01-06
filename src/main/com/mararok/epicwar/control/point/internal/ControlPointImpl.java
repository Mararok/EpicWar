/**
 * EpicWar
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.internal;

import java.util.Arrays;

import com.mararok.epiccore.math.Position3D;
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
    power = new ControlAreaPower(data.maxPower);

    owner = war.getFactionManager().findById(data.ownerId);
    occupation = new Occupation(this);

    sector = war.getSectorManager().findById(data.sectorId);
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
      throw new IllegalArgumentException("Can't set radius < 2 for Control Point " + getName());
    }

    radius = newRadius;

    onChangeProperty("radius", radius);
  }

  @Override
  public ControlAreaPower getPower() {
    return power;
  }

  public void setMaxPower(int maxPower) {
    power.setMax(maxPower);

    onChangeProperty("maxPower", power.getMax());
  }

  @Override
  public Faction getOwner() {
    return owner;
  }

  public void setOwner(Faction newOwner) {
    owner = newOwner;
    onChangeProperty("ownerId", owner.getId());
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
    onChangeProperty("sectorId", sector.getId());
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

    String connectionsString = "";
    for (int i = 0; i < connections.length - 1; ++i) {
      connectionsString += connections[i].getId() + ",";
    }
    connectionsString += connections[connections.length - 1];

    onChangeProperty("connections", connectionsString);
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
