/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.controlpoint.ControlPoint;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.sector.Sector;
import com.gmail.mararok.epicwar.utility.DataObject;
import com.gmail.mararok.epicwar.utility.database.DB;
import com.gmail.mararok.epicwar.war.War;

public class WarPlayer implements DataObject<PlayerInfo> {
	private static int SQLID_AddPlayerKill = -1;
	private static int SQLID_AddPlayerDeath = -1;
	private static int SQLID_RegisterPlayerAtWar = -1;
	private static int SQLID_LoadPlayerWarData = -1;
	
	public static void precompileSQL() throws SQLException {
		int[] ids = DB.get().prepareCachedQueriesFromScript("PlayerQueries");
		SQLID_AddPlayerKill = ids[0];
		SQLID_AddPlayerDeath = ids[1];
		SQLID_RegisterPlayerAtWar = ids[2];
		SQLID_LoadPlayerWarData = ids[3];
	}
	
	private PlayerInfo Info;
	
	private int LastBlockX;
	private int LastBlockY;
	private int LastBlockZ;
	
	private Sector LastSector;
	private ControlPoint LastPoint;
	
	private Player BukkitPlayer;
	private PlayersManager Players;
	private War CurrentWar;
	
	private Scoreboard GUI;
	private Objective Sidebar;
	public WarPlayer(Player player,PlayersManager players) {
		Info = new PlayerInfo();
		BukkitPlayer = player;
		Players = players;
	}
	
	private void initStatsGUI() {
		ScoreboardManager sbm = Bukkit.getScoreboardManager();
		GUI = sbm.getNewScoreboard();
		Sidebar = GUI.registerNewObjective("stats","dummy");
		Sidebar.setDisplayName("Stats");
		Sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:")).setScore(Info.points);
		Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:")).setScore(Info.kills);
		Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Deaths:")).setScore(Info.deaths);
		
		getBPlayer().setScoreboard(GUI);
		
	}
	
	public final void updateLocation(PlayerMoveEvent event) {
		if (hasFaction()) {
			Location currentLoc = event.getTo();
			int 
				x = currentLoc.getBlockX(),
				y = currentLoc.getBlockY(),
				z = currentLoc.getBlockZ();
			if (LastBlockX != x || LastBlockY != y || LastBlockZ != z ) {
				LastBlockX = x;
				LastBlockY = y;
				LastBlockZ = z;
				updateLastSector(event);
				updateLastControlPoint();
			}
		}
	}
	
	private final void updateLastSector(PlayerMoveEvent event) {
		if (checkIsNotInLastSector()) {
			Sector lastSector = getWar().getSectors().getFromLocation(event.getTo());
			if (lastSector == getWar().getSectors().getByID(0)) {
				if (LastSector != lastSector) {
					LastPoint = getWar().getControlPoints().getByID(0);
					LastSector = lastSector;
					sendMessage("You are on wild");
				}
			} else if (checkSectorIsEnemyCapital(lastSector)) {
				event.setCancelled(true);
				sendMessage("You can't go to enemy faction capital sector");
			} else if (LastSector != lastSector) {
				LastSector = lastSector;
				LastPoint = getWar().getControlPoints().getByID(0);
				sendMessage("Entering Sector: "+LastSector.getInfo().name);
			}
		} 
	}
	
	private final boolean checkIsNotInLastSector() {
		return !LastSector.isPointInner(LastBlockX,LastBlockZ);
	}
	
	private final boolean checkSectorIsEnemyCapital(Sector lastSector) {
		return getWar().getFactions().isCapital(lastSector) && 
			   lastSector.getInfo().ownerFactionID != getInfo().factionID;
	}
	
	private final void updateLastControlPoint() {
		if (!LastPoint.isWithin(this)) {
			LastPoint.removeOccupantPlayer(this);
			for (ControlPoint cp : LastSector.getControlPoints()) {
				if (cp.isWithin(this)) {
					cp.addOccupantPlayer(this);
					LastPoint = cp;
					return;
				}
			}
			LastPoint = getWar().getControlPoints().getByID(0);
		}
	}
	
	public void onQuite() {
		if (hasFaction()) {
			getFaction().onMemberServerQuite(this);
		}
		
		LastPoint.removeOccupantPlayer(this);
	}
	
	public void onKill(WarPlayer victim) {
		Info.points += getWar().getInfo().pointsKill;
		++Info.kills;
		Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:")).setScore(Info.points);
		Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:")).setScore(Info.kills);
		try {
			PreparedStatement st = DB.get().getCachedQuery(SQLID_AddPlayerKill);
			st.setInt(1,getWar().getInfo().pointsKill); 
			st.setInt(2,getID());
			st.executeUpdate();
			DB.get().commit();
	} catch (SQLException e) {
		getPlayers().getPlugin().logCriticalException(e);
		DB.get().rollback();
	}
	}
	
	public void onDeath(WarPlayer killer) {
		Info.points -= getWar().getInfo().pointsDeath;
		if (hasFaction()) {
			getBPlayer().teleport(getFaction().getSpawnLocation());
		} 
		
		++Info.deaths;
		Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:")).setScore(Info.points);
		Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Deaths:")).setScore(Info.deaths);
		try {
			PreparedStatement st = DB.get().getCachedQuery(SQLID_AddPlayerDeath);
			st.setInt(1,getWar().getInfo().pointsDeath); 
			st.setInt(2,getID());
			st.executeUpdate();
			DB.get().commit();
		} catch (SQLException e) {
			getPlayers().getPlugin().logCriticalException(e);
			DB.get().rollback();
		}
	}
	
