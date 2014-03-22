/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.controlpoint;

import com.gmail.mararok.epicwar.player.WarPlayer;

public class NoControlPoint extends ControlPoint {

	public NoControlPoint(ControlPointInfo info, ControlPointsManager controlPoints) {
		super(info,controlPoints);
	}
	
	@Override
	public void init() {}
	
	@Override 
	public void update() {}
	
	@Override
	public void addOccupantPlayer(WarPlayer occupant) {}
	
	@Override
	public void removeOccupantPlayer(WarPlayer occupant) {}
	
	@Override
	public boolean isUnderSige() {
		return false;
	}
	
	@Override
	public boolean canCapture() {
		return false;
	}
	
	@Override
	public boolean isWithin(WarPlayer warPlayer) {
		return false;
	}

}
