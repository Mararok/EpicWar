/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.subsector;

import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.command.ParentPluginCommand;
import com.mararok.epicwar.EpicWarPlugin;

public class SubsectorCommands extends ParentPluginCommand<EpicWarPlugin> {

  public SubsectorCommands(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("ewss")
        .description(plugin.getLanguage().getText("command.subsector.all"))
        .usage("\\ewss [subcommand]"));

    addCommand(new AssignSubsectorCommand(plugin));
    addCommand(new DeleteSubsectorCommand(plugin));

    addCommand(new InfoSubsectorCommand(plugin));
  }

}
