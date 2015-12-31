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
import com.mararok.epicwar.control.ControlAreaPower;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.ControlPointData;

public class ControlPointCreateCommand extends EpicWarCommand {

  public ControlPointCreateCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("create")
        .description(getLanguage().getText("command.controlPoint.create"))
        .usage("\\ewcp create <radius in blocks> <power> <sectorId> <name>")
        .permission("epicwar.controlPoint.create")
        .requiredArguments(4));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkIsEditableWar(sender, war)) {
      if (!arguments.isNumber(0)) {
        sender.sendMessage(getLanguage().getText("message.controlPoint.create.error.radius"));
        return false;
      }

      if (!arguments.isNumber(1)) {
        sender.sendMessage(getLanguage().getText("message.controlPoint.create.error.power"));
        return false;
      }

      ControlPointData data = new ControlPointData();
      data.radius = arguments.asInt(0);
      data.power = new ControlAreaPower(arguments.asInt(1));
      data.name = arguments.join(2, " ");

      ControlPoint controlPoint = war.getControlPointManager().create(data);

      sendSuccessMessage(sender, controlPoint);
    }

    return true;
  }

  private void sendSuccessMessage(Player sender, ControlPoint controlPoint) {
    sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.created", controlPoint.getName()));
    getPlugin().getLogger().info(sender.getName() + " created control point " + controlPoint.getName());
  }
}
