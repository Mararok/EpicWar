/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.util.List;

import com.gmail.mararok.bukkit.util.UMath;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;

public class Sector extends NamedControlArea {
  private Faction owner;
  
  private List<ControlPoint> controlPoints;
  private SectorManager sectors;

  public Sector(int id, SectorManager sectors) {
    super(id);
    this.sectors = sectors;
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
    point.setSector(this);
    controlPoints.add(point);
  }

  public List<ControlPoint> getControlPoints() {
    return controlPoints;
  }
  
  public void setControlPoints(List<ControlPoint> newControlPoints) {
    controlPoints = newControlPoints;
  }

  public Faction getOwner() {
    return owner;
  }
  
  public Faction setOwner(Faction newOwner) {
    Faction oldOwner = owner;
    owner = newOwner;
    return oldOwner;
  }

  public SectorManager getSectors() {
    return sectors;
  }
  
  @Override
  public War getWar() {
    return sectors.getWar();
  }

  

}
