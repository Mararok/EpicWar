/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.command;

import java.util.Arrays;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mararok.bukkit.util.StringUtil;

/**
 * Simple command arguments wrapper. 
 * Supports subcommands in args, type checking methods and required args amount check
 */

public class CommandArguments<P extends JavaPlugin> {
  private String[] rawArguments;
  private int amount;

  public CommandArguments(String[] rawArguments) {
    this.rawArguments = rawArguments;
    this.amount = (rawArguments != null) ? rawArguments.length : 0;
  }

  public boolean isExists(int index) {
    return getAmount() > index;
  }

  public String get(int index) {
    return rawArguments[index];
  }

  public boolean isNumber(int index) {
    return isExists(index) && StringUtil.isNumber(get(index));
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

  public boolean isBoolean(int index) {
    return isExists(index) && StringUtil.isBoolean(get(index));
  }

  public boolean asBoolean(int index) {
    return Boolean.parseBoolean(get(index));
  }

  public char asChar(int index) {
    return get(index).charAt(0);
  }

  public boolean hasRequired(PluginCommand<P> command) {
    return (getAmount() >= command.getRequiredArgumentAmount());
  }

  public CommandArguments<P> getArgumentsForChild(ChildPluginCommand<P> command) {
    if (getAmount() >= command.getDepthLevel()) {
      return new CommandArguments<P>(Arrays.copyOfRange(rawArguments, command.getDepthLevel(), getAmount()));
    } else {
      return new CommandArguments<P>(null);
    }
  }

  public int getAmount() {
    return amount;
  }

}
