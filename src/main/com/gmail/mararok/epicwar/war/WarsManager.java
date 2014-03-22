/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */

package com.gmail.mararok.epicwar.war;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.utility.Disposable;

public class WarsManager implements Disposable {
	private static String WARS_PATH = "wars\\";
	private static final String WAR_EXT = ".ward";
	private EpicWarPlugin Plugin;
	
	private HashMap<String,War> Wars;
	private HashMap<String,War> WarsWorlds;
	
	public WarsManager(EpicWarPlugin plugin) {
		Plugin = plugin;
		plugin.saveResource(WARS_PATH+"WarTemplate"+WAR_EXT,true);
		WARS_PATH = plugin.getDataFolder().getPath()+"\\"+WARS_PATH;
		
		Wars = new HashMap<String,War>();
		WarsWorlds = new HashMap<String,War>();
	}
	
	public void load() throws Exception {
		for (String warName : getPlugin().Config.wars) {
			FileConfiguration config = loadConfig(warName);
			if (config == null) {
				continue;
			}
			
			WarInfo info = new WarInfo();
			info.id = config.getInt("id");
			info.name = config.getString("name");
			info.desc = config.getString("desc");
			SimpleDateFormat format = new SimpleDateFormat("DD.MM.YYYY HH:MM");
			info.startTime = format.parse(config.getString("startTime"));
			info.worldName = config.getString("world.name");
			info.pointsKill = config.getInt("points.kill");
			info.pointsDeath = config.getInt("points.death");
			info.pointsCapturePoint = config.getInt("points.capture.point");
			info.pointsCaptureSector = config.getInt("points.capture.sector");
			info.powerDefender = config.getInt("power.defender");
			info.powerAttacker = config.getInt("power.attacker");
			
			War war = new War(info,getPlugin(),config);
			Wars.put(info.name,war);
			WarsWorlds.put(info.worldName,war);
		}
	}
	
	public List<War> getList() {
		return (List<War>)Wars.values();
	}
	
	public War getByName(String name) {
		return Wars.get(name);
	}
	
	public War getByWorld(World world) {
		return WarsWorlds.get(world.getName());
	}
	
	public boolean isExists(String name) {
		return Wars.containsKey(name);
	}
	
	public boolean isExists(World world) {
		return WarsWorlds.containsKey(world.getName());
	}
	
	private YamlConfiguration loadConfig(final String filename) throws IOException {
		File f = new File(WARS_PATH+filename+WAR_EXT);
		if (!f.exists()) {
			getPlugin().logSevere("War file in path: "+filename+WAR_EXT+" not exists");
			return null;
		}
		
		return YamlConfiguration.loadConfiguration(f);
	}
	
	public EpicWarPlugin getPlugin() {
		return Plugin;
	}

	@Override
	public void dispose() {
		for (War war : Wars.values()) {
			war.dispose();
		}
		
	}
}
