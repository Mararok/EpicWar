/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.sector;

import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.command.ParentPluginCommand;
import com.mararok.epicwar.EpicWarPlugin;

public class SectorCommands extends ParentPluginCommand<EpicWarPlugin> {

  public SectorCommands(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("ews")
        .description(plugin.getLanguage().getText("command.sector.all"))
        .usage("\\ews [subcommand]"));

    addCommand(new SectorCreateCommand(plugin));
    addCommand(new SectorDeleteCommand(plugin));
    addCommand(new SectorSetPropertyCommand(plugin));

    addCommand(new SectorInfoCommand(plugin));
  }
}
