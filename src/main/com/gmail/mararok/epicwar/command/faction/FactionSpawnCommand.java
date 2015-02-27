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

public class FactionSpawnCommand extends PluginCommand {

	public FactionSpawnCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
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
