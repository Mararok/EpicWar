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
import com.mararok.epiccore.misc.MessageBuilder;
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
    MessageBuilder message = new MessageBuilder();
    message.line(getLanguage().getText("message.faction.list"));
    for (Faction faction : factions) {
      message.line(faction.getColor().getChatColor() + "[" + faction.getShortcut() + "] " + ChatColor.RESET + faction.getName());
    }

    sender.sendMessage(message.toArray());
    return true;
  }

}
