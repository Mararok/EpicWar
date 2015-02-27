/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

public class RegionsUtlil {
	public static void createRegion(RegionManager regions, String name, BlockVector position1, BlockVector position2) {
		regions.addRegion(new ProtectedCuboidRegion(name,position1,position2));
		try {
			regions.save();
		} catch (ProtectionDatabaseException e) {
			e.printStackTrace();
		}
	}
}
