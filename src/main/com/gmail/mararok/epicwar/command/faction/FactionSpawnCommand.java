/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.utility.command.CommandArguments;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;
import com.gmail.mararok.epicwar.utility.command.PluginCommand;

public class FactionSpawnCommand extends PluginCommand {

	public FactionSpawnCommand(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin, parent,"spawn");
		setOnlyPlayer();
		setDescription(Language.CD_FACTION_SPAWN);
		setUsage("\\ewf spawn");
	}

	@Override
	public boolean onCommandAsPlayer(WarPlayer player, CommandArguments arguments) {
		player.teleport2FactionCaptital();
		return true;
	}

}
