/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

@SuppressWarnings("serial")
public class SectorsGeneratorConfigException extends Exception {
	public SectorsGeneratorConfigException(String configFieldName, String message) {
		super(String.format("SG config error in %s field, %s",configFieldName,message));
	}
}
