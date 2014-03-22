/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.sector;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;

public class SectorCommands extends CommandsSet {

	public SectorCommands(EpicWarPlugin plugin,CommandsSet parent) {
		super(plugin,parent,"ews");
		
		setOnlyPlayer();
		setDescription(Language.CD_SECTOR);
		setUsage("\\ews [subcommand]");
		
		addCommand(new SectorGenCommand(getPlugin(),this));
		addCommand(new SectorSetMapCommand(getPlugin(),this));
		addCommand(new SectorInfoCommand(getPlugin(),this));
	}
}
