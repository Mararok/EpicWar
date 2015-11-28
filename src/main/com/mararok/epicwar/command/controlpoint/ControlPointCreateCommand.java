/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.controlpoint;

import org.bukkit.Location;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.ParentPluginCommand;
import com.mararok.epiccore.command.PluginCommand;
import com.mararok.epiccore.language.Language;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.control.ControlPointData;
import com.mararok.epicwar.control.internal.SectorImpl;
import com.mararok.epicwar.player.internal.WarPlayerImpl;

public class ControlPointCreateCommand extends PluginCommand {

  public ControlPointCreateCommand(EpicWarPlugin plugin,
      ParentPluginCommand parent) {
    super(plugin, parent, "create", true);

    setOnlyPlayer();
    setRequiredArgumentsAmount(3);
    setDescription(Language.CD_POINT_CREATE);
    setUsage("\\ewp create <pointName> <radius in blocks> <maxPower>");
  }

  @Override
  public boolean onCommandAsAdmin(WarPlayerImpl sender, CommandArguments arguments) {
    ControlPointData info = new ControlPointData();
    try {
      Location loc = sender.getLocation();
      SectorImpl s = sender.getWar().getSectorManager().getFromLocation(loc);
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
        sender.sendFormatMessage(Language.POINT_CREATE_RADIUS, info.radius, 3);
        return false;
      }
      info.maxPower = info.power = arguments.asInt(2);
      if (info.maxPower < 1) {
        sender.sendFormatMessage(Language.POINT_CREATE_POWER, info.power, 2);
        return false;
      }

      sender.getWar().getControlPoints().create(info);
      sendSuccessMessage(sender, info);
      return true;
    } catch (ControlPointExistsException e) {
      sender.sendFormatMessage(Language.POINT_CREATE_EXISTS);
    }
    return false;
  }

  private void sendSuccessMessage(WarPlayerImpl player, ControlPointData info) {
    String sectorName = player.getWar().getSectorManager().getByID(info.sectorID)
        .getName();
    player.sendMessage("Control point " + info.name + " created in sector "
        + sectorName);
    getPlugin().logInfo(
        player.getName() + " created controlPoint " + info.name + " in sector "
            + sectorName);
  }
}
