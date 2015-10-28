/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import java.util.Date;

public class WarSettings implements Cloneable {
  public String name;
  public String description;
  public Date startDateTime;

  /** If true war map is editable(you can add more sectors, more control points and changing all settings) */
  public boolean editMode;

  public PointsWarSettings points;
  public FactionWarSettings faction;
  public ControlPointWarSettings controlPoint;

  public WorldWarSettings world;

  @Override
  public WarSettings clone() {
    try {
      return (WarSettings) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.toString());
    }
  }
}
