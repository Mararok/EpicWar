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
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.player.WarPlayer;

public class FactionInfoCommand extends EpicWarCommand {

  public FactionInfoCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("info")
        .description(plugin.getLanguage().getText("command.faction.info"))
        .usage("\\ewf info"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (arguments.hasAny()) {
      String shortcut = arguments.get(0);
      Faction faction = war.getFactionManager().findByShortcut(shortcut);
      if (faction != null) {
        sendFactionInfoToPlayer(faction, sender);
      } else {
        sender.sendMessage(getPlugin().getLanguage().getFormatedText("message.faction.notExists", shortcut));
      }

    } else {
      return super.onCommandOnWarWorld(war, sender, arguments);
    }

    return true;

  }

  @Override
  protected boolean onCommandAsWarPlayer(WarPlayer sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    sendFactionInfoToPlayer(sender.getFaction(), sender.getNativePlayer());
    return true;
  }

  private void sendFactionInfoToPlayer(Faction faction, Player sender) {
    String[] messages = new String[3];
    int i = 0;
    messages[i++] = faction.getColor().getChatColor() + "[" + faction.getShortcut() + "]" + ChatColor.RESET;
    messages[i++] = faction.getName();
    messages[i++] = faction.getDescription();

    sender.sendMessage(messages);
  }

}
