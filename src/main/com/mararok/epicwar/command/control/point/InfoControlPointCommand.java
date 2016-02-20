/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.point;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.misc.MessageBuilder;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.player.WarPlayer;

public class InfoControlPointCommand extends EpicWarCommand {

  public InfoControlPointCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("info")
        .description(getLanguage().getText("command.controlPoint.info"))
        .usage("/ewcp info [id]"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (arguments.hasAny()) {
      if (!arguments.isNumber(0)) {
        return false;
      }

      int controlPointId = arguments.asInt(0);
      sendInfo(sender, war.getControlPointManager().findById(controlPointId));

      return true;
    } else {
      return super.onCommandOnWarWorld(war, sender, arguments);
    }

  }

  @Override
  protected boolean onCommandAsWarPlayer(WarPlayer sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    sendInfo(sender.getNativePlayer(), sender.getControlPoint());
    return true;
  }

  private void sendInfo(Player sender, ControlPoint controlPoint) {
    if (controlPoint != null) {
      sender.sendMessage(MessageBuilder.message()
          .line("[" + controlPoint.getId() + "] " + controlPoint.getPosition() + controlPoint.getName())
          .line(controlPoint.getDescription())
          .line(controlPoint.getCurrentPower() + "/" + controlPoint.getMaxPower())
          .toArray());
    }
  }

}
