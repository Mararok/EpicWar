/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public class PluginCommand implements CommandExecutor {
  private String name;
  private 
  private EpicWarPlugin plugin;

  public PluginCommand(String name, EpicWarPlugin plugin) {
    this.name = name;
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label,
      String[] args) {
    return onCommand(sender, new CommandArguments(args));
  }

  public boolean onCommand(CommandSender sender, CommandArguments arguments) {
    if (getPlugin().isValid()) {
      if (!arguments.hasRequired(this)) {
        sender.sendMessage("Not enough arguments, Usage: " + getUsage());
        return false;
      }
      return true;
    }

    sender.sendMessage("EpicWar is invalid, you can't execute command");
    return false;
  }

  public int getRequiredArgumentsAmount() {
    return metadata.requiredArgumentAmount;
  }

  public String getName() {
    return name;
  }

  public String getDisplayName() {
    return metadata.displayName;
  }

  public String getDescription() {
    return metadata.description;
  }

  public String getUsage() {
    return metadata.usage;
  }

  public EpicWarPlugin getPlugin() {
    return plugin;
  }
}
