/**
 * EpicWar
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.mararok.epiccore.math.Position3D;
import com.mararok.epiccore.misc.StringUtils;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlAreaPower;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.ControlPointData;
import com.mararok.epicwar.control.ControlPointManager;
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

  private int[] connections;
  private Sector sector;

  public ControlPointImpl(ControlPointData data, War war) {
    super(data);

    position = data.position;
    radius = data.radius;
    power = new ControlAreaPower(data.maxPower);

    owner = war.getFactionManager().findById(data.ownerId);
    occupation = new Occupation(this);

    connections = data.connections;

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
    radius = newRadius;
    onChangeProperty("radius", radius);
  }

  @Override
  public int getCurrentPower() {
    return power.getCurrent();
  }

  @Override
  public void setCurrentPower(int currentPower) {
    power.set(currentPower);
  }

  @Override
  public boolean canCapture() {
    return power.canCapture();
  }

  @Override
  public int getMaxPower() {
    return power.getMax();
  }

  @Override
  public void setMaxPower(int maxPower) {
    power.setMax(maxPower);
    onChangeProperty("maxPower", power.getMax());
  }

  @Override
  public Faction getOwner() {
    return owner;
  }

  public void setOwner(Faction owner) {
    this.owner = owner;
    onChangeProperty("ownerId", (this.owner == null) ? 0 : this.owner.getId());
  }

  @Override
  public boolean isOwner(Faction faction) {
    return owner == faction;
  }

  public Occupation getOccupation() {
    return occupation;
  }

  @Override
  public boolean isFactionHasAnyConnection(Faction faction) {
    if (connections != null) {
      ControlPointManager manager = getWar().getControlPointManager();
      for (int connection : connections) {
        if (manager.findById(connection).isOwner(faction)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isConnectedTo(ControlPoint controlPoint) {
    for (int connection : connections) {
      if (controlPoint.getId() == connection) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Collection<ControlPoint> getConnections() {
    Collection<ControlPoint> collection = new ArrayList<ControlPoint>();
    if (connections != null) {
      ControlPointManager manager = getWar().getControlPointManager();
      for (int connection : connections) {
        collection.add(manager.findById(connection));
      }
    }

    return collection;
  }

  @Override
  public void connectTo(ControlPoint controlPoint) {
    if (connections == null) {
      connections = new int[1];
      connections[0] = controlPoint.getId();
    } else {
      if (isConnectedTo(controlPoint)) {
        return;
      }

      int oldSize = connections.length;
      connections = Arrays.copyOf(connections, oldSize + 1);
      connections[oldSize] = controlPoint.getId();
    }

    controlPoint.connectTo(this);
    onChangeProperty("connections", StringUtils.join(connections, ","));
  }

  @Override
  public void disconnectFrom(ControlPoint controlPoint) {
    if (connections != null && isConnectedTo(controlPoint)) {
      int[] tmpConnections = new int[connections.length - 1];
      int i = 0;
      for (int connection : connections) {
        if (controlPoint.getId() != connection) {
          tmpConnections[i++] = connection;
        }
      }
      connections = tmpConnections;
      disconnectFrom(this);
      onChangeProperty("connections", StringUtils.join(connections, ","));
    }
  }

  @Override
  public Sector getSector() {
    return sector;
  }

  @Override
  public void setSector(Sector sector) {
    this.sector = sector;
    onChangeProperty("sectorId", this.sector.getId());
  }

  @Override
  public War getWar() {
    return sector.getWar();
  }

}
