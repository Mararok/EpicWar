/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.utility.command.CommandArguments;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;
import com.gmail.mararok.epicwar.utility.command.PluginCommand;

public class FactionInfoCommand extends PluginCommand {

	public FactionInfoCommand(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin, parent,"info");
		setOnlyPlayer();
		setDescription(Language.CD_FACTION_INFO);
		setUsage("\\ewf info");
	}

	@Override
	public boolean onCommandAsPlayer(WarPlayer player, CommandArguments arguments) {
		if (player.hasFaction()) {
			Faction faction = player.getFaction();
			player.sendMessage("Your faction: "+faction.getChatColor()+faction.getName());
		} else {
			player.sendMessage("You haven't faction");
		}
		
		return true;
	}

}
