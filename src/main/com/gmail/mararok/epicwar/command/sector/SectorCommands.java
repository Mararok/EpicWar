/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.sector;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.command.PluginParentCommand;

public class SectorCommands extends PluginParentCommand {

	public SectorCommands(EpicWarPlugin plugin,PluginParentCommand parent) {
		super(plugin,parent,"ews");
		
		setOnlyPlayer();
		setDescription(Language.CD_SECTOR);
		setUsage("\\ews [subcommand]");
		
		addCommand(new SectorGenCommand(getPlugin(),this));
		addCommand(new SectorSetMapCommand(getPlugin(),this));
		addCommand(new SectorInfoCommand(getPlugin(),this));
	}
}
