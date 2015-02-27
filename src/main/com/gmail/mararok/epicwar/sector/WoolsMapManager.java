/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gmail.mararok.epicwar.utility.DBConnection;
import com.gmail.mararok.epicwar.utility.DataSetManager;
import com.gmail.mararok.epicwar.war.War;

public class WoolsMapManager extends DataSetManager<WoolsMap>{

	public WoolsMapManager(War war) {
		super(war);
	}

	public void load() throws SQLException {
		PreparedStatement mapsInfoStatement = DBConnection.get().prepareQuery("SELECT id,name,orientation,perLine,x,y,z FROM ew_WoolsMaps WHERE warID = ?");
		mapsInfoStatement.setInt(1,getWar().getID());
		ResultSet results = mapsInfoStatement.executeQuery();
		WoolsMap map;
		while (results.next()) {
			map = new WoolsMap(WoolsMapInfo.fromDBResults(results),this);
			put(map);
		}
		
		mapsInfoStatement.close();
		getPlugin().logInfo("Loaded "+size()+" wool maps");
	}
	
	public void init() {
		List<WoolsMap> maps = getList();
		for(WoolsMap map : maps) {
			map.init();
		}
	}
	
	public void create(WoolsMapInfo info) {
		try {
			PreparedStatement statement = DBConnection.get().prepareQuery("INSERT INTO ew_WoolsMaps (name,warID,orientation,x,y,z) VALUES(?,?,?,?,?,?)");
			statement.setString(1,info.name);
			statement.setInt(2,getWar().getID());
			statement.setInt(3,0);
			statement.setInt(4,info.x);
			statement.setInt(5,info.y);
			statement.setInt(6,info.z);
			
			statement.executeUpdate();
			DBConnection.get().commit();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			info.id = rs.getInt(1);
			
			WoolsMap map = new WoolsMap(info,this);
			map.init();
			put(map);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onSectorCapture(Sector sector) {
		List<WoolsMap> maps = getList();
		for (WoolsMap map : maps) {
			map.onSectorCapture(sector);
		}
	}
	@Override
	public void dump2File() throws IOException {
		dump2File("WoolSectorsMaps");
	}
	
}
