/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;

import com.gmail.mararok.epicwar.sector.Sector;
import com.gmail.mararok.epicwar.utility.DataSetManager;
import com.gmail.mararok.epicwar.utility.Disposable;
import com.gmail.mararok.epicwar.utility.database.DB;
import com.gmail.mararok.epicwar.war.War;

public class FactionsManager extends DataSetManager<Faction> implements Disposable {
	public static final int NEUTRAL_ID = 0;
	private HashMap<ChatColor,Faction> FactionsColors;
	
	public FactionsManager(War war) throws Exception {
		super(war);
		FactionsColors = new HashMap<ChatColor,Faction>();
	}
	
	@Override
	public void load() throws Exception {
		createNeutralFaction();
		PreparedStatement factionsInfoStatement = DB.get().prepareQuery(
			"SELECT id,name,desc,color,spawnX,spawnY,spawnZ,members,maxMembers,controlledSectors,capitalSectorID FROM ew_Factions WHERE warID = ?");
		factionsInfoStatement.setInt(1,getWar().getID());
		ResultSet results = factionsInfoStatement.executeQuery();
		
		Faction faction;
		while (results.next()) {
			faction = new Faction(FactionInfo.fromDBResults(results),this);
			FactionsColors.put(faction.getInfo().color,faction);
			put(faction);
		}
		
		factionsInfoStatement.close();
		getPlugin().logInfo("Loaded "+size()+" factions");
	}
	
	public void init() {
		
		List<Faction> factions = getList();
		for(Faction faction : factions) {
			faction.init();
		}
	}
	
	private void createNeutralFaction() {
		FactionInfo info = new FactionInfo();
		info.color = ChatColor.WHITE;
		info.name = "Neutral Faction";
		info.desc = "this is neutral faction";
		info.controlledSectors = 1;
		info.maxMembers = 1000000;
		put (new NeutralFaction(info,this));
	}
	
	public void create(FactionInfo info) {
		try {
			PreparedStatement st = DB.get().prepareQuery(
				"INSERT INTO ew_Factions (name,warID,color,spawnX,spawnY,spawnZ,capitalSectorID) VALUES(?,?,?,?,?,?,?)"
			);
			
			info.name = info.name.replace('_',' ');
			
			st.setString(1,info.name);
			st.setInt(2,getWar().getID());
			st.setInt(3,info.color.getChar());
			st.setInt(4,info.spawnX);
			st.setInt(5,info.spawnY);
			st.setInt(6,info.spawnZ);
			st.setInt(7,info.capitalSectorID);
			st.executeUpdate();
			DB.get().commit();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			
			info.id = rs.getInt(1);
			info.desc = "Default desc";
			info.maxMembers = 10000;
			info.controlledSectors = 1;
			
			getSectors().getByID(info.capitalSectorID).setOwner(info.id);
			rs.close();
			st.close();
			
			Faction faction = new Faction(info,this);
			faction.init();
			FactionsColors.put(info.color,faction);
			put(faction);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isCapital(Sector sector) {
		List<Faction> factions = getList();
		for (Faction faction : factions) {
			if (faction.getInfo().capitalSectorID == sector.getID()) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isExists(String factionName) {
		return getByName(factionName) != null;
	}
	
	public Faction getNeutral() {
		return getByID(0);
	}
	
	public Faction getByColor(ChatColor color) {
		return FactionsColors.get(color);
	}
	
	public boolean isColorUsed(ChatColor color) {
		return FactionsColors.containsKey(color);
	}
	
	public void dump2File() throws IOException {
		dump2File("Factions");
	}
	
}
