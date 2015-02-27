/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2014 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public abstract class PluginParentCommand extends PluginChildCommand {
	private ArrayList<PluginChildCommand> children;
	private int nextIndex;
	
	public PluginParentCommand(EpicWarPlugin plugin, String name, PluginParentCommand parent) {
		super(plugin,name,parent);
		children = new ArrayList<PluginChildCommand>();
	}
	
	protected void addCommand(PluginChildCommand command) {
		command.setIndex(nextIndex);
		children.add(command);
		++nextIndex;
	}
	
	protected PluginChildCommand getCommandByName(String name) {
		for (int i = 0; i < nextIndex;++i) {
			if (children.get(i).getName() == name) {
				return children.get(i);
			}
		}
		
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] rawArguments) {
		CommandArguments arguments = new CommandArguments(rawArguments);
		if (arguments.isExists(0)) {
			return execSubCommand(sender,arguments);
		}
		
		sendDescription(sender);
		return true;
	}
	
	private boolean execSubCommand(CommandSender sender,CommandArguments arguments) {
		String subCommandName = arguments.asString(0);
		PluginChildCommand childCommand = getCommandByName(subCommandName);
		if (childCommand != null) {
			return childCommand.onCommand(sender, arguments.getSubArguments(childCommand.getDepthLevel()));
		}
		sender.sendMessage("Command: "+subCommandName+" not exists or not implemented yet");
		return false;
	}
	
	@Override
	public void sendDescription(CommandSender sender) {
		String[] subCommandsInfo = new String[SubCommandsList.size()+1];
		subCommandsInfo[0] = ChatColor.YELLOW+""+ChatColor.BOLD+getDescription();
		int i = 1;
		for (PluginCommand sub : SubCommandsList) {
			subCommandsInfo[i] = sub.getDisplayName()+ChatColor.RESET+" - "+sub.getDescription();
			++i;
		}
		sender.sendMessage(subCommandsInfo);
	}
	
	
	
}
