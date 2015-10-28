/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.epiccore.command.ParentPluginCommand;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;

public class FactionCommands extends ParentPluginCommand {
  public FactionCommands(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "ewf");
    setDescription(Language.CD_FACTION);
    setUsage("\\ewf [subcommand]");

    addCommand(new FactionJoinCommand(getPlugin(), this));
    addCommand(new FactionLeaveCommand(getPlugin(), this));
    addCommand(new FactionListCommand(getPlugin(), this));
    addCommand(new FactionInfoCommand(getPlugin(), this));
    addCommand(new FactionColorsCommand(getPlugin(), this));
    addCommand(new FactionCreateCommand(getPlugin(), this));
    addCommand(new FactionSpawnCommand(getPlugin(), this));
    addCommand(new FactionSetSpawnCommand(getPlugin(), this));
  }
}
