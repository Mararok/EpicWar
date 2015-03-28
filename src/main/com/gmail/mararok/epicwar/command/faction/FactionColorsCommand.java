/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.command.CommandArguments;
import com.gmail.mararok.epicwar.command.PluginParentCommand;
import com.gmail.mararok.epicwar.command.PluginCommand;
import com.gmail.mararok.epicwar.util.ColorConverter;

public class FactionColorsCommand extends PluginCommand {

  public FactionColorsCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
    super(plugin, parent, "colors");

    setDescription(Language.CD_FACTION_COLORS);
    setUsage("\\ewf colors");
  }

  @Override
  public boolean onCommandAsConsole(CommandSender sender,
      CommandArguments arguments) {

    List<ChatColor> colors = ColorConverter.getColorsList();
    sender.sendMessage("SupportedColors: ");
    for (ChatColor color : colors) {
      sender.sendMessage(color + color.name());
    }
    return true;
  }

}
