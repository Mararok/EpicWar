/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

public class PlayerStats implements Cloneable {
  public int kills;
  public int deaths;
  public int teamkills;
  public int points;
  public int capturedControlPoints;
  public int capturedSectors;
  
  public PlayerStats clone() {
    try {
      return (PlayerStats) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.toString());
    }
  }
}
