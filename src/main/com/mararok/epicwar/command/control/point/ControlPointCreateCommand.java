/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.point;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.ControlPointData;

public class ControlPointCreateCommand extends EpicWarCommand {
  private final static int SECTORID_ARGUMENT = 0;
  private final static int RADIUS_ARGUMENT = 1;
  private final static int MAXPOWER_ARGUMENT = 2;
  private final static int NAME_ARGUMENT = 3;

  public ControlPointCreateCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("create")
        .description(getLanguage().getText("command.controlPoint.create"))
        .usage("\\ewcp create <sectorId> <radius> <maxPower> <name>")
        .permission("epicwar.controlPoint.create")
        .requiredArguments(4));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkIsEditableWar(sender, war)) {
      if (!arguments.isNumber(SECTORID_ARGUMENT) && war.getSectorManager().findById(arguments.asInt(SECTORID_ARGUMENT)) != null) {
        sender.sendMessage(getLanguage().getText("message.controlPoint.create.error.sectorId"));
        return false;
      }

      if (!arguments.isNumber(RADIUS_ARGUMENT)) {
        sender.sendMessage(getLanguage().getText("message.controlPoint.create.error.radius"));
        return false;
      }

      if (!arguments.isNumber(MAXPOWER_ARGUMENT)) {
        sender.sendMessage(getLanguage().getText("message.controlPoint.create.error.maxPower"));
        return false;
      }

      ControlPointData data = new ControlPointData();
      data.sectorId = arguments.asInt(SECTORID_ARGUMENT);
      data.radius = arguments.asInt(RADIUS_ARGUMENT);
      data.maxPower = arguments.asInt(MAXPOWER_ARGUMENT);
      data.name = arguments.join(NAME_ARGUMENT, " ");

      ControlPoint controlPoint = war.getControlPointManager().create(data);
      sendSuccessMessage(sender, controlPoint);
    }

    return true;
  }

  private void sendSuccessMessage(Player sender, ControlPoint controlPoint) {
    sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.created", controlPoint.getName()));
  }
}
