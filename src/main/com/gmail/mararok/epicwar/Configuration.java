/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.mararok.epicwar.utility.Disposable;
import com.gmail.mararok.epicwar.utility.database.DBInfo;

public class Configuration implements Disposable {
	private EpicWarPlugin Plugin;
	private boolean NeedSave = false;
	
	public DBInfo db;
	public String LanguagePrefix;
	public List<String> wars;
	
	Configuration(EpicWarPlugin plugin) throws ConfigException {
		Plugin = plugin;
		plugin.saveDefaultConfig();
		FileConfiguration config = plugin.getConfig();
		db = new DBInfo();
		loadLanguageConfig(config);
		loadDBConfig(config);
		loadWarsConfig(config);
		
	}
	
	private void loadLanguageConfig(FileConfiguration config) {
		Language.save(Plugin,"en");
		LanguagePrefix = config.getString("language");
		if (LanguagePrefix != null) {
			Language.load(Plugin,LanguagePrefix);
		}
	}
	
	private void loadDBConfig(FileConfiguration config) {
		db.engine = config.getString("database.engine");
		db.host = config.getString("database.host");
		db.name = config.getString("database.name");
		db.user = config.getString("database.user");
		db.password = config.getString("database.password");
	}
	
	private void loadWarsConfig(FileConfiguration config) throws ConfigException {
		wars = config.getStringList("wars");
	}
	
	@Override
	public void dispose() {
		if (NeedSave) {
			saveConfig();
		}
	}
	
	public void saveConfig() {
		Plugin.saveConfig();
		NeedSave = false;
	}
}
