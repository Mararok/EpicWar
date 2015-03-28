/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.war;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.command.CommandArguments;
import com.gmail.mararok.epicwar.command.PluginParentCommand;
import com.gmail.mararok.epicwar.command.PluginCommand;

public class WarListCommand extends PluginCommand {

  public WarListCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
    super(plugin, parent, "list");
    setDescription(Language.CD_WAR_LIST);
    setUsage("\\eww list");
  }

  @Override
  public boolean onCommandAsConsole(CommandSender sender,
      CommandArguments arguments) {

    List<World> worlds = getPlugin().getServer().getWorlds();
    String[] messages = new String[worlds.size() + 1];
    messages[0] = "World - WarName or none";
    int i = 0;
    for (World world : worlds) {
      War war = getPlugin().getWars().getByWorld(world);
      messages[i] = world.getName() + " - "
          + ((war != null) ? war.getName() : "none");
      ++i;
    }
    sender.sendMessage(messages);
    return true;
  }
}
