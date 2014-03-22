/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerInfo implements Cloneable {
	public int id;
	public int factionID;
	public int points;
	public int kills;
	public int deaths;
	
	public static PlayerInfo fromDBResults(ResultSet results) throws SQLException {
		PlayerInfo info = new PlayerInfo();
		
		info.id = results.getInt(1);
		info.factionID = results.getInt(2);
		info.points = results.getInt(3);
		info.kills = results.getInt(4);
		info.deaths = results.getInt(5);
		
		return info;
	}
	
	public void reset() {
		id = factionID = kills = deaths = points = 0;
	}
	
	@Override
	public PlayerInfo clone() {
		try {
			return (PlayerInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
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
