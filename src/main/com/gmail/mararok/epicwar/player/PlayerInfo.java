/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerInfo {
  public int id = 0;
  public int factionID = 0;
  public int points = 0;
  public int kills = 0;
  public int deaths = 0;

  public static PlayerInfo fromDBResult(ResultSet result) throws SQLException {
    PlayerInfo info = new PlayerInfo();

    info.id = result.getInt(1);
    info.factionID = result.getInt(2);
    info.points = result.getInt(3);
    info.kills = result.getInt(4);
    info.deaths = result.getInt(5);

    return info;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("P[");
    sb.append(id);
    sb.append("] ");
    sb.append("FID: ");
    sb.append(factionID);
    sb.append(" Points: ");
    sb.append(points);
    sb.append(" Kills: ");
    sb.append(kills);
    sb.append(" deaths: ");
    sb.append(deaths);

    return sb.toString();
  }
}
