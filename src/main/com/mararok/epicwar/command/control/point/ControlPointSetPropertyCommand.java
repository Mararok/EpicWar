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
import com.mararok.epicwar.control.Sector;

public class ControlPointSetPropertyCommand extends EpicWarCommand {

  public ControlPointSetPropertyCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("set")
        .description(getLanguage().getText("command.controlPoint.set"))
        .usage("\\ewcp set <id> <propertyName> <value>")
        .permission("epicwar.controlPoint.set")
        .requiredArguments(3));

  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkIsEditableWar(sender, war)) {
      if (!arguments.isNumber(0)) {
        return false;
      }

      int controlPointId = arguments.asInt(0);
      ControlPoint controlPoint = war.getControlPointManager().findById(controlPointId);
      if (controlPoint != null) {

        String propertyName = arguments.get(1).toLowerCase();
        switch (propertyName) {
        case "name":
          controlPoint.setName(arguments.join(2, " "));
          break;
        case "description":
          controlPoint.setDescription(arguments.join(2, " "));
          break;
        case "radius":
          if (arguments.isNumber(2)) {
            controlPoint.setRadius(Math.abs(arguments.asInt(2)));
          } else {
            sender.sendMessage(getLanguage().getText("message.controlPoint.error.radius"));
          }
          break;
        case "power":
          if (arguments.isNumber(2)) {
            controlPoint.getPower().setMax(Math.abs(arguments.asInt(2)));
          } else {
            sender.sendMessage(getLanguage().getText("message.controlPoint.error.power"));
          }
          break;
        case "sectorId":
          if (arguments.isNumber(2)) {
            Sector sector = war.getSectorManager().findById(arguments.asInt(2));
            if (sector != null) {
              controlPoint.setSector(sector);
            } else {
              sender.sendMessage(getLanguage().getFormatedText("messsage.sector.notExists", arguments.asInt(2)));
            }
          } else {
            sender.sendMessage(getLanguage().getText("message.controlPoint.error.sectorId"));
          }
          break;
        default:
          sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.set.propertiesList", "name, description, radius, power, sectorId"));
        }

        war.getControlPointManager().update(controlPoint);
      } else {
        sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.notExists", controlPointId));
      }
    }

    return true;
  }

}
