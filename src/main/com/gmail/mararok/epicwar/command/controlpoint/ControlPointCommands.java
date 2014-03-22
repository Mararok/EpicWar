/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.controlpoint;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;

public class ControlPointCommands extends CommandsSet {

	public ControlPointCommands(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin, parent,"ewp");
		
		setDescription(Language.CD_POINT);
		setUsage("\\ewp [subcommand]");
		
		addCommand(new ControlPointCreateCommand(getPlugin(),this));
	}

}
