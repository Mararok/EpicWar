/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.sector;

import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.command.CommandArguments;
import com.gmail.mararok.epicwar.command.PluginParentCommand;
import com.gmail.mararok.epicwar.command.PluginCommand;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class SectorInfoCommand extends PluginCommand {

  public SectorInfoCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
    super(plugin, parent, "info");
    setOnlyPlayer();
    setDescription(Language.CD_SECTOR_INFO);
    setUsage("\\ews info");
  }

  @Override
  public boolean onCommandAsPlayer(WarPlayer player, CommandArguments arguments) {
    if (player.hasFaction()) {
      player.sendMessage("You are at " + player.getSector().getName()
          + " sector");
    } else {
      Sector sector = player.getWar().getSectors()
          .getFromLocation(player.getLocation());
      player.sendMessage("You are at " + sector.getName() + " sector");
    }

    return true;
  }

}
