/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.war;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;

public class WarCommands extends CommandsSet {

	public WarCommands(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin, parent,"War");
		setDescription(Language.CD_WAR);
		setUsage("\\eww [subcommand]");
		addCommand(new WarListCommand(getPlugin(),this));
	}


}
