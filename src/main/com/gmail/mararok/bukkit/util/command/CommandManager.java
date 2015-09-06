package com.gmail.mararok.bukkit.util.command;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager<P extends JavaPlugin> {
  private P plugin;
  private Collection<PluginCommand<P>> commands;

  public CommandManager(P plugin) {
    this.plugin = plugin;
    commands = new ArrayList<PluginCommand<P>>();
  }

  public void addCommand(PluginCommand<P> command) {
     commands.add(command);
  }
  
  
  public P getPlugin() {
    return plugin;
  }
}
