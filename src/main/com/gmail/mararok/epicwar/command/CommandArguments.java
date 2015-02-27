/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command;

import java.util.Arrays;

import com.gmail.mararok.epicwar.utility.StringUtility;

/** Simple command arguments wrapper.
 * Supports subcommands in args, type checking methods and required args amount check
 */

public class CommandArguments {
	private String[] rawArguments;
	private int amount;
	
	public CommandArguments(String[] rawArguments) {
		this.rawArguments = rawArguments;
		amount = (rawArguments != null)? rawArguments.length : 0;
	}
	
	public String get(int index) {
		return rawArguments[index];
	}
	
	public boolean isNumber(int index) {
		return isExists(index) && StringUtility.isNumber(get(index));
	}
	
	public boolean isFloat(int index) {
		return isExists(index) && StringUtility.isFloat(get(index));
	}
	
	public boolean isInteger(int index) {
		return isExists(index) && StringUtility.isInteger(get(index));
	}
	public boolean isBoolean(int index) {
		return isExists(index) && StringUtility.isBoolean(get(index));
	}
	
	public double asDouble(int index) throws NumberFormatException {
		return Double.parseDouble(get(index));
	}
	
	public float asFloat(int index) throws NumberFormatException {
		return Float.parseFloat(get(index));
	}
	
	public long asLong(int index) throws NumberFormatException {
		return Long.parseLong(get(index));
	}
	
	public int asInt(int index) throws NumberFormatException {
		return Integer.parseInt(get(index));
	}
	
	public short asShort(int index) throws NumberFormatException {
		return Short.parseShort(get(index));
	}
	
	public byte asByte(int index) throws NumberFormatException {
		return Byte.parseByte(get(index));
	}
	
	public boolean asBoolean(int index) {
		return Boolean.parseBoolean(get(index));
	}
	
	public char asChar(int index) {
		return get(index).charAt(0);
	}
	
	public boolean hasRequired(PluginCommand command) {
		return ( getAmount() >= command.getRequiredArgumentsAmount() );
	}
	
	public CommandArguments getArgumentsForChild(PluginChildCommand command) {
		if (getAmount() >= command.getDepthLevel()) {
			return new CommandArguments(Arrays.copyOfRange(rawArguments,command.getDepthLevel(),getAmount()));
		}
		return new CommandArguments(null);
	}
	
	public boolean isExists(int index) {
		return getAmount() > index;
	}
	
	public int getAmount() {
		return amount;
	}
	
}
