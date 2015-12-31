/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.sector;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.Sector;

public class SectorDeleteCommand extends EpicWarCommand {

  public SectorDeleteCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("delete")
        .description(getLanguage().getText("command.sector.delete"))
        .usage("\\ews delete <id>")
        .permission("epicwar.sector.delete")
        .requiredArguments(1));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkIsEditableWar(sender, war)) {
      if (!arguments.isNumber(0)) {
        return false;
      }

      int sectorId = arguments.asInt(0);
      Sector sector = war.getSectorManager().findById(sectorId);
      if (sector != null) {
        war.getSectorManager().delete(sector);
      }

    }

    return true;
  }

}
