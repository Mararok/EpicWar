/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;

public class FactionInfo implements Cloneable {
	
	public FactionInfo() {}
	public int id;
	public String name;
	public ChatColor color;
	public String desc;
	
	public int spawnX;
	public int spawnY;
	public int spawnZ;

	public int members;
	public int maxMembers;
	
	public int controlledSectors;
	public int capitalSectorID;
	
	public static FactionInfo fromDBResults(ResultSet results) throws SQLException {
		FactionInfo info = new FactionInfo();
		
		info.id = results.getInt(1);
		info.name = results.getString(2);
		info.desc = results.getString(3);
		info.color = ChatColor.getByChar(results.getString(4));
		info.spawnX = results.getInt(5);
		info.spawnY = results.getInt(6);
		info.spawnZ = results.getInt(7);
		info.members = results.getInt(8);
		info.maxMembers = results.getInt(9);
		info.controlledSectors = results.getInt(10);
		info.capitalSectorID = results.getInt(11);
		
		return info;
	}
	
	public FactionInfo clone() {
		try {
			return (FactionInfo) super.clone();
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
		sb.append(desc);
		
		sb.append(" Sx: ");
		sb.append(spawnX);
		sb.append(" Sy: ");
		sb.append(spawnY);
		sb.append(" Sz: ");
		sb.append(spawnZ);
		
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
