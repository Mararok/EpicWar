/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command;

import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.command.ParentPluginCommand;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.command.control.point.ControlPointCommands;
import com.mararok.epicwar.command.control.sector.SectorCommands;
import com.mararok.epicwar.command.control.subsector.SubsectorCommands;
import com.mararok.epicwar.command.faction.FactionCommands;

public class Commands extends ParentPluginCommand<EpicWarPlugin> {
  public Commands(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("ew")
        .description(plugin.getLanguage().getText("command.all"))
        .usage("\\ew [subcommand]"));

    addCommand(new WarListCommand(plugin));
  }

  public void register() {
    EpicWarPlugin plugin = getPlugin();
    plugin.getCommand("ew").setExecutor(this);

    plugin.getCommand("ewf").setExecutor(new FactionCommands(plugin));
    plugin.getCommand("ews").setExecutor(new SectorCommands(plugin));
    plugin.getCommand("ewcp").setExecutor(new ControlPointCommands(plugin));
    plugin.getCommand("ewss").setExecutor(new SubsectorCommands(plugin));
  }
}
