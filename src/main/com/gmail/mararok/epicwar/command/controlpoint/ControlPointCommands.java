/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.controlpoint;

import com.gmail.mararok.epiccore.command.ParentPluginCommand;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;

public class ControlPointCommands extends ParentPluginCommand {

  public ControlPointCommands(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "ewp");

    setDescription(Language.CD_POINT);
    setUsage("\\ewp [subcommand]");

    addCommand(new ControlPointCreateCommand(getPlugin(), this));
  }

}
