/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.war;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.airdrop.AirdropConfig;
import com.gmail.mararok.epicwar.controlpoint.ControlPointsManager;
import com.gmail.mararok.epicwar.faction.FactionsManager;
import com.gmail.mararok.epicwar.player.PlayersManager;
import com.gmail.mararok.epicwar.sector.SectorsManager;
import com.gmail.mararok.epicwar.sector.WoolsMapManager;
import com.gmail.mararok.epicwar.utility.DataObject;
import com.gmail.mararok.epicwar.utility.Disposable;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;


public class War implements Disposable,DataObject<WarInfo>{
	private WarInfo Info;
	private EpicWarPlugin Plugin;
	private RegionManager Regions;
	
	private World WarWorld;
	private Location NeutralSpawn;
	private AirdropConfig ADConfig;
	
	private FactionsManager Factions;
	private SectorsManager Sectors;
	private ControlPointsManager ControlPoints;
	private WoolsMapManager WoolMaps;
	private ConfigurationSection SectorsGeneratorConfig;
	
	public War(WarInfo info, EpicWarPlugin plugin, FileConfiguration config) throws Exception {
		Info = info;
		Plugin = plugin;
		SectorsGeneratorConfig = config.getConfigurationSection("sectors.generator.config");
		ADConfig = AirdropConfig.createFromWarConfig(plugin,config);
		
		ControlPoints = new ControlPointsManager(this);
		Sectors = new SectorsManager(this);
		Factions = new FactionsManager(this);
		WoolMaps = new WoolsMapManager(this);
		
		loadData();
		init();
	}
	
	private void loadData() throws Exception {
		getPlugin().logInfo(String.format("Loading war: %s data",getName()));
		ControlPoints.load();
		Sectors.load();
		Factions.load();
		WoolMaps.load();
	}
	
	private void init() {
		getPlugin().logInfo(String.format("Initing war: %s",getName()));
		List<World> list = getPlugin().getServer().getWorlds();
		for (World w : list) {
			getPlugin().logInfo("Checking world: "+w.getName());
			if (w.getName().equals(getInfo().worldName)) {
				getPlugin().logInfo("World exists:"+w.getName());
				WarWorld = w;
			}
		}
	
		//WarWorld = getPlugin().getServer().getWorld(getInfo().worldName.trim());
		if (WarWorld == null) {
			getPlugin().logInfo("War world name isn't corect: "+getInfo().worldName);
			getPlugin().setInvalid();
			return;
		}
		NeutralSpawn = getWorld().getSpawnLocation();
		Regions = WGBukkit.getPlugin().getRegionManager(WarWorld);
		
		ControlPoints.init();
		Sectors.init();
		Factions.init();
		WoolMaps.init();
	}
	
	public AirdropConfig getAirdropConfig() {
		return ADConfig;
	}
	
	public Location getNeutralSpawn() {
		return NeutralSpawn;
	}
	
	public RegionManager getRegions() {
		return Regions;
	}
	
	public  World getWorld() {
		return WarWorld;
	}
	public PlayersManager getPlayers() {
		return getPlugin().getPlayers();
	}
	
	public FactionsManager getFactions() {
		return Factions;
	}
	
	public  SectorsManager getSectors() {
		return Sectors;
	}
	
	public  ControlPointsManager getControlPoints() {
		return ControlPoints;
	}
	
	public ConfigurationSection getSectorsGeneratorConfig() {
		return SectorsGeneratorConfig;
	}
	
	public WoolsMapManager getWoolMaps() {
		return WoolMaps;
	}
	
	public  EpicWarPlugin getPlugin() {
		return Plugin;
	}

	@Override
	public WarInfo getInfo() {
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

	@Override
	public void dispose() {
		Factions.dispose();
		Sectors.dispose();
		ControlPoints.dispose();
	}
}
