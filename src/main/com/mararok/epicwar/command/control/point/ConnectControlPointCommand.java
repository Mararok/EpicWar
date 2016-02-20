/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2016 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.point;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.ControlPoint;

public class ConnectControlPointCommand extends EpicWarCommand {
  private final static int CONTROLPOINTID_ARGUMENT = 0;
  private final static int OTHER_CONTROLPOINTID_ARGUMENT = 1;

  public ConnectControlPointCommand(EpicWarPlugin plugin) {
    super(plugin);
    setMetadata(CommandMetadata.command("connect")
        .description(getLanguage().getText("command.controlPoint.connect"))
        .usage("/ewcp connect <controlPointId> <otherControlPointId>")
        .permission("epicwar.controlPoint.connect")
        .requiredArguments(2));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkIsEditableWar(sender, war)) {

      if (!arguments.isNumber(CONTROLPOINTID_ARGUMENT) && !arguments.isNumber(OTHER_CONTROLPOINTID_ARGUMENT)) {
        return false;
      }

      int controlPointId = arguments.asInt(CONTROLPOINTID_ARGUMENT);
      int otherControlPointId = arguments.asInt(OTHER_CONTROLPOINTID_ARGUMENT);

      if (controlPointId == otherControlPointId) {
        sender.sendMessage(getLanguage().getText("message.controlPoint.connect.error.idEquals"));
        return false;
      }

      ControlPoint controlPoint = war.getControlPointManager().findById(controlPointId);
      if (controlPoint == null) {
        sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.notExists", controlPointId));
        return false;
      }

      ControlPoint otherControlPoint = war.getControlPointManager().findById(otherControlPointId);
      if (otherControlPoint == null) {
        sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.notExists", otherControlPointId));
        return false;
      }

      controlPoint.connectTo(otherControlPoint);
      sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.connect.success", controlPoint.getName(), otherControlPoint.getName()));

    }

    return true;
  }

}
