/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.subsector;

import java.util.Collection;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.Subsector;

public class AssignSubsectorCommand extends EpicWarCommand {
  private final static int CONTROLPOINT_ARGUMENT = 0;
  private final static int RADIUS_ARGUMENT = 1;

  public AssignSubsectorCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("assign")
        .description(getLanguage().getText("command.subsector.assign"))
        .usage("\\ewss assign <controlPointId> [radius]")
        .permission("epicwar.subsector.assign")
        .requiredArguments(1));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkIsEditableWar(sender, war)) {
      if (!arguments.isNumber(CONTROLPOINT_ARGUMENT)) {
        return false;
      }

      int controlPointId = arguments.asInt(CONTROLPOINT_ARGUMENT);
      ControlPoint controlPoint = war.getControlPointManager().findById(controlPointId);
      if (controlPoint == null) {
        sender.sendMessage(getLanguage().getFormatedText("controlPoint.notExists", controlPointId));
        return true;
      }

      if (arguments.isExists(RADIUS_ARGUMENT)) {
        if (!arguments.isNumber(RADIUS_ARGUMENT)) {
          return false;
        }

        int radius = arguments.asInt(RADIUS_ARGUMENT);
        Collection<Subsector> subsectors = war.getSubsectorMap().assignToControlPointInRadius(sender.getLocation().getChunk(), radius, controlPoint);
        for (Subsector subsector : subsectors) {
          sendAssignedMessage(sender, subsector);
        }
      } else {
        Subsector subsector = war.getSubsectorMap().assignToControlPoint(sender.getLocation().getChunk(), controlPoint);
        sendAssignedMessage(sender, subsector);
      }
    }

    return true;
  }

  private void sendAssignedMessage(Player sender, Subsector subsector) {
    sender.sendMessage(getLanguage().getFormatedText("message.subsector.assignedToControlPoint", subsector.getId(), subsector.getChunkX(), subsector.getChunkZ()));
  }

}
