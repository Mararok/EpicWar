/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

public interface DataObject<InfoType> {
	public InfoType getInfo();
	public int getID();
	public String getName();
}
