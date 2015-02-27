/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.controlpoint;

import org.bukkit.Location;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.command.CommandArguments;
import com.gmail.mararok.epicwar.command.PluginParentCommand;
import com.gmail.mararok.epicwar.command.PluginCommand;
import com.gmail.mararok.epicwar.controlpoint.ControlPointExistsException;
import com.gmail.mararok.epicwar.controlpoint.ControlPointInfo;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.sector.Sector;

public class ControlPointCreateCommand extends PluginCommand {

	public ControlPointCreateCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
		super(plugin,parent,"create",true);
		
		setOnlyPlayer();
		setRequiredArgumentsAmount(3);
		setDescription(Language.CD_POINT_CREATE);
		setUsage("\\ewp create <pointName> <radius in blocks> <maxPower>");
	}

	@Override
	public boolean onCommandAsAdmin(WarPlayer sender, CommandArguments arguments) {
			ControlPointInfo info = new ControlPointInfo();
		try {
			Location loc = sender.getLocation();
			Sector s = sender.getWar().getSectors().getFromLocation(loc);
			if (s == null) {
				sender.sendMessage(Language.POINT_CREATE_ON_WILD);
				return false;
			}

			info.centerX = loc.getBlockX();
			info.centerY = loc.getBlockY();
			info.centerZ = loc.getBlockZ();
				
			info.sectorID = s.getID();
			info.name = arguments.asString(0);
			info.radius = arguments.asInt(1);
			if (info.radius < 2) {
				sender.sendFormatMessage(Language.POINT_CREATE_RADIUS,info.radius,3);
				return false;
			}
			info.maxPower = info.power = arguments.asInt(2);
			if (info.maxPower < 1) {
				sender.sendFormatMessage(Language.POINT_CREATE_POWER,info.power,2);
				return false;
			}
				
			sender.getWar().getControlPoints().create(info);
			sendSuccessMessage(sender,info);
			return true;
		} catch (ControlPointExistsException e) {
			sender.sendFormatMessage(Language.POINT_CREATE_EXISTS);
		}
		return false;
	}

	private void sendSuccessMessage(WarPlayer player, ControlPointInfo info) {
		String sectorName = player.getWar().getSectors().getByID(info.sectorID).getName();
		player.sendMessage("Control point "+info.name+
			" created in sector "+sectorName
		);
		getPlugin().logInfo(player.getName()+" created controlPoint "+info.name+" in sector "+sectorName);
	}
}
