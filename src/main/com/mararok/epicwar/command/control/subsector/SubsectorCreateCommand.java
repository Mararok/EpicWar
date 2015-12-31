/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.subsector;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.Subsector;

public class SubsectorCreateCommand extends EpicWarCommand {

  public SubsectorCreateCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("create")
        .description(getLanguage().getText("command.subsector.create"))
        .usage("\\ewss create <controlPointId>")
        .permission("epicwar.subsector.create")
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
      if (controlPoint != null) {
        Subsector subsector = war.getSubsectorMap().assignToControlPoint(sender.getLocation().getChunk(), controlPoint);
        sender.sendMessage(getLanguage().getFormatedText("message.subsector.assignedToControlPoint", subsector.getId(), subsector.getChunkX(), subsector.getChunkZ()));
      }

    }
    return true;

  }

}
