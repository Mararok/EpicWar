/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.gmail.mararok.bukkit.util.UMath;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.util.DataObject;

public class Sector {
  private SectorInfo info;
  private List<ControlPoint> controlPoints;
  private SectorManager sectors;

  public Sector(SectorInfo info, SectorManager sectors) {
    this.info = info;
    this.sectors = sectors;
  }

  public void init() {
    controlPoints = sectors.getControlPoints().getControlPointsFor(this);
  }

  public void tryCapture(int newOwner) {
    if (canCapture(newOwner)) {
      getOwner().onLostSector(this);
      getSectors().getFactions().getByID(newOwner).onCaptureSector(this);
      setOwner(newOwner);
      getSectors().getWar().getWoolMaps().onSectorCapture(this);
    }
  }

  public boolean canCapture(int newOwner) {
    for (ControlPoint cp : controlPoints) {
      if (cp.getInfo().ownerFactionID != newOwner)
        return false;
    }
    return true;
  }

  public void setOwner(int newOwnerID) {
    getInfo().ownerFactionID = newOwnerID;
    try {
      PreparedStatement st = DatabaseConnection.get().prepareQuery(
          "UPDATE ew_Sectors SET ownerID = ? WHERE id = ?");
      st.setInt(1, newOwnerID);
      st.setInt(2, getID());
      st.executeUpdate();
      DatabaseConnection.get().commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean isPointInner(int x, int z) {
    if (UMath.isPointWithinCircle2D(x, z, info.centerX, info.centerZ,
        info.size / 2)) {
      return true;
    }
    return false;
  }

  public void addControlPoint(ControlPoint point) {
    controlPoints.add(point);
  }

  public List<ControlPoint> getControlPoints() {
    return controlPoints;
  }

  public Faction getOwner() {
    return getSectors().getFactions().getByID(getInfo().ownerFactionID);
  }

  public SectorManager getSectors() {
    return sectors;
  }

  @Override
  public SectorInfo getInfo() {
    return info;
  }

  @Override
  public int getID() {
    return info.id;
  }

  @Override
  public String getName() {
    return info.name;
  }

  @Override
  public String toString() {
    return info.toString();
  }
}
