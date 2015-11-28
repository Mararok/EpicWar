/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.ParentPluginCommand;
import com.mararok.epiccore.command.PluginCommand;
import com.mararok.epiccore.language.Language;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.internal.WarImpl;

public class WarListCommand extends PluginCommand {

  public WarListCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
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
      WarImpl war = getPlugin().getWarManager().getByWorld(world);
      messages[i] = world.getName() + " - "
          + ((war != null) ? war.getName() : "none");
      ++i;
    }
    sender.sendMessage(messages);
    return true;
  }
}
