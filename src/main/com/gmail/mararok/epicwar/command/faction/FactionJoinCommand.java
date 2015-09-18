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
import com.gmail.mararok.epicwar.faction.internal.FactionImpl;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;

public class FactionJoinCommand extends PluginCommand {

  public FactionJoinCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "join");
    setOnlyPlayer();
    setRequiredArgumentsAmount(1);
    setDescription(Language.CD_FACTION_JOIN);
    setUsage("\\ewf join <factionName>");
  }

  @Override
  public boolean onCommandAsPlayer(WarPlayerImpl player, CommandArguments arguments) {
    String factionName = arguments.asString(0);
    if (player.hasFaction()) {
      player
          .sendMessage("You can't join to new faction, you must leave current");

    }

    if (player.getWar().getFactionManager().isExists(factionName)) {
      FactionImpl faction = player.getWar().getFactionManager().getByName(factionName);
      if (faction.isFull()) {
        player.sendMessage("This faction is full, you can't join");
      } else {
        player.join2Faction(faction);
        player.sendMessage("You joined to faction " + faction.getDisplayName());
      }
    } else {
      player.sendMessage(String.format("Faction: %s not exists", factionName));
    }

    return true;
  }

}
