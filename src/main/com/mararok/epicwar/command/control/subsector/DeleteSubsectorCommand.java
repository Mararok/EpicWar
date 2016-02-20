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
import com.mararok.epicwar.control.Subsector;

public class DeleteSubsectorCommand extends EpicWarCommand {

  public DeleteSubsectorCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("delete")
        .description(getLanguage().getText("command.sector.delete"))
        .usage("/ewss delete <id>")
        .permission("epicwar.subsector.delete")
        .requiredArguments(1));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkIsEditableWar(sender, war)) {
      if (!arguments.isNumber(0) || !arguments.isNumber(1)) {
        return false;
      }

      int chunkX = arguments.asInt(0);
      int chunkZ = arguments.asInt(1);
      Subsector subsector = war.getSubsectorMap().get(chunkX, chunkZ);
      if (subsector != null) {
        war.getSubsectorMap().delete(subsector);
      }
    }

    return true;
  }

}
