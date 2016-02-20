/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.player.WarPlayer;

public class SpawnFactionCommand extends EpicWarCommand {

  public SpawnFactionCommand(EpicWarPlugin plugin) {
    super(plugin);
    setMetadata(CommandMetadata.command("spawn")
        .description(plugin.getLanguage().getText("command.faction.spawn"))
        .usage("/ewf spawn"));
  }

  @Override
  protected boolean onCommandAsWarPlayer(WarPlayer sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    sender.getNativePlayer().teleport(sender.getFaction().getSpawnLocation());
    return true;
  }
}
