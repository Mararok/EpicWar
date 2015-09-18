/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.bukkit.util.command.CommandArguments;
import com.gmail.mararok.bukkit.util.command.ParentPluginCommand;
import com.gmail.mararok.bukkit.util.command.PluginCommand;
import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;

public class FactionSpawnCommand extends PluginCommand {

  public FactionSpawnCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "spawn");
    setOnlyPlayer();
    setDescription(Language.CD_FACTION_SPAWN);
    setUsage("\\ewf spawn");
  }

  @Override
  public boolean onCommandAsPlayer(WarPlayerImpl player, CommandArguments arguments) {
    player.teleport2FactionCaptital();
    return true;
  }

}
