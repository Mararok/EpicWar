/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.airdrop.Airdrop;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class AirdropRequestCommand extends PluginCommand {

	public AirdropRequestCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
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
