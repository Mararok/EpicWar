/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

import java.awt.Rectangle;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.gmail.mararok.epicwar.controlpoint.ControlPoint;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.utility.DBConnection;
import com.gmail.mararok.epicwar.utility.DataObject;
import com.gmail.mararok.epicwar.utility.NameConverter;
import com.gmail.mararok.epicwar.utility.RegionsUtlil;
import com.gmail.mararok.epicwar.utility.UMath;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Sector implements DataObject<SectorInfo> {
	public static final String REGION_PREFIX = "s";
	
	private SectorInfo Info;
	private Rectangle Region2D;
	private SectorsManager Sectors;
	private List<ControlPoint> ControlPoints;
	
	public Sector(SectorInfo info, SectorsManager sectors) {
		Info = info;
		Sectors = sectors;
	}
	
	public void init() {
		ControlPoints = Sectors.getWar().getControlPoints().getControlPointsFor(this);
		createRegionIfNotExists();
		int size = getInfo().size;
		int halfSize = size/2;
		Region2D = new Rectangle(getInfo().centerX+halfSize,getInfo().centerZ+halfSize,size,size);
	}
	
	private void createRegionIfNotExists() {
		if (getRegion() != null) {
			return;
		}
		
		int 
			halfSize = getInfo().size/2,
			top = getInfo().centerZ-halfSize, left = getInfo().centerX-halfSize, 
			bottom = getInfo().centerZ+halfSize, right = getInfo().centerX+halfSize;
	
		RegionsUtlil.createRegion(getSectors().getRegions(),
			NameConverter.nameToRegionName(Sector.REGION_PREFIX,getName()), 
			new BlockVector(left,getSectors().getWorld().getMaxHeight(),top),
			new BlockVector(right,0,bottom)
	);
}
	public void tryCapture(int newOwner) {
		if (canCapture(newOwner)) {
			getOwner().onLostSector(this);
			getSectors().getFactions().getByID(newOwner).onCaptureSector(this);
			setOwner(newOwner);
			getSectors().getWar().getWoolMaps().onSectorCapture(this);
		}
	}
	
	public boolean canCapture(int newOwner) {
		for(ControlPoint cp : ControlPoints) {
			if (cp.getInfo().ownerFactionID != newOwner)
				return false;
		}
		return true;
	}

	public void setOwner(int newOwnerID) {
		getInfo().ownerFactionID = newOwnerID;
		try {
			PreparedStatement st = DBConnection.get().prepareQuery("UPDATE ew_Sectors SET ownerID = ? WHERE id = ?");
			st.setInt(1,newOwnerID);
			st.setInt(2,getID());
			st.executeUpdate();
			DBConnection.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isPointInner(int x, int z) {
		if (UMath.isPointWithinCircle2D(x,z,Info.centerX,Info.centerZ,Info.size/2)) {
			return true;
		}
		return false;
		//return Region2D.contains(x, z);
	}
	
	public ProtectedRegion getRegion() {
		return getSectors().getRegions().getRegion(NameConverter.nameToRegionName(REGION_PREFIX,getName()));
	}
	
	public void addControlPoint(ControlPoint point) {
		ControlPoints.add(point);
	}
	
	public List<ControlPoint> getControlPoints() {
		return ControlPoints;
	}
	
	public Faction getOwner() {
		return getSectors().getFactions().getByID(getInfo().ownerFactionID);
	}
	
	public SectorsManager getSectors() {
		return Sectors;
	}
	@Override
	public SectorInfo getInfo() {
		return Info;
	}
	
	@Override
	public int getID() {
		return Info.id;
	}

	@Override
	public String getName() {
		return Info.name;
	}
	
	@Override
	public String toString() {
		return Info.toString();
	}
}
