/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.sector;

import com.gmail.mararok.epiccore.command.ParentPluginCommand;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;

public class SectorCommands extends ParentPluginCommand {

  public SectorCommands(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "ews");

    setOnlyPlayer();
    setDescription(Language.CD_SECTOR);
    setUsage("\\ews [subcommand]");

    addCommand(new SectorGenCommand(getPlugin(), this));
    addCommand(new SectorSetMapCommand(getPlugin(), this));
    addCommand(new SectorInfoCommand(getPlugin(), this));
  }
}
