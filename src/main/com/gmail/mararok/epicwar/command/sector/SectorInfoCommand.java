/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.sector;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.sector.Sector;
import com.gmail.mararok.epicwar.utility.command.CommandArguments;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;
import com.gmail.mararok.epicwar.utility.command.PluginCommand;

public class SectorInfoCommand extends PluginCommand {

	public SectorInfoCommand(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin, parent,"info");
		setOnlyPlayer();
		setDescription(Language.CD_SECTOR_INFO);
		setUsage("\\ews info");
	}

	@Override
	public boolean onCommandAsPlayer(WarPlayer player, CommandArguments arguments) {
		if (player.hasFaction()) {
			player.sendMessage("You are at "+player.getSector().getName()+" sector");
		} else {
			Sector sector = player.getWar().getSectors().getFromLocation(player.getLocation());
			player.sendMessage("You are at "+sector.getName()+" sector");
		}
		
		return true;
	}

}
