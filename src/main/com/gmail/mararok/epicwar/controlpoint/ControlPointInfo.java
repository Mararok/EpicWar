/**
 * EpicWar
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.controlpoint;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ControlPointInfo implements Cloneable {
	public int id;
	public String name;
	
	public int sectorID;
	public int ownerFactionID;
	
	public int centerX;
	public int centerY;
	public int centerZ;
	public int radius;
	
	public int power;
	public int maxPower;
	
	public static ControlPointInfo fromDBResults(ResultSet results) throws SQLException {
		ControlPointInfo info = new ControlPointInfo();
		
		info.id = results.getInt(1);
		info.name = results.getString(2);
		info.sectorID = results.getInt(3);
		info.ownerFactionID = results.getInt(4);
		
		info.centerX = results.getInt(5);
		info.centerY = results.getInt(6);
		info.centerZ = results.getInt(7);
		info.radius = results.getInt(8);
		
		info.power = results.getInt(9);
		info.maxPower = results.getInt(10);
		
		return info;
	}
	
	public ControlPointInfo clone() {
		try {
			return (ControlPointInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CP[");
		sb.append(id);
		sb.append("] ");
		sb.append(name);
		
		sb.append(" SID: ");
		sb.append(sectorID);
		sb.append(" OFID: ");
		sb.append(ownerFactionID);
		
		sb.append(" CX: ");
		sb.append(centerX);
		
		sb.append(" CY: ");
		sb.append(centerY);
		
		sb.append(" CZ: ");
		sb.append(centerZ);
		
		sb.append("R: ");
		sb.append(radius);
		
		sb.append(" P: ");
		sb.append(power);
		sb.append(" MP: ");
		sb.append(maxPower);
		
		return sb.toString();
	}
}
