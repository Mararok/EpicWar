/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;

import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.controlpoint.ControlPoint;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.sector.Sector;
import com.gmail.mararok.epicwar.utility.ColorConverter;
import com.gmail.mararok.epicwar.utility.DataObject;
import com.gmail.mararok.epicwar.utility.database.DB;

public class Faction implements DataObject<FactionInfo> {
	private static int SQLID_AddMember = -1;
	private static int SQLID_RemoveMember = -1;
	private static int SQLID_SetPlayerFaction = -1;
	private static int SQLID_SetSpawn = -1;
	private static int SQLID_UpdateControlledSectorsAmount = -1;
	
	public static void precompileSQL() throws SQLException {
		int[] ids = DB.get().prepareCachedQueriesFromScript("FactionQueries");
		SQLID_AddMember = ids[0];
		SQLID_RemoveMember = ids[1];
		SQLID_SetPlayerFaction = ids[2];
		SQLID_SetSpawn = ids[3];
		SQLID_UpdateControlledSectorsAmount = ids[4];
	}
	
	private FactionInfo Info;
	private FactionsManager Factions;
	private List<WarPlayer> OnlineMembers;
	private Location SpawnLocation;
	
	public Faction(FactionInfo info, FactionsManager factions) {
		Info = info;
		Factions = factions;
		OnlineMembers = new LinkedList<WarPlayer>();
	}
	
	public void init() {
		SpawnLocation = new Location(getFactions().getWorld(),getInfo().spawnX,getInfo().spawnY,getInfo().spawnZ);
	}
	
	public void addMember(WarPlayer player) {
		DB db = DB.get();
		try {
			PreparedStatement st = db.getCachedQuery(SQLID_SetPlayerFaction);
			st.setInt(1, Info.id);
			st.setInt(2, player.getID());
			st.executeUpdate();
					
			st = db.getCachedQuery(SQLID_AddMember);
			st.setInt(1,Info.id);
			st.executeUpdate();
			
			db.commit();
			player.getInfo().factionID = getID();
			++Info.members;
			onMemberServerJoin(player);
			teleport2Capital(player);
		} catch (SQLException e) {
			db.rollback();
			getFactions().getPlugin().logCriticalException(e);
		}	
	}
	
	public boolean isFull() {
		return Info.members == Info.maxMembers;
	}
	
	public void removeMember(WarPlayer player) {
		DB db = DB.get();
		try {
			PreparedStatement st = db.getCachedQuery(SQLID_SetPlayerFaction);
			st.setInt(1,0);
			st.setInt(2, player.getID());
			st.executeUpdate();
			
			st = db.getCachedQuery(SQLID_RemoveMember);
			st.setInt(1,Info.id);
			st.executeUpdate();
		
			db.commit();
			--Info.members;
			player.getInfo().factionID = 0;
			player.getBPlayer().setPlayerListName(player.getName());
			onMemberServerQuite(player);
		} catch (SQLException e) {
			Factions.getPlugin().logCriticalException(e);
			db.rollback();
		}
	}
	
	public void onMemberServerJoin(WarPlayer player) {
		player.getBPlayer().setPlayerListName(getInfo().color+player.getName());
		OnlineMembers.add(player);
	}
	
	public void onMemberServerQuite(WarPlayer player) {
		OnlineMembers.remove(player);
	}

	public Location getSpawnLocation() {
		return SpawnLocation;
	}

	public void setSpawnLocation(Location spawnLocation) {
		SpawnLocation = spawnLocation;
		Info.spawnX = spawnLocation.getBlockX();
		Info.spawnY = spawnLocation.getBlockY();
		Info.spawnZ = spawnLocation.getBlockZ();
		try {
			PreparedStatement st = DB.get().getCachedQuery(SQLID_SetSpawn);
			st.setInt(1,Info.spawnX);
			st.setInt(2,Info.spawnY);
			st.setInt(3,Info.spawnZ);
			st.setInt(4,Info.id);
			st.executeUpdate();
			DB.get().commit();
		} catch (SQLException e) {

			DB.get().rollback();
			e.printStackTrace();
		}
	}

	public Sector getCapitalSector() {
		return getFactions().getSectors().getByID(getInfo().capitalSectorID);
	}

	public void teleport2Capital(WarPlayer player) {
		player.getBPlayer().teleport(getSpawnLocation());
	}
	
	private void updateControlledSectors() {
		try {
			PreparedStatement statement = DB.get().getCachedQuery(SQLID_UpdateControlledSectorsAmount);
			statement.setInt(1,getInfo().controlledSectors);
			statement.setInt(2,getID());
			statement.executeUpdate();
			DB.get().commit();
		} catch (SQLException e) {
			DB.get().rollback();
			getFactions().getPlugin().logCriticalException(e);
		}
	}
	
	public void onCapturePoint(ControlPoint point) {
		sendFormatMessage2OnlineMembers(Language.FACTION_CAPTURED_POINT,point.getName(),point.getSector().getName());
	}
	
	public void onLostPoint(ControlPoint point) {
		sendFormatMessage2OnlineMembers(Language.FACTION_LOST_POINT,point.getName(),point.getSector().getName());
	}
	
	public void onCaptureSector(Sector sector) {
		addControlledSectors();
		sendFormatMessage2OnlineMembers(Language.FACTION_CAPTURED_SECTOR,sector.getName());
	}
	
	private void addControlledSectors() {
		++Info.controlledSectors;
		updateControlledSectors();
	}
	
	public void onLostSector(Sector sector) {
		subControlledSectors();
		sendFormatMessage2OnlineMembers(Language.FACTION_LOST_SECTOR,sector.getName());
	}
	
	private void subControlledSectors() {
		--Info.controlledSectors;
		updateControlledSectors();
	}
	
	public void sendFormatMessage2OnlineMembers(Language langMessage, Object... args) {
		String message = String.format(langMessage.toString(),args);
		for (WarPlayer member : OnlineMembers) {
			member.sendMessage(message);
		}
	}
	
	public String getDisplayName() {
		return getChatColor()+getName();
	}
	
	public ChatColor getChatColor() {
		return getInfo().color;
	}
	
	public DyeColor getDyeColor() {
		return ColorConverter.getDyeColor(getChatColor());
	}
	
	public FactionsManager getFactions() {
		return Factions;
	}
	
	@Override
	public FactionInfo getInfo() {
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
}
