/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.epiccore.command.CommandArguments;
import com.gmail.mararok.epiccore.command.ParentPluginCommand;
import com.gmail.mararok.epiccore.command.PluginCommand;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.faction.internal.FactionImpl;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;

public class FactionInfoCommand extends PluginCommand {

  public FactionInfoCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "info");
    setOnlyPlayer();
    setDescription(Language.CD_FACTION_INFO);
    setUsage("\\ewf info");
  }

  @Override
  public boolean onCommandAsPlayer(WarPlayerImpl player, CommandArguments arguments) {
    if (player.hasFaction()) {
      FactionImpl faction = player.getFaction();
      player.sendMessage("Your faction: " + faction.getChatColor()
          + faction.getName());
    } else {
      player.sendMessage("You haven't faction");
    }

    return true;
  }

}
