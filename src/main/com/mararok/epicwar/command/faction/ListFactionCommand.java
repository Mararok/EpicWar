/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.faction.Faction;

public class ListFactionCommand extends EpicWarCommand {

  public ListFactionCommand(EpicWarPlugin plugin) {
    super(plugin);
    setMetadata(CommandMetadata.command("list")
        .description(plugin.getLanguage().getText("command.faction.list"))
        .usage("/ewf list"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    Collection<Faction> factions = war.getFactionManager().findAll();
    String[] messages = new String[factions.size() + 1];
    messages[0] = getPlugin().getLanguage().getText("message.faction.list");
    int i = 1;
    for (Faction faction : factions) {
      messages[i++] = faction.getColor().getChatColor() + "[" + faction.getShortcut() + "] " + ChatColor.RESET + faction.getName();
    }

    sender.sendMessage(messages);
    return true;
  }

}
