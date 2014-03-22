/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.controlpoint;

import org.bukkit.scheduler.BukkitRunnable;

public class ControlPointsUpdater extends BukkitRunnable {
	private ControlPointsManager ControlPoints;
	private boolean Enabled;
	public ControlPointsUpdater(ControlPointsManager controlPoints) {
		ControlPoints = controlPoints;
		Enabled = true;
	}
	
	@Override
	public void run() {
		if (isEnable()) {
			//ControlPoints.getPlugin().logInfo("up");
			ControlPoints.update();
		}
	}
	
	public void setEnabled(boolean enable) {
		Enabled = enable;
	}
	
	public boolean isEnable() {
		return Enabled;
	}
}
