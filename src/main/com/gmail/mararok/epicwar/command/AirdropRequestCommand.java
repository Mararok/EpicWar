/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.airdrop.Airdrop;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.utility.command.CommandArguments;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;
import com.gmail.mararok.epicwar.utility.command.PluginCommand;

public class AirdropRequestCommand extends PluginCommand {

	public AirdropRequestCommand(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin, parent,"adr");
		setOnlyPlayer();
		setDescription(Language.CD_ALL_AIRDROP_REQUEST);
		setUsage("\\ew adr");
	}

	public boolean onCommandAsPlayer(WarPlayer sender, CommandArguments arguments) {
		new Airdrop(sender);
		return true;
	}

}
