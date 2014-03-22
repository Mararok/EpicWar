/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class SimpleSectorsGenerator implements SectorsGenerator {
	
	private int SectorSize;
	private int WidthInSectors;
	private int HeightInSectors;
	private List<String> Names;
	
	
	@Override
	public String getName() {
		return "simple";
	}
	
	@Override
	public void setConfig(ConfigurationSection config) throws SectorsGeneratorConfigException {
		SectorSize = config.getInt("size");
		WidthInSectors = config.getInt("width");
		HeightInSectors = config.getInt("height");
		Names = config.getStringList("names");
		if (SectorSize < 64) {
			throw new SectorsGeneratorConfigException("size","you can't set "+SectorSize+" value, min is 64");
		}
		
		if (WidthInSectors < 2) {
			throw new SectorsGeneratorConfigException("width","you can't set  "+WidthInSectors+" value, min is 2");
		}
		
		if (HeightInSectors < 2) {
			throw new SectorsGeneratorConfigException("height","you can't  "+HeightInSectors+" value, min is 2");
		}
		
		if (Names.size() == 0) {
			throw new SectorsGeneratorConfigException("names","you don't define names for sectors");
		}
		
		if (Names.size() != WidthInSectors*HeightInSectors) {
			throw new SectorsGeneratorConfigException("names","you don't define all names for sectors "+Names.toString());
		}
	}
	
	@Override
	public List<SectorInfo> generate(Location creatorLocation) {
		int lastX = creatorLocation.getBlockX();
		int lastZ = creatorLocation.getBlockZ();
		
		List<SectorInfo> sectors = new ArrayList<SectorInfo>(WidthInSectors*HeightInSectors);
		
		SectorInfo info = new SectorInfo();
		info.size = SectorSize;
		int current = 0;
		for (int i = 0;i < HeightInSectors;++i) {
			for (int j = 0;j < WidthInSectors;++j) {
				info.name = Names.get(current);
				current++;
				info.centerX = lastX;
				lastX += SectorSize+1;
				info.centerZ = lastZ;
				sectors.add(info);
				info = info.clone();
			}
			lastZ += SectorSize+1;
			lastX = creatorLocation.getBlockX();
		}
		return sectors;
	}

}
