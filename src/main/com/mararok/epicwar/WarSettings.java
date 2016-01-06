/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar;

import java.util.Date;

public class WarSettings implements Cloneable {
  public String name;
  public String description;
  public Date startDateTime;

  /** If true war map is editable(you can add more sectors, more control points and changing all settings) */
  public boolean editMode;

  public PointsSettings points;
  public FactionSettions faction;
  public ControlPointSettings controlPoint;
  public WorldSettings world;

  public static class PointsSettings {
    public int kill;
    public int death;

    public int captureControlPoint;
    public int captureSector;
  }

  public static class ControlPointSettings {
    /** control point occupiers power checks interval in seconds */
    public int updateInterval;

    public int powerDefender;
    public int powerAttacker;
  }

  public static class FactionSettions {
    public boolean onlineBalance;
    public int onlineBalancePercentPerFaction;
  }

  public static class WorldSettings {
    public String name;
    public int startChunkX;
    public int startChunkZ;
    public int sizeInChunks;
  }

  @Override
  public WarSettings clone() {
    try {
      return (WarSettings) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.toString());
    }
  }
}
