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
  public String worldName;

  public int pointsKill;
  public int pointsTeamkill;
  public int pointsDeath;
  
  public int pointsCapturePoint;
  public int pointsCaptureSector;

  public int powerDefender;
  public int powerAttacker;
  
  public int truceStartHour;
  public int truceHours; 
  
  public boolean editMode;
  
  public int startChunkX;
  public int startChunkZ;
  public int sizeInChunks;
  
  public WarSettings clone() {
    try {
      return (WarSettings) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.toString());
    }
  }
}
