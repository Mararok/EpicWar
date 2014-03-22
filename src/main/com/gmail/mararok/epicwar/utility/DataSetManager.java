/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.World;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.controlpoint.ControlPointsManager;
import com.gmail.mararok.epicwar.faction.FactionsManager;
import com.gmail.mararok.epicwar.player.PlayersManager;
import com.gmail.mararok.epicwar.sector.SectorsManager;
import com.gmail.mararok.epicwar.war.War;
import com.sk89q.worldguard.protection.managers.RegionManager;

public abstract class DataSetManager<DataType extends DataObject<?> > implements Disposable {
	private War WarInstance;
	
	private ArrayList<DataType> DataSet;
	private HashMap<String,DataType> NamesDataSet;
	
	public DataSetManager(War war) {
		WarInstance = war;
		DataSet = new ArrayList<DataType>(5);
		NamesDataSet = new HashMap<String,DataType>();
	}

	public abstract void load() throws Exception;
	public abstract void init() throws Exception;
	protected void optimizeMemory() {
		DataSet.trimToSize();
	}
	protected void put(DataType data) {
		DataSet.add(data);
		NamesDataSet.put(NameConverter.convertName(data.getName()),data);
	}
	
	public DataType getByID(int id) {
		return DataSet.get(id);
	}
	
	public DataType getByName(final String name) {
		return NamesDataSet.get(name);
	}
	
	public List<DataType> getList() {
		return DataSet;
	}
	
	public int size() {
		return DataSet.size();
	}
	
	public boolean isExists(final String name) {
		return NamesDataSet.containsKey(name);
	}
	
	public abstract void dump2File() throws IOException;
	
	public void dump2File(final String dumpName) throws IOException {
		Path dumpPath = Paths.get(getPlugin().getDataDumpPath());
		if (!Files.isDirectory(dumpPath)) {
			Files.createDirectory(dumpPath);
		}
		
		PrintWriter writer = new PrintWriter(Files.newBufferedWriter(
				dumpPath.resolve(dumpName+"Dump.txt"),
				StandardCharsets.UTF_8));
		writer.println("[EpicWar] "+dumpName+" dump");
		for (DataType pi : DataSet) {
			writer.println(pi);
		}
		writer.println("[EpicWar] "+dumpName+" end dump");
		writer.close();
	}
	
	public EpicWarPlugin getPlugin() {
		return WarInstance.getPlugin();
	}
	
	public War getWar() {
		return WarInstance;
	}
	
	public World getWorld() {
		return getWar().getWorld();
	}
	
	public RegionManager getRegions() {
		return getWar().getRegions();
	}
	
	public PlayersManager getPlayers() {
		return getWar().getPlayers();
	}
	
	public FactionsManager getFactions() {
		return getWar().getFactions();
	}
	
	public SectorsManager getSectors() {
		return getWar().getSectors();
	}
	
	public ControlPointsManager getControlPoints() {
		return getWar().getControlPoints();
	}
	
	public void dispose() {}
}
