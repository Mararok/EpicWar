/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import java.util.List;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.command.CommandArguments;
import com.gmail.mararok.epicwar.command.PluginParentCommand;
import com.gmail.mararok.epicwar.command.PluginCommand;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class FactionListCommand extends PluginCommand {

	public FactionListCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
		super(plugin, parent,"list");
		setOnlyPlayer();
		setDescription(Language.CD_FACTION_LIST);
		setUsage("\\ewf list");
	}

	@Override
	public boolean onCommandAsPlayer(WarPlayer player, CommandArguments arguments) {
		List<Faction> factions = player.getWar().getFactions().getList();
		if (factions.size() > 1) {
			int size = factions.size();
			String[] list = new String[size+1];
			list[0] = "Factions list:";
			
			Faction faction;
			for (int i = 1; i < size; ++i) {
				faction = factions.get(i);
				list[i] = "["+i+"] "+faction.getInfo().color+faction.getName();
			}
			
			player.sendMessage(list);
			
		} else {
			player.sendMessage("No factions defined yet");
		}
		
		return true;	
	}

}
