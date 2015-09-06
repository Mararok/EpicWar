/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.command;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ParentPluginCommand<P extends JavaPlugin> extends ChildPluginCommand<P> {
  private Collection<ChildPluginCommand<P>> children;

  public ParentPluginCommand(String name, CommandMetadata metadata, P plugin) {
    super(name, metadata, plugin);
    children = new ArrayList<ChildPluginCommand<P>>();
  }

  protected void addCommand(ChildPluginCommand<P> command) {
    command.setParent(this, children.size());
    children.add(command);
  }

  @Override
  protected boolean onCommand(CommandSender sender, CommandArguments<P> arguments) {
    if (arguments.isExists(0)) {
      return execSubCommand(sender, arguments);
    }

    sendDescription(sender);
    return true;
  }

  private boolean execSubCommand(CommandSender sender, CommandArguments<P> arguments) {
    String subCommandName = arguments.get(0);
    ChildPluginCommand<P> childCommand = getCommandByName(subCommandName);
    if (childCommand != null) {
      return childCommand.onCommand(sender, arguments.getArgumentsForChild(childCommand));
    }
    
    sender.sendMessage("Command: " + subCommandName + " not exists or not implemented yet");
    return false;
  }
  
  protected ChildPluginCommand<P> getCommandByName(String name) {
    for (ChildPluginCommand<P> child : children) {
      if (child.getName() == name) {
        return child;
      }
    }

    return null;
  }

  @Override
  protected void sendDescription(CommandSender sender) {
    String[] subCommandsInfo = new String[children.size() + 1];
    subCommandsInfo[0] = ChatColor.YELLOW + "" + ChatColor.BOLD + getDescription();
    int i = 1;
    for (PluginCommand<P> sub : children) {
      subCommandsInfo[i] = sub.getDisplayName() + ChatColor.RESET + " - " + sub.getDescription();
      ++i;
    }
    sender.sendMessage(subCommandsInfo);
  }

}
