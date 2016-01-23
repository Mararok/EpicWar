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

public class SetPropertySectorCommand extends EpicWarCommand {

  public SetPropertySectorCommand(EpicWarPlugin plugin) {
    super(plugin);
    setMetadata(CommandMetadata.command("set")
        .description(getLanguage().getText("command.sector.set"))
        .usage("\\ews set <id> <propertyName> <value>")
        .permission("epicwar.sector.set")
        .requiredArguments(3));

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
        String propertyName = arguments.get(1).toLowerCase();
        switch (propertyName) {
        case "name":
          sector.setName(arguments.join(2, " "));
          break;
        case "description":
          sector.setDescription(arguments.join(2, " "));
          break;
        default:
          sender.sendMessage(getLanguage().getFormatedText("message.sector.set.propertiesList", "name, description"));
        }

        war.getSectorManager().update(sector);
      } else {
        sender.sendMessage(getLanguage().getFormatedText("message.sector.notExists", sectorId));
      }
    }

    return true;
  }

}