	public void join2Faction(Faction faction) {
		getFaction().removeMember(this);
		faction.addMember(this);
		LastSector = getFaction().getCapitalSector();
		LastPoint.removeOccupantPlayer(this);
		LastPoint = getWar().getControlPoints().getByID(0);
	}
	
	public void leaveFromFaction() {
		if (!hasFaction()) {
			sendMessage("You must be in faction to leave");
		} else {
			LastPoint.removeOccupantPlayer(this);
			LastPoint = getWar().getControlPoints().getByID(0);
			LastSector = getWar().getSectors().getByID(0);
			
			getFaction().removeMember(this);
			sendMessage("You left from faction "+getFaction().getName());
			getWar().getFactions().getByID(0).addMember(this);
		}
	}
	
	public void sendMessage(String message) {
		getBPlayer().sendMessage(message);
	}
	
	public void sendFormatMessage(String formatMessage, Object... args) {
		sendMessage(String.format(formatMessage,args));
	}
	
	public void sendFormatMessage(Language langFormatMessage, Object... args) {
		sendMessage(String.format(langFormatMessage.toString(),args));
	}
	
	public void sendMessage(Language langMessage) {
		sendMessage(langMessage.toString());
	}
	
	public void sendMessage(String[] message) {
		getBPlayer().sendMessage(message);
	}
	
	public void teleport(Location location) {
		getBPlayer().teleport(location);
	}
	
	public void teleport2FactionCaptital() {
		getFaction().teleport2Capital(this);
	}
	
	public void joinToWar(War war) {
		if (isOnWar()) {
			leaveFromWar();
		}
		
		CurrentWar = war;
		loadInfo();
		getFaction().onMemberServerJoin(this);
		LastSector = getWar().getSectors().getByID(0);
		LastPoint = getWar().getControlPoints().getByID(0);
		LastPoint.addOccupantPlayer(this);
		Location location = BukkitPlayer.getLocation();
		updateLocation(new PlayerMoveEvent(BukkitPlayer,location,location)); 
		initStatsGUI();
	}
	
	public void leaveFromWar() {
		LastPoint.removeOccupantPlayer(this);
		LastPoint = null;
		LastSector = null;
		getFaction().onMemberServerQuite(this);
		CurrentWar = null;
	}
	
	private void loadInfo() {
		try {
			PreparedStatement st = DB.get().getCachedQuery(SQLID_LoadPlayerWarData);
			st.setInt(1,getWar().getID());
			st.setString(2,getName());
			ResultSet results = st.executeQuery();
			PlayerInfo info = getInfo();
			if (results.next()) {
				info.id = results.getInt(1);
				info.factionID = results.getInt(2);
				info.kills = results.getInt(3);
				info.deaths = results.getInt(4);
				info.points = results.getInt(5);
			} else {
				registerAtWar();
			}
		} catch (SQLException e) {
			getPlayers().getPlugin().logSevere(e.toString());
		}
	}
	
	private void registerAtWar() {
		try {
			PreparedStatement st = DB.get().getCachedQuery(SQLID_RegisterPlayerAtWar);
			st.setString(1,getName());
			st.setInt(2,getWar().getID());
			st.executeUpdate();
			DB.get().commit();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			getInfo().reset();
			getInfo().id = rs.getInt(1);
			getPlugin().logInfo(String.format("Registered player %s with id %d at %s world, war: %s",
				getName(),getID(),getWorld().getName(),getWar().getName()));
			sendMessage("Welcome, Your first time at "+getWar().getName()+" war");
		} catch (SQLException e) {
			getPlugin().logCriticalException(e);
			DB.get().rollback();
		}
	}
	
	public PlayersManager getPlayers() {
		return Players;
	}
	
	public Player getBPlayer() {
		return BukkitPlayer;
	}

	public Faction getFaction() {
		return getWar().getFactions().getByID(getInfo().factionID);
	}
	
	public boolean hasFaction() {
		return (getInfo().factionID != 0);
	}

	public final int getBlockX() {
		return LastBlockX;
	}
	
	public final int getBlockY() {
		return LastBlockY;
	}
	
	public final int getBlockZ() {
		return LastBlockZ;
	}
	
	public Location getLocation() {
		return getBPlayer().getLocation();
	}
	
	public War getWar() {
		return CurrentWar;
	}
	
	public World getWorld() {
		return getBPlayer().getWorld();
	}
	
	public Sector getSector() {
		return LastSector;
	}
	
	public ControlPoint getControlPoint() {
		return LastPoint;
	}
	
	public EpicWarPlugin getPlugin() {
		return getPlayers().getPlugin();
	}
	
	@Override
	public PlayerInfo getInfo() {
		return Info;
	}

	@Override
	public String getName() {
		return getBPlayer().getName();
	}
	
	@Override
	public int getID() {
		return getInfo().id;
	}
	
	public String toString() {
		return Info.toString();
	}

	public boolean isOnWar() {
		return getWar() != null;
	}
}
