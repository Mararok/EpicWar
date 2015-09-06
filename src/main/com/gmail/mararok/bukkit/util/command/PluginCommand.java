/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginCommand<P extends JavaPlugin> implements CommandExecutor {
  private String name;
  private CommandMetadata metadata;
  private P plugin;

  public PluginCommand(String name, CommandMetadata metadata, P plugin) {
    this.name = name;
    this.metadata = metadata;
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    return onCommand(sender, new CommandArguments<P>(args));
  }

  protected abstract boolean onCommand(CommandSender sender, CommandArguments<P> arguments);
  
  protected void sendDescription(CommandSender sender) {
    String message = ChatColor.YELLOW + "" + ChatColor.BOLD + getDisplayName() + ChatColor.RESET + " - " + getDescription();
    sender.sendMessage(message);
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

  public String[] getPermissions() {
    return metadata.permissions;
  }
  
  public boolean hasPermissions(CommandSender sender) {
    for (String permission : getPermissions()) {
      if (!sender.hasPermission(permission)) {
        return false;
      }
    }
    return true;
  }
  
  public int getRequiredArgumentAmount() {
    return metadata.requiredArgumentAmount;
  }
  
  public P getPlugin() {
    return plugin;
  }
}
