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
import com.gmail.mararok.epicwar.player.WarPlayer;

public class FactionLeaveCommand extends PluginCommand {

	public FactionLeaveCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
		super(plugin, parent,"leave");
		setOnlyPlayer();
		setDescription(Language.CD_FACTION_LEAVE);
		setUsage("/ewf leave");
	}

	@Override
	public boolean onCommandAsPlayer(WarPlayer player, CommandArguments arguments) {
		player.leaveFromFaction();
		return true;
	}

}
