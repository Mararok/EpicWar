/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

@SuppressWarnings("serial")
public class SectorExistsException extends Exception {
	String Name;
	public SectorExistsException(String name) {
		Name = name;
	}
	public String getName() {
		return Name;
	}
}
