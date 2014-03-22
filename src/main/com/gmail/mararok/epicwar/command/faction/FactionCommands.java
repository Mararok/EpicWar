/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;

public class FactionCommands extends CommandsSet {
	public FactionCommands(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin,parent,"ewf");
		setDescription(Language.CD_FACTION);
		setUsage("\\ewf [subcommand]");
		
		addCommand(new FactionJoinCommand(getPlugin(),this));
		addCommand(new FactionLeaveCommand(getPlugin(),this));
		addCommand(new FactionListCommand(getPlugin(),this));
		addCommand(new FactionInfoCommand(getPlugin(),this));
		addCommand(new FactionColorsCommand(getPlugin(),this));
		addCommand(new FactionCreateCommand(getPlugin(),this));
		addCommand(new FactionSpawnCommand(getPlugin(),this));
		addCommand(new FactionSetSpawnCommand(getPlugin(),this));
	}
}
