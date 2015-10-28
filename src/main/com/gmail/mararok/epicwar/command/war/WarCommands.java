/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.war;

import com.gmail.mararok.epiccore.command.ParentPluginCommand;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;

public class WarCommands extends ParentPluginCommand {

  public WarCommands(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "War");
    setDescription(Language.CD_WAR);
    setUsage("\\eww [subcommand]");
    addCommand(new WarListCommand(getPlugin(), this));
  }

}
