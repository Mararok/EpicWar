/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.faction.FactionInfo;
import com.gmail.mararok.epicwar.faction.FactionsManager;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.sector.Sector;
import com.gmail.mararok.epicwar.utility.ColorConverter;
import com.gmail.mararok.epicwar.utility.command.CommandArguments;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;
import com.gmail.mararok.epicwar.utility.command.PluginCommand;

public class FactionCreateCommand extends PluginCommand {
	public FactionCreateCommand(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin,parent,"create",true);
		setOnlyPlayer();
		setRequiredArgumentsAmount(2);
		setDescription(Language.CD_FACTION_CREATE);
		setUsage("\\ewf create <name> <colorName>");
	}

	@Override
	public boolean onCommandAsAdmin(WarPlayer admin, CommandArguments arguments) {
		FactionsManager factions = admin.getWar().getFactions();
		String factionName = arguments.asString(0);
			
		if (factions.isExists(factionName)) {
			admin.sendFormatMessage(Language.FACTION_EXISTS,factionName);
			return false;
		}
			
		String colorName = arguments.asString(1).toUpperCase();
		ChatColor color = ChatColor.valueOf(colorName);
		if (color != null && !ColorConverter.isSupported(color) && factions.isColorUsed(color)) {
			admin.sendFormatMessage(Language.FACTION_COLOR_NOT_SUPPORTED_OR_USED,colorName);
			return false;
		}
			
		Sector capitalSector = admin.getWar().getSectors().getFromLocation(admin.getLocation());
		if (capitalSector.getID() == 0) {
			admin.sendMessage(Language.FACTION_CANT_CREATE_ON_WILD);
			return false;
		}
				
		if (factions.isCapital(capitalSector)) {
			admin.sendMessage(Language.FACTION_CANT_CREATE_ON_OTHER_CAPITAL);
			return false;
		}
			
		FactionInfo info = new FactionInfo();
		info.name = factionName;
		info.color = color;

		Location spawnLoc = admin.getLocation();
		info.spawnX = spawnLoc.getBlockX();
		info.spawnY = spawnLoc.getBlockY();
		info.spawnZ = spawnLoc.getBlockZ();
				
		info.capitalSectorID = capitalSector.getID();	
			
		factions.create(info);
			
		sendSuccessMessage(admin,info);
		return true;
	}
	
	private void sendSuccessMessage(WarPlayer player, FactionInfo info) {
		player.sendFormatMessage(Language.FACTION_CREATED,info.color+info.name);
		getPlugin().logInfo(player.getName()+" created faction "+info.color+info.name);
	}

}
