/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public abstract class CommandsSet extends PluginCommand {
	private HashMap<String,PluginCommand> SubCommands;
	private List<PluginCommand> SubCommandsList;
	
	public CommandsSet(EpicWarPlugin plugin, CommandsSet parent,String name) {
		super(plugin,parent,name,false);
		SubCommands = new HashMap<String,PluginCommand>();
		SubCommandsList = new LinkedList<PluginCommand>();
	}
	
	protected void addCommand(PluginCommand executor) {
		executor.setSubLevel(getSubLevel()+1);
		SubCommands.put(executor.getName(),executor);
		SubCommandsList.add(executor);
	}
	
	protected PluginCommand getCommand(String name) {
		return SubCommands.get(name);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] rawArguments) {
		CommandArguments arguments = new CommandArguments(this, rawArguments);
		if (arguments.isExists(0)) {
			return execSubCommand(sender,arguments);
		}
		sendDescription(sender);
		return true;
	}
	
	private boolean execSubCommand(CommandSender sender,CommandArguments arguments) {
		String subCommandName = arguments.asString(0);
		PluginCommand subCommand = getCommand(subCommandName);
		if (subCommand != null) {
			arguments.setCommand(subCommand);
			return subCommand.onCommand(sender, arguments);
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
