/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

public class PlayerStats {
  public int kills;
  public int deaths;
  public int teamkills;
  public int points;
  public int capturedControlPoints;
  public int capturedSectors;

  public PlayerStats() {
  }

  public PlayerStats(PlayerStats other) {
    kills = other.kills;
    deaths = other.deaths;
    teamkills = other.teamkills;
    points = other.points;
    capturedControlPoints = other.capturedControlPoints;
    capturedSectors = other.capturedSectors;
  }
}
