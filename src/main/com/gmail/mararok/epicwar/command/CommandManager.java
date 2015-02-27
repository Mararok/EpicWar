package com.gmail.mararok.epicwar.command;

import java.util.ArrayList;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public class CommandManager {
	private EpicWarPlugin plugin;
	private ArrayList<PluginParentCommand> commands;
	
	public CommandManager(EpicWarPlugin plugin) {
		this.plugin = plugin;
		commands = new ArrayList<PluginParentCommand>();
	}
	
	public void addCommand(PluginCommand command) {
		
	}
}
