/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command;

import org.bukkit.entity.Player;

import com.gmail.mararok.epiccore.command.ChildPluginCommand;
import com.gmail.mararok.epiccore.command.CommandArguments;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.player.WarPlayer;

public abstract class EpicWarCommand extends ChildPluginCommand<EpicWarPlugin> {

  public EpicWarCommand(EpicWarPlugin plugin) {
    super(plugin);
  }

  @Override
  protected boolean onCommandAsPlayer(Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    War war = getPlugin().getWarManager().findByPlayer(sender);
    if (war != null) {
      return onCommandOnWarWorld(war, sender, arguments);
    } else {
      sender.sendMessage(getPlugin().getLanguage().getText("message.command.onlyOnWarWorld"));
    }

    return false;
  }

  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    WarPlayer warPlayer = war.getPlayerManager().findByPlayer(sender);
    if (warPlayer != null) {
      return onCommandAsWarPlayer(warPlayer, arguments);
    } else {
      sender.sendMessage(getPlugin().getLanguage().getText("message.command.onlyAsWarPlayer"));
    }

    return false;
  }

  protected boolean onCommandAsWarPlayer(WarPlayer sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    return false;

  }

}
