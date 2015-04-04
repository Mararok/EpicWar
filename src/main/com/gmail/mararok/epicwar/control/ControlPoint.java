/**
 * EpicWar
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.bukkit.util.UMath;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class ControlPoint extends NamedControlArea {
  private static final int DEFAULT_RADIUS = 4;
  private static final int DEFAULT_MAX_POWER = 100;
  
  private Location location;
  private int radius;
  private ControlAreaPower power;
  private Faction owner;
  private Occupiers occupiers;
  
  private List<Subsector> subsectors;
  private Sector sector;

  public ControlPoint(int id, Location location) {
    super(id);
    this.location = location;
    radius = DEFAULT_RADIUS;
    power = new ControlAreaPower(DEFAULT_MAX_POWER);
    occupiers = new Occupiers(this);
    
    subsectors = new LinkedList<Subsector>();
  }

  public boolean isWithin(WarPlayer warPlayer) {
    return (int) UMath.getDistance3D(warPlayer.getBlockX(),
        warPlayer.getBlockY(), warPlayer.getBlockZ(), info.centerX,
        info.centerY, info.centerZ) <= info.radius;
  }

  public Location getLocation() {
    return location.clone();
  }

  public int getRadius() {
    return radius;
  }
  
  public void setRadius(int newRadius) {
    radius = Math.max(DEFAULT_RADIUS,newRadius);
  }
  
  public ControlAreaPower getPower() {
    return power;
  }
  
  public Faction getOwner() {
    return owner;
  }
  
  public Occupiers getOccupiers() {
    return occupiers;
  }
  
  public Faction setOwner(Faction newOwner) {
    Faction oldOwner = owner;
    owner = newOwner;
    return oldOwner;
  }
  
  public void addSubsector(Subsector subsector) {
    if (subsector.getControlPoint() != null) {
      subsector.getControlPoint().removeSubsector(subsector);
    }
    
    subsectors.add(subsector);
    subsector.setControlPoint(this);
  }
  
  public void removeSubsector(Subsector subsector) {
    if (subsectors.remove(subsector)) {
      subsector.setControlPoint(null);
    }
  }

  public Sector getSector() {
    return sector;
  }
  
  public void setSector(Sector newSector) {
    sector = newSector;
  }

  @Override
  public War getWar() {
    return sector.getWar();
  }

  

}
