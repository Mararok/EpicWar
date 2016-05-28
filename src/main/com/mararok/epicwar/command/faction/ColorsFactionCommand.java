/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.misc.MessageBuilder;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.faction.Faction;

public class ColorsFactionCommand extends EpicWarCommand {

  public ColorsFactionCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("colors")
        .description(plugin.getLanguage().getText("command.faction.colors"))
        .usage("/ewf colors"));

  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    MessageBuilder message = MessageBuilder.message().line(getLanguage().getText("message.faction.supportedColors"));
    for (Faction.Color color : Faction.Color.values()) {
      Faction faction = war.getFactionManager().findByColor(color);
      message.line(color.getChatColor() + color.name() + " " + ChatColor.RESET + ((faction != null) ? faction.getName() : ""));
    }

    sender.sendMessage(message.toArray());
    return true;
  }
}
