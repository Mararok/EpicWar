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

    setMetadata(CommandMetadata.command("ew")
        .description(plugin.getLanguage().getText("command.faction.all"))
        .usage("/ewf [subcommand]"));

    addCommand(new JoinFactionCommand(plugin));
    addCommand(new ListFactionCommand(plugin));
    addCommand(new InfoFactionCommand(plugin));
    addCommand(new ColorsFactionCommand(plugin));
    addCommand(new CreateFactionCommand(plugin));
    addCommand(new SpawnFactionCommand(plugin));
    addCommand(new SetSpawnFactionCommand(plugin));
  }
}
