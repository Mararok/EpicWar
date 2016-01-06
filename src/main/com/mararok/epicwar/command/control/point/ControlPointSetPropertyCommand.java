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

  private final static int ID_ARGUMENT = 0;
  private final static int PROPERTY_ARGUMENT = 1;
  private final static int VALUE_ARGUMENT = 2;

  public ControlPointSetPropertyCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("set")
        .description(getLanguage().getText("command.controlPoint.set"))
        .usage("\\ewcp set <id> <property> <value>")
        .permission("epicwar.controlPoint.set")
        .requiredArguments(3));

  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkIsEditableWar(sender, war)) {
      if (!arguments.isNumber(ID_ARGUMENT)) {
        return false;
      }

      int controlPointId = arguments.asInt(ID_ARGUMENT);
      ControlPoint controlPoint = war.getControlPointManager().findById(controlPointId);
      if (controlPoint != null) {
        String propertyName = arguments.get(PROPERTY_ARGUMENT).toLowerCase();
        switch (propertyName) {
        case "name":
          controlPoint.setName(arguments.join(VALUE_ARGUMENT, " "));
          break;
        case "description":
          controlPoint.setDescription(arguments.join(VALUE_ARGUMENT, " "));
          break;
        case "radius":
          if (arguments.isNumber(VALUE_ARGUMENT)) {
            controlPoint.setRadius(Math.abs(arguments.asInt(VALUE_ARGUMENT)));
          } else {
            sender.sendMessage(getLanguage().getText("message.controlPoint.error.radius"));
          }
          break;
        case "maxpower":
          if (arguments.isNumber(VALUE_ARGUMENT)) {
            controlPoint.getPower().setMax(Math.abs(arguments.asInt(VALUE_ARGUMENT)));
          } else {
            sender.sendMessage(getLanguage().getText("message.controlPoint.error.maxPower"));
          }
          break;
        case "sectorid":
          if (arguments.isNumber(VALUE_ARGUMENT)) {
            Sector sector = war.getSectorManager().findById(arguments.asInt(VALUE_ARGUMENT));
            if (sector != null) {
              controlPoint.setSector(sector);
            } else {
              sender.sendMessage(getLanguage().getFormatedText("messsage.sector.notExists", arguments.asInt(VALUE_ARGUMENT)));
            }
          } else {
            sender.sendMessage(getLanguage().getText("message.controlPoint.error.sectorId"));
          }
          break;
        default:
          sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.set.propertiesList", "name, description, radius, maxpower, sectorid"));
        }

        war.getControlPointManager().update(controlPoint);
      } else {
        sender.sendMessage(getLanguage().getFormatedText("message.controlPoint.notExists", controlPointId));
      }
    }

    return true;
  }

}
