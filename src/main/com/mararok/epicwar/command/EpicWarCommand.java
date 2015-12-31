/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mararok.epiccore.command.ChildPluginCommand;
import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.language.Language;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.player.WarPlayer;

/**
 * Base command class for all EpicWar commands
 */
public abstract class EpicWarCommand extends ChildPluginCommand<EpicWarPlugin> {

  public EpicWarCommand(EpicWarPlugin plugin) {
    super(plugin);
  }

  /**
   * Command can by only executed on war world
   */
  @Override
  protected boolean onCommandAsPlayer(Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (checkPermission(sender)) {
      War war = getPlugin().getWarManager().findByPlayerWorld(sender);
      if (war != null) {
        return onCommandOnWarWorld(war, sender, arguments);
      }
      sender.sendMessage(getLanguage().getText("message.command.onlyOnWarWorld"));
    }

    return true;
  }

  protected boolean checkPermission(CommandSender sender) {
    if (hasPermission(sender)) {
      return true;
    } else {
      sender.sendMessage(getLanguage().getFormatedText("message.noPermission", sender.getName(), getPermission()));
      return false;
    }
  }

  /**
   * Exceute command in war context
   */
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    WarPlayer warPlayer = war.getPlayerManager().findByPlayer(sender);
    if (warPlayer != null) {
      return onCommandAsWarPlayer(warPlayer, arguments);
    }

    sender.sendMessage(getPlugin().getLanguage().getText("message.command.onlyAsWarPlayer"));
    return true;
  }

  /**
   * Returns true if war is in edit mode
   * When war can't be edit sends message to player
   */
  protected boolean checkIsEditableWar(Player sender, War war) {
    if (war.getSettings().editMode) {
      return true;
    } else {
      sender.sendMessage(getLanguage().getFormatedText("message.war.notEditable", war.getSettings().name));
      return false;
    }

  }

  /**
   * Execute command in WarPlayer context
   */
  protected boolean onCommandAsWarPlayer(WarPlayer sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    return false;

  }

  protected Language getLanguage() {
    return getPlugin().getLanguage();
  }

}
