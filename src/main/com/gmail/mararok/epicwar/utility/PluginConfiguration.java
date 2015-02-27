/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;

public class PluginConfiguration implements Disposable {
	private EpicWarPlugin plugin;
	private boolean needSave = false;
	private FileConfiguration config;
	
	public PluginConfiguration(EpicWarPlugin plugin) {
		this.plugin = plugin;
		plugin.saveDefaultConfig();
		config = plugin.getConfig();
	}
	
	public String getString(String path) {
		return config.getString(path);
	}
	
	private void loadLanguageConfig(FileConfiguration config) {
		Language.save(Plugin,"en");
		LanguagePrefix = config.getString("language");
		if (LanguagePrefix != null) {
			Language.load(Plugin,LanguagePrefix);
		}
	}
	
	private void loadDBConfig(FileConfiguration config) {
		config.getConfigurationSection(path)
		db.engine = config.getString("database.engine");
		db.host = config.getString("database.host");
		db.name = config.getString("database.name");
		db.user = config.getString("database.user");
		db.password = config.getString("database.password");
	}
	
	private void loadWarsConfig(FileConfiguration config) {
		wars = config.getStringList("wars");
	}
	
	@Override
	public void dispose() {
		if (needSave) {
			saveConfig();
		}
	}
	
	private void saveConfig() {
		plugin.saveConfig();
		needSave = false;
	}
}
