/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public enum Language {
	CD_ALL("all.command","Shows all EpicWar subcommands"),
	CD_ALL_AIRDROP_REQUEST("all.commands.airdropRequest","Drops eq from sky on battlefield"),
	CD_ALL_DUMP("all.commands.dump","Dumps selected war data to file"),
	
	CD_WAR("war.command","Shows all war subcommands"),
	CD_WAR_LIST("war.commands.list","Shows all current wars"),
	
	CD_POINT("point.command","Shows all control points subcommands"),
	CD_POINT_CREATE("point.commands.create","Creates point at current location in sector"),
	
	POINT_CREATE_ON_WILD("point.messages.createOnWild","You can't create point on wild"),
	POINT_CREATE_RADIUS("point.messages.createRadius","You can't create point with radius: %d, min is %d"),
	POINT_CREATE_POWER("point.messages.createPower","You can't create point with power: %d, min is %d"),
	POINT_CREATE_EXISTS("point.messages.createExists","Control point with name %d exists !!!"),
	
	
	CD_SECTOR("sector.command","Shows all sectors subcommands"),
	CD_SECTOR_GEN("sector.commands.gen","Generates sectors from war sectors description"),
	CD_SECTOR_INFO("sector.commands.info","Shows current sector info"),
	CD_SECTOR_SETMAP("sector.commands.setmap","Sets sectors map on current loaction"),
	
	CD_FACTION("faction.command","Shows all EpicWar faction subcommands"),
	CD_FACTION_CREATE("faction.commands.create","Creates faction with given name and color"),
	CD_FACTION_JOIN("faction.commands.join","Joining player to selected faction"),
	CD_FACTION_LEAVE("faction.commands.leave","Left player from current faction"),
	CD_FACTION_INFO("faction.commands.info","Shows player faction info"),
	CD_FACTION_COLORS("faction.commands.colors","Shows supported faction colors"),
	CD_FACTION_LIST("faction.commands.list","Shows faction list for current war"),
	CD_FACTION_SPAWN("faction.commands.spawn","Teleports player to faction spawn"),
	CD_FACTION_SETSPAWN("faction.commands.setspawn","Sets faction spawn (can use only in faction capital sector)"),
	
	FACTION_JOINED("faction.messages.joined","You joined to faction"),
	FACTION_FULL("faction.messages.full","This faction is full"),
	FACTION_CANT_CREATE_ON_WILD("faction.messages.cantCreateOnWild","You can't create Faction on wild"),
	FACTION_CANT_CREATE_ON_OTHER_CAPITAL("faction.messages.cantCreateOnOtherCapital","You can't create new faction on other faction capital"),
	FACTION_CREATED("faction.messages.created","Faction %s created"),
	FACTION_EXISTS("faction.messages.exists","Faction with name %s exists !"),
	FACTION_COLOR_NOT_SUPPORTED_OR_USED("faction.messages.colorNotSupportedOrUsed","Can't create faction, color: %s not supported or used"),
	FACTION_NOT_EXISTS("faction.messages.notExists", "Faction %s not exists"),
	FACTION_MUST_LEAVE_BEFORE("faction.messages.mustLeaveBefore","You can't join to new faction, you must leave current"),
	FACTION_CAPTURED_SECTOR("faction.messages.capturedSector","Your faction captured %s sector"),
	FACTION_LOST_SECTOR("faction.messages.lostSector","Your faction lost %s sector"),
	FACTION_CAPTURED_POINT("faction.messages.capturedPoint","Your faction captured %s point in %s sector"),
	FACTION_LOST_POINT("faction.messages.lostPoint","Your faction lost %s point in %s sector");
	private String Path;
	private String Default;
	private String Current;
	
	private Language(String path, String defaultValue) {
		Path = path;
		Default = defaultValue;
	}
	
	public void set(String value) {
		Current = value;
	}
	
	public String get() {
		return (Current == null) ? Default : Current;
	}
	
	public String getPath() {
		return Path;
	}
	
	private static final String LANGUAGES_FOLDER = "\\langs\\";
	private static final String LANGUAGES_EXT = ".lang";
	
	public static void createLangsFolderIfNotExists(EpicWarPlugin plugin) {
		File file = new File(plugin.getDataFolder().getPath()+LANGUAGES_FOLDER);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	public static void load(EpicWarPlugin plugin, String langPrefix) {
		createLangsFolderIfNotExists(plugin);
		String langFilename = plugin.getDataFolder().getPath()+LANGUAGES_FOLDER+langPrefix+LANGUAGES_EXT;
		File file = new File(langFilename);
		if (file.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			for (Language item : Language.values()) {
				item.set(config.getString(item.getPath()));
			}
			
		} else {
			plugin.logSevere("Language file: "+langFilename+"not exists, loaded default language");
		}
	}
	
	public static void save(EpicWarPlugin plugin, String langPrefix) {
		createLangsFolderIfNotExists(plugin);
		String langFilename = plugin.getDataFolder().getPath()+LANGUAGES_FOLDER+langPrefix+LANGUAGES_EXT;
		File file = new File(langFilename);
		if (file.exists()) {
			plugin.logInfo("Language file: "+langFilename+" is exists, ignore it");
		}
		
		YamlConfiguration config = new YamlConfiguration();
		
		for (Language item : Language.values()) {
			config.set(item.getPath(),item.get());
		}
		
		try {
			config.save(file);
		} catch (IOException e) {
			plugin.logSevere("Can't save language file in "+langFilename);
		}
		
			
	}
	
	public String toString() {
		return get();
	}
}
