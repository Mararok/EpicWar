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

public class ControlPointDeleteCommand extends EpicWarCommand {

  public ControlPointDeleteCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("delete")
        .description(getLanguage().getText("command.controlPoint.delete"))
        .usage("\\ewcp delete <id>")
        .permission("epicwar.controlPoint.delete")
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
        war.getControlPointManager().delete(controlPoint);
      }

    }

    return true;
  }

}
