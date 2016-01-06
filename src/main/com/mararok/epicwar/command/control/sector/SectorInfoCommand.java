/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.sector;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.misc.MessageBuilder;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.player.WarPlayer;

public class SectorInfoCommand extends EpicWarCommand {

  public SectorInfoCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("info")
        .description(plugin.getLanguage().getText("command.sector.info"))
        .usage("\\ews info [sectorId]"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (arguments.hasAny()) {
      if (!arguments.isNumber(0)) {
        return false;
      }

      int sectorId = arguments.asInt(0);
      sendInfo(sender, war.getSectorManager().findById(sectorId));
      return true;
    } else {
      return super.onCommandOnWarWorld(war, sender, arguments);
    }
  }

  @Override
  protected boolean onCommandAsWarPlayer(WarPlayer sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    sendInfo(sender.getNativePlayer(), sender.getSector());
    return true;
  }

  private void sendInfo(Player sender, Sector sector) {
    if (sector != null) {
      sender.sendMessage(MessageBuilder.message()
          .line("[" + sector.getId() + "] " + sector.getName())
          .line(sector.getDescription())
          .toArray());
    }
  }

}
