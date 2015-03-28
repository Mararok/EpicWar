/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command;

import java.io.IOException;

import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class DumpCommand extends PluginCommand {

  public DumpCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
    super(plugin, parent, "dump", true);
    setOnlyPlayer();
    setRequiredArgumentsAmount(1);
    setDescription(Language.CD_ALL_DUMP);
    setUsage("\\ew dump [players|factions|sectors|points]");
  }

  @Override
  public boolean onCommandAsAdmin(WarPlayer sender, CommandArguments arguments) {
    String dumpType = arguments.asString(0);
    War currentWar = sender.getWar();
    try {
      switch (dumpType) {
      case "players":
        currentWar.getPlayers().dump2File();
        break;
      case "factions":
        currentWar.getFactions().dump2File();
        break;
      case "sectors":
        currentWar.getSectors().dump2File();
        break;
      case "points":
        currentWar.getControlPoints().dump2File();
        break;
      }
      sender.sendMessage("You dumped data of " + dumpType);
      return true;
    } catch (IOException e) {
      getPlugin().logException(e);
    }

    return false;
  }

}
