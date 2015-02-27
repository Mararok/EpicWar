/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.command.CommandArguments;
import com.gmail.mararok.epicwar.command.PluginParentCommand;
import com.gmail.mararok.epicwar.command.PluginCommand;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class FactionInfoCommand extends PluginCommand {

	public FactionInfoCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
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
