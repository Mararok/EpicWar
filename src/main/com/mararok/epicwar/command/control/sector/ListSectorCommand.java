/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2016 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.sector;

import java.util.Collection;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.misc.MessageBuilder;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.Sector;

public class ListSectorCommand extends EpicWarCommand {

  public ListSectorCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("list")
        .description(plugin.getLanguage().getText("command.sector.list"))
        .usage("/ews list"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    Collection<Sector> sectors = war.getSectorManager().findAll();
    if (!sectors.isEmpty()) {
      MessageBuilder message = MessageBuilder.message().line(getLanguage().getText("message.sector.list.header"));
      for (Sector sector : sectors) {
        message.line(getLanguage().getFormatedText("message.sector.list.row", sector.getId(), sector.getName(), sector.getOwner().getName()));
      }
      sender.sendMessage(message.toArray());
    } else {
      sender.sendMessage(getLanguage().getText("message.sector.list.empty"));
    }

    return true;
  }

}
