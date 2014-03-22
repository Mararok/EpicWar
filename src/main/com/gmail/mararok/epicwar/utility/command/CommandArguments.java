/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility.command;

/** Simple command arguments wrapper.
 * Supports subcommands in args, type checking methods and required args amount check
 */

public class CommandArguments {
	private PluginCommand Command;
	private String[] Arguments;
	
	public CommandArguments(PluginCommand command, String[] rawArguments) {
		setCommand(command);
		Arguments = rawArguments;
	}
	
	public String asString(int index) {
		return Arguments[Command.getSubLevel()+index];
	}
	
	public boolean isDouble(int index) {
		try {
			asDouble(index);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public double asDouble(int index) {
		return Double.parseDouble(asString(index));
	}
	
	public boolean isFloat(int index) {
		try {
			asFloat(index);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public float asFloat(int index) {
		return Float.parseFloat(asString(index));
	}
	
	public boolean isLong(int index) {
		try {
			asLong(index);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public long asLong(int index) {
		return Long.parseLong(asString(index));
	}
	
	public boolean isInt(int index) {
		try {
			asInt(index);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public int asInt(int index) {
		return Integer.parseInt(asString(index));
	}

	public boolean isShort(int index) {
		try {
			asShort(index);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public short asShort(int index) {
		return Short.parseShort(asString(index));
	}
	
	public char asChar(int index) {
		return asString(index).charAt(0);
	}
	
	public boolean isByte(int index) {
		try {
			asByte(index);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public byte asByte(int index) {
		return Byte.parseByte(asString(index));
	}
	
	public boolean isBoolean(int index) {
		try {
			asBoolean(index);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public boolean asBoolean(int index) {
		return Boolean.parseBoolean(asString(index));
	}
	
	
	public boolean hasRequired() {
		return ( getAmount() >= Command.getRequiredArgumentsAmount() );
	}
	
	public boolean isExists(int index) {
		return getAmount() > index;
	}
	
	public int getAmount() {
		return Arguments.length - Command.getSubLevel();
	}
	
	public void setCommand(PluginCommand command) {
		Command = command;
	}
}
