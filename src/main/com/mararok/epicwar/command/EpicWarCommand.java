/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.ChildPluginCommand;
import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.player.WarPlayer;

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
