/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.subsector;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.ControlPoint;

public class SubsectorAssignCommand extends EpicWarCommand {

  public SubsectorAssignCommand(EpicWarPlugin plugin) {
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

        Chunk centerChunk = sender.getLocation().getChunk();
        int radius = arguments.asInt(1);
        war.getSubsectorMap().assignToControlPointInRadius(centerChunk.getX(), centerChunk.getZ(), radius, controlPoint);
      }
    }

    return true;
  }

}
