/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command;

import java.io.IOException;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.ParentPluginCommand;
import com.mararok.epiccore.command.PluginCommand;
import com.mararok.epiccore.language.Language;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.internal.WarImpl;
import com.mararok.epicwar.player.internal.WarPlayerImpl;

public class DumpCommand extends PluginCommand {

  public DumpCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "dump", true);
    setOnlyPlayer();
    setRequiredArgumentsAmount(1);
    setDescription(Language.CD_ALL_DUMP);
    setUsage("\\ew dump [players|factions|sectors|points]");
  }

  @Override
  public boolean onCommandAsAdmin(WarPlayerImpl sender, CommandArguments arguments) {
    String dumpType = arguments.asString(0);
    WarImpl currentWar = sender.getWar();
    try {
      switch (dumpType) {
      case "players":
        currentWar.getPlayerManager().dump2File();
        break;
      case "factions":
        currentWar.getFactionManager().dump2File();
        break;
      case "sectors":
        currentWar.getSectorManager().dump2File();
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
