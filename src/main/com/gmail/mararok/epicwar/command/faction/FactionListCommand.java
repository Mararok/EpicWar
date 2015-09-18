/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import java.util.List;

import com.gmail.mararok.bukkit.util.command.CommandArguments;
import com.gmail.mararok.bukkit.util.command.ParentPluginCommand;
import com.gmail.mararok.bukkit.util.command.PluginCommand;
import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.faction.internal.FactionImpl;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;

public class FactionListCommand extends PluginCommand {

  public FactionListCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "list");
    setOnlyPlayer();
    setDescription(Language.CD_FACTION_LIST);
    setUsage("\\ewf list");
  }

  @Override
  public boolean onCommandAsPlayer(WarPlayerImpl player, CommandArguments arguments) {
    List<FactionImpl> factions = player.getWar().getFactionManager().getList();
    if (factions.size() > 1) {
      int size = factions.size();
      String[] list = new String[size + 1];
      list[0] = "Factions list:";

      FactionImpl faction;
      for (int i = 1; i < size; ++i) {
        faction = factions.get(i);
        list[i] = "[" + i + "] " + faction.getInfo().color + faction.getName();
      }

      player.sendMessage(list);

    } else {
      player.sendMessage("No factions defined yet");
    }

    return true;
  }

}
