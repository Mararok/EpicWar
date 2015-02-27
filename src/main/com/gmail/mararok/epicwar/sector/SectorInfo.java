/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectorInfo implements Cloneable {
	public int id;
	public String name;
	public String desc;
	public int ownerFactionID;
	public int centerX;
	public int centerZ;
	public int size;
	
	public static SectorInfo fromDBResults(ResultSet results) throws SQLException {
		SectorInfo info = new SectorInfo();
		info.id = results.getInt(1);
		info.name = results.getString(2);
		info.desc = results.getString(3);
		info.ownerFactionID = results.getInt(4);
		info.centerX = results.getInt(5);
		info.centerZ = results.getInt(6);
		info.size = results.getInt(7);
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("S[");
		sb.append(id);
		sb.append("] ");
		sb.append(name);
		sb.append(' ');
		sb.append(desc);
		sb.append(" OF: ");
		sb.append(ownerFactionID);
		
		sb.append(" Cx: ");
		sb.append(centerX);
		sb.append(" Cz: ");
		sb.append(centerZ);
		sb.append(" Size: ");
		sb.append(size);
	
		return sb.toString();
	}
}
