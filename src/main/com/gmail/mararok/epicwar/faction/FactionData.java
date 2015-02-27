/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;

import com.gmail.mararok.epicwar.utility.Position;

public class FactionData implements Cloneable {
	public int id;
	public String name;
	public String description;
	public ChatColor color;
	
	public Position spawnPosition;

	public int members;
	public int maxMembers;
	
	public int controlledSectors;
	public int capitalSectorID;
	
	public static FactionData fromDBResults(ResultSet results) throws SQLException {
		FactionData info = new FactionData();
		
		info.id = results.getInt(1);
		info.name = results.getString(2);
		info.description = results.getString(3);
		info.color = ChatColor.getByChar(results.getString(4));
		
		info.spawnPosition = new Position();
		
		info.spawnPosition.x = results.getInt(5);
		info.spawnPosition.y = results.getInt(6);
		info.spawnPosition.z = results.getInt(7);
		
		info.members = results.getInt(8);
		info.maxMembers = results.getInt(9);
		info.controlledSectors = results.getInt(10);
		info.capitalSectorID = results.getInt(11);
		
		return info;
	}
	
	public FactionData clone() {
		try {
			return (FactionData) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("F[");
		sb.append(id);
		sb.append("] ");
		sb.append(color+name);
		sb.append(" Desc: ");
		sb.append(description);
		
		sb.append("SP: ");
		sb.append(spawnPosition);
		
		sb.append(" Members: ");
		sb.append(members);
		sb.append(" MaxMembers: ");
		sb.append(maxMembers);
		
		sb.append(" ControlledSectors: ");
		sb.append(controlledSectors);
		sb.append(" CapitalSectorID: ");
		sb.append(capitalSectorID);
		
		return sb.toString();
	}
}
