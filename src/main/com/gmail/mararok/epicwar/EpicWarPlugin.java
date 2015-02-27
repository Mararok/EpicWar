/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.gmail.mararok.epicwar;

import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mararok.epicwar.command.CommandManager;
import com.gmail.mararok.epicwar.command.Commands;
import com.gmail.mararok.epicwar.controlpoint.ControlPointsManager;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.EnviromentListener;
import com.gmail.mararok.epicwar.player.PlayerManager;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.sector.SectorsManager;
import com.gmail.mararok.epicwar.utility.DBConnection;
import com.gmail.mararok.epicwar.utility.DBConnectionConfig;
import com.gmail.mararok.epicwar.utility.DBConnectionFactory;
import com.gmail.mararok.epicwar.utility.DisposableManager;
import com.gmail.mararok.epicwar.utility.PluginConfiguration;
import com.gmail.mararok.epicwar.utility.SQLScriptLoader;
import com.gmail.mararok.epicwar.war.WarsManager;


public final class EpicWarPlugin extends JavaPlugin {
	public static final boolean CAN_UPDATE_SQL_SCRIPTS = true;
	public static final String SQL_SCRIPTS_PATH = "sqlscripts/";
	
	private PluginConfiguration config;
	private DisposableManager disposableManager;
	private DBConnection dbConnection;
	private boolean valid = false;
	private Logger log;
	
	private CommandManager commandManager;
	private WarsManager warManager;
	
	public void onEnable() {
		log = getLogger();
		try {
			config = new PluginConfiguration(this);
			initDB();
			initWars(); 
			initCommands();
			
		} catch(Exception e) {
			logCriticalException(e);
		}
	}
	
	private void initDB() throws SQLException {
		DBConnectionFactory factory = new DBConnectionFactory(this);
		DBConnectionConfig db = new DBConnectionConfig();
		db.engine = config.getString("database.engine");
		db.host = config.getString("database.host");
		db.dbName = config.getString("database.dbName");
		db.user = config.getString("database.user");
		db.password = config.getString("database.password");
		
		dbConnection = factory.newConnection(db);
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
		dbConnection.execScript("ew_Players");
		DBConnection.get().execScript("ew_Factions");
		DBConnection.get().execScript("ew_Sectors");
		DBConnection.get().execScript("ew_ControlPoints");
		DBConnection.get().execScript("ew_WoolsMaps");
	}
	
	private void precompileSQL() throws SQLException {
		WarPlayer.precompileSQL();
		Faction.precompileSQL();
		SectorsManager.precompileSQL();
		ControlPointsManager.precompileSQL();
	}
	
	private void initWars() throws Exception {
		warManager = new WarsManager(this);
		warManager.load();
		checkReload();
	}
	
	private void checkReload() {
		Collection<? extends Player> playersOnline = getServer().getOnlinePlayers();
		if (playersOnline.size > 0) {
			getWarManager().onServerReload(playersOnline);
		}
		
	}
	
	public void onDisable() {
		if (isValid()) {
			disposableManager.dispose();
		}
	}
	
	public WarsManager getWarManager() {
		return warManager;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public boolean isInvalid() {
		return !valid;
	}
	
	public String getDataDumpPath() {
		return getDataFolder().getPath()+"/datadump";
	}
	
	public DBConnection getDBConnection() {
		return dbConnection;
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