/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility.command;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.player.PlayersManager;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class PluginCommand implements CommandExecutor {
	private EpicWarPlugin Plugin;
	private String Description = "EMPTY DESC";
	private String Usage = "EMPTY USAGE";
	private String Name;
	private String DisplayName;
	private int SubLevel;
	
	private boolean OnlyPlayer;
	private int RequiredArgumentsAmount;
	
	public PluginCommand(EpicWarPlugin plugin, CommandsSet parent, String name) {
		this(plugin,parent,name,false);
	}
	
	public PluginCommand(EpicWarPlugin plugin, CommandsSet parent, String name,boolean onlyAdmin) {
		Plugin = plugin;
		Name = name;
		DisplayName = (onlyAdmin) ? ChatColor.RED+name : ChatColor.GREEN+name;
		DisplayName = ChatColor.BOLD+DisplayName;
		
		if (parent != null)
			setSubLevel(parent.getSubLevel()+1);
		
		OnlyPlayer = false;
		RequiredArgumentsAmount = 0;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		sender.sendMessage("Plugin error: "+command.getName()+"A: "+Arrays.toString(args));
		return false;
	}
	
	public boolean onCommand(CommandSender sender, CommandArguments arguments) {
		if (getPlugin().isValid()) {
			
			if (!arguments.hasRequired()) {
				sender.sendMessage("Not enough arguments, Usage:");
				sendUsage(sender);
				return false;
			}
			
			if (isOnlyForPlayer()) {
				if (!isPlayer(sender)) {
					sender.sendMessage("You can't execute this command from console");
					return false;
				}
				
				WarPlayer warPlayer = getWarPlayer(sender);
				if (!getPlayers().isAtWar(warPlayer)) {
					sender.sendMessage("To execute this command you must be at war world");
					return false;
				}
				
				if (isAdmin(sender)) {
					if (onCommandAsAdmin(warPlayer,arguments)) {
						return true;
					} else {
						sendUsage(sender);
						return false;
					}
				}
				
				if (onCommandAsPlayer(warPlayer,arguments)) {
					return true;
				} else {
					sendUsage(sender);
					return false;
				}
			} 
			
			if (onCommandAsConsole(sender,arguments)) {
				return true;
			} else {
				sendUsage(sender);
				return false;
			}
		}
		
		sender.sendMessage("EpicWar is invalid, you can't execute commands");;
		return false;
	}
	
	/** Execute Command as Player
	 * In default, if not overrided command is for admins
	 */
	public boolean onCommandAsPlayer(WarPlayer player, CommandArguments arguments) {
		player.sendMessage("Only admins can use this command");
		return true;
	}
	
	/** Execute Command as Admin
	 * In default, if not overrided command execute version for player
	 */
	public boolean onCommandAsAdmin(WarPlayer admin, CommandArguments arguments) {
		return onCommandAsPlayer(admin, arguments);
	}

	/** Execute Command as Console or Player, when warplayer instance isn't necessary
	 * In default, if not overrided is empty 
	 */
	public boolean onCommandAsConsole(CommandSender sender, CommandArguments arguments) {
		return true;
	}

	public boolean isPlayer(final CommandSender sender) {
		return sender instanceof Player;
	}
	
	public boolean isConsole(final CommandSender sender) {
		return sender instanceof ConsoleCommandSender;
	}
	
	public boolean isAdmin(final CommandSender sender) {
		return (isPlayer(sender) && sender.isOp() || sender.hasPermission("epicwar.admin"));
	}
	
	protected void setOnlyPlayer() {
		OnlyPlayer = true;
	}
	
	public boolean isOnlyForPlayer() {
		return OnlyPlayer;
	}
	
	public int getRequiredArgumentsAmount() {
		return RequiredArgumentsAmount;
	}
	
	public void setRequiredArgumentsAmount(int argumentsAmount) {
		RequiredArgumentsAmount = argumentsAmount;
	}
	
	public int getSubLevel() {
		return SubLevel;
	}
	
	public void setSubLevel(int level) {
		SubLevel = level;
	}
	
	public  void setDescription(String description) {
		Description = description;
	}
	
	public  void setDescription(Language langItem) {
		Description = langItem.toString();
	}
	
	public String getDescription() {
		return Description;
	}
	
	public void sendDescription(CommandSender sender) {
		sender.sendMessage(Description);
	}
	
	public void sendUsage(CommandSender sender) {
		sender.sendMessage(Usage);
	}
	
	public void setUsage(String usage) {
		Usage = usage;
	}
	
	public String getName() {
		return Name;
	}
	
	public String getDisplayName() {
		return DisplayName;
	}
	
	public WarPlayer getWarPlayer(CommandSender sender) {
		return getPlayers().get((Player)sender);
	}
	
	public PlayersManager getPlayers() {
		return getPlugin().getPlayers();
	}
	
	public EpicWarPlugin getPlugin() {
		return Plugin;
	}
}
