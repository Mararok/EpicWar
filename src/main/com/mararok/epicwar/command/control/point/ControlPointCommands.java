/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.point;

import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.command.ParentPluginCommand;
import com.mararok.epicwar.EpicWarPlugin;

public class ControlPointCommands extends ParentPluginCommand<EpicWarPlugin> {

  public ControlPointCommands(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("ewcp")
        .description(plugin.getLanguage().getText("command.controlPoint.all"))
        .usage("\\ewcp [subcommand]"));

    addCommand(new ControlPointCreateCommand(plugin));
    addCommand(new ControlPointDeleteCommand(plugin));
    addCommand(new ControlPointInfoCommand(plugin));
    addCommand(new ControlPointSetPropertyCommand(plugin));
  }

}
