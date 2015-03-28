/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gmail.mararok.epicwar.control.SectorInfo;

public class WoolsMapInfo implements Cloneable {
  public int id;
  public String name;
  public WoolsMapOrientation Orientation;
  public int sectorsPerLine;
  public int x;
  public int y;
  public int z;

  public static WoolsMapInfo fromDBResults(ResultSet results)
      throws SQLException {
    WoolsMapInfo info = new WoolsMapInfo();

    info.id = results.getInt(1);
    info.name = results.getString(2);
    info.Orientation = WoolsMapOrientation.values()[results.getInt(3)];
    info.sectorsPerLine = results.getInt(4);
    info.x = results.getInt(5);
    info.y = results.getInt(6);
    info.z = results.getInt(7);

    return info;
  }

  @Override
  public SectorInfo clone() {
    try {
      return (SectorInfo) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.toString());
    }
  }
}
