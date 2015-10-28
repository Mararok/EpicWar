/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.sector;

import com.gmail.mararok.epiccore.command.CommandArguments;
import com.gmail.mararok.epiccore.command.ParentPluginCommand;
import com.gmail.mararok.epiccore.command.PluginCommand;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.control.impl.SectorImpl;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;

public class SectorInfoCommand extends PluginCommand {

  public SectorInfoCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "info");
    setOnlyPlayer();
    setDescription(Language.CD_SECTOR_INFO);
    setUsage("\\ews info");
  }

  @Override
  public boolean onCommandAsPlayer(WarPlayerImpl player, CommandArguments arguments) {
    if (player.hasFaction()) {
      player.sendMessage("You are at " + player.getSector().getName()
          + " sector");
    } else {
      SectorImpl sector = player.getWar().getSectorManager()
          .getFromLocation(player.getLocation());
      player.sendMessage("You are at " + sector.getName() + " sector");
    }

    return true;
  }

}
