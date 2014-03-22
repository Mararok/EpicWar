/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */

package com.gmail.mararok.epicwar;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mararok.epicwar.command.Commands;
import com.gmail.mararok.epicwar.controlpoint.ControlPointsManager;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.PlayersManager;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.sector.SectorsManager;
import com.gmail.mararok.epicwar.utility.database.DB;
import com.gmail.mararok.epicwar.war.WarsManager;


public final class EpicWarPlugin extends JavaPlugin {
	public static final boolean CAN_UPDATE_SQL_SCRIPTS = true;
	public static final String SQL_SCRIPTS_PATH = "sqlscripts/";
	public Configuration Config;
	public Logger log;
	
	private boolean Invalid = false;
	
	@SuppressWarnings("unused")
	private Commands AllCommands;
	
	private PlayersManager Players;
	@SuppressWarnings("unused")
	private EnviromentListener EListener;
	private WarsManager Wars;
	
	public EpicWarPlugin() {}
	
	public void onEnable() {
		log = getLogger();
		try {
			initConfig();
			initDB();
			initData(); 
			initListeners();
			initCommands();
		} catch(Exception e) {
			logCriticalException(e);
		}
	}
	
	private void initConfig() throws ConfigException {
		Config = new Configuration(this);
	}
	
	private void initListeners() {
		EListener = new EnviromentListener(this);
	}
	
	private void initDB() throws SQLException {
		DB.init(this,Config.db,false);
		initSQLs();
	}
	
	private void initSQLs() throws SQLException {
		updateSQLScript("ew_Players");
		updateSQLScript("ew_Factions");
		updateSQLScript("ew_Sectors");
		updateSQLScript("ew_ControlPoints");
		updateSQLScript("ew_WoolsMaps");
		updateSQLScript("PlayerQueries");
		updateSQLScript("FactionQueries");
		updateSQLScript("SectorsQueries");
		updateSQLScript("ControlPointsQueries");
		initTables();
		precompileSQL();
	}
	
	public void updateSQLScript(String scriptName) {
		this.saveResource(SQL_SCRIPTS_PATH+scriptName+".sql",CAN_UPDATE_SQL_SCRIPTS);
	}
	
	private void initTables() throws SQLException {
		DB.get().execScript("ew_Players");
		DB.get().execScript("ew_Factions");
		DB.get().execScript("ew_Sectors");
		DB.get().execScript("ew_ControlPoints");
		DB.get().execScript("ew_WoolsMaps");
	}
	
	private void precompileSQL() throws SQLException {
		WarPlayer.precompileSQL();
		Faction.precompileSQL();
		SectorsManager.precompileSQL();
		ControlPointsManager.precompileSQL();
	}
	
	private void initData() throws Exception {
		Wars = new WarsManager(this);
		Wars.load();
		Players = new PlayersManager(Wars);
		checkReload();
	}
	
	private void checkReload() {
		Player[] playersOnline = getServer().getOnlinePlayers();
		if (playersOnline.length > 0) {
			getPlayers().onServerReload(playersOnline);
		}
		
	}
	private void initCommands() {
		AllCommands = new Commands(this);
	}
	
	public void onDisable() {
		if (isInvalid()) {
			return;
		}
		Wars.dispose();
		Players.dispose();
		DB.get().dispose();
	}
	
	public PlayersManager getPlayers() {
		return Players;
	}
	
	public final WarsManager getWars() {
		return Wars;
	}
	
	public void setInvalid() {
		Invalid = true;
	}
	
	public boolean isValid() {
		return !Invalid;
	}
	public boolean isInvalid() {
		return Invalid;
	}
	
	public String getDataDumpPath() {
		return getDataFolder().getPath()+"/datadump";
	}
	
	public void logSevere(String msg) {
		log.severe(msg);
	}
	
	public void logWarning(String msg) {
		log.warning(msg);
	}
	public void logInfo(String msg) {
		log.info(msg);
	}
	
	public void logCriticalException(Exception e) {
		e.printStackTrace();
		setInvalid();
	}
	
	public void logException(Exception e) {
		e.printStackTrace();
	}
}