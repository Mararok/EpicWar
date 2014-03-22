/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.command.controlpoint.ControlPointCommands;
import com.gmail.mararok.epicwar.command.faction.FactionCommands;
import com.gmail.mararok.epicwar.command.sector.SectorCommands;
import com.gmail.mararok.epicwar.command.war.WarCommands;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;

public class Commands extends CommandsSet {
	public Commands(EpicWarPlugin plugin) {
		super(plugin,null,"ew");
		
		setDescription(Language.CD_ALL);
		setUsage("\\ew [subcommand]");
		
		plugin.getCommand("ew").setExecutor(this);
		plugin.getCommand("ewf").setExecutor(new FactionCommands(getPlugin(),null));
		plugin.getCommand("ews").setExecutor(new SectorCommands(getPlugin(),null));
		plugin.getCommand("ewp").setExecutor(new ControlPointCommands(getPlugin(),null));
		plugin.getCommand("eww").setExecutor(new WarCommands(getPlugin(),null));
		addCommand(new DumpCommand(getPlugin(),this));
		addCommand(new AirdropRequestCommand(getPlugin(),this));
		plugin.getCommand("ew").setExecutor(this);
	}
}
