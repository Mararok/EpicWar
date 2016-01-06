/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command;

import java.util.Collection;

import org.bukkit.command.CommandSender;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.misc.MessageBuilder;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;

public class WarListCommand extends EpicWarCommand {

  public WarListCommand(EpicWarPlugin plugin) {
    super(plugin);
    setMetadata(CommandMetadata.command("list")
        .description(getLanguage().getText("command.war.list"))
        .usage("\\ew list")
        .permission("epicwar.list"));
  }

  @Override
  public boolean onCommand(CommandSender sender, CommandArguments<EpicWarPlugin> arguments) {
    if (checkPermission(sender)) {
      Collection<War> wars = getPlugin().getWarManager().findAll();
      if (!wars.isEmpty()) {
        MessageBuilder message = MessageBuilder.message().line(getLanguage().getText("message.war.list.header"));
        for (War war : wars) {
          message.line(getLanguage().getFormatedText("message.war.list.row", war.getId(), war.getSettings().name, war.getWorld().getName()));
        }
        sender.sendMessage(message.toArray());
      } else {
        sender.sendMessage(getLanguage().getText("message.war.list.empty"));
      }

    }

    return true;
  }
}
