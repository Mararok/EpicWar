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
      if (!arguments.isNumber(0)) {
        return false;
      }

      int controlPointId = arguments.asInt(0);
      ControlPoint controlPoint = war.getControlPointManager().findById(controlPointId);

      if (arguments.isExists(1)) {
        if (!arguments.isNumber(1)) {
          return false;
        }

        int radius = arguments.asInt(1);
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
