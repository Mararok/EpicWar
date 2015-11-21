/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.command.ParentPluginCommand;
import com.mararok.epicwar.EpicWarPlugin;

public class FactionCommands extends ParentPluginCommand<EpicWarPlugin> {
  public FactionCommands(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("ew").description(plugin.getLanguage().getText("command.faction.all")).usage("\\ewf [subcommand]"));

    addCommand(new FactionJoinCommand(plugin));
    addCommand(new FactionListCommand(plugin));
    addCommand(new FactionInfoCommand(plugin));
    addCommand(new FactionColorsCommand(plugin));
    addCommand(new FactionCreateCommand(plugin));
    addCommand(new FactionSpawnCommand(plugin));
    addCommand(new FactionSetSpawnCommand(plugin));
  }
}
