package com.gmail.mararok.epicwar.command.sector;

import org.bukkit.Location;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.sector.WoolsMapOrientation;
import com.gmail.mararok.epicwar.sector.WoolsMapInfo;
import com.gmail.mararok.epicwar.utility.command.CommandArguments;
import com.gmail.mararok.epicwar.utility.command.CommandsSet;
import com.gmail.mararok.epicwar.utility.command.PluginCommand;

public class SectorSetMapCommand extends PluginCommand {

	public SectorSetMapCommand(EpicWarPlugin plugin, CommandsSet parent) {
		super(plugin,parent,"setmap",true);
		setOnlyPlayer();
		setRequiredArgumentsAmount(2);
		setDescription(Language.CD_SECTOR_SETMAP);
		setUsage("\\ews setmap <name> <sectorsPerLine> [orientation]");
	}

	@Override
	public boolean onCommandAsAdmin(WarPlayer admin, CommandArguments arguments) {
		WoolsMapInfo mapInfo = new WoolsMapInfo();
		mapInfo.name = arguments.asString(0);
		mapInfo.sectorsPerLine = arguments.asInt(1);
		mapInfo.Orientation = WoolsMapOrientation.VERTICAL;
			
		Location loc = admin.getLocation();
		mapInfo.x = loc.getBlockX();
		mapInfo.y = loc.getBlockY();
		mapInfo.z = loc.getBlockZ();
			
		admin.getWar().getWoolMaps().create(mapInfo);
	
		return true;
	}

}
