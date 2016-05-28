/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.player.WarPlayer;

public class JoinFactionCommand extends EpicWarCommand {

  public JoinFactionCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("join")
        .description(plugin.getLanguage().getText("command.faction.join"))
        .usage("/ewf join <factionShortcut>")
        .requiredArguments(1));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (war.getPlayerManager().findByPlayer(sender) != null) {
      sender.sendMessage(getPlugin().getLanguage().getText("message.faction.joinWhenInFaction"));
    } else {
      String factionShortcut = arguments.get(0);
      Faction faction = war.getFactionManager().findByShortcut(factionShortcut);
      if (faction != null) {
        WarPlayer warPlayer = war.getPlayerManager().register(sender, faction);
        if (warPlayer != null) {
          sender.sendMessage(getPlugin().getLanguage().getFormatedText("message.faction.joined", faction.getName()));
        }
      } else {
        sender.sendMessage(getPlugin().getLanguage().getFormatedText("message.faction.notExists", factionShortcut));
      }
    }

    return true;
  }

}
