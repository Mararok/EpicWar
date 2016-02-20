/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2016 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.point;

import java.util.Collection;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.misc.MessageBuilder;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.ControlPoint;

public class ListControlPointCommand extends EpicWarCommand {

  public ListControlPointCommand(EpicWarPlugin plugin) {
    super(plugin);
    setMetadata(CommandMetadata.command("list")
        .description(getLanguage().getText("command.controlPoint.list"))
        .usage("/ewcp list"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    Collection<ControlPoint> controlPoints = war.getControlPointManager().findAll();
    if (!controlPoints.isEmpty()) {
      MessageBuilder message = MessageBuilder.message().line(getLanguage().getText("message.controlPoint.list.header"));
      for (ControlPoint controlPoint : controlPoints) {
        message.line(getLanguage().getFormatedText("message.controlPoint.list.row", controlPoint.getId(), controlPoint.getName(), controlPoint.getOwner().getName()));
      }
      sender.sendMessage(message.toArray());
    } else {
      sender.sendMessage(getLanguage().getText("message.controlPoint.list.empty"));
    }

    return true;
  }
}
