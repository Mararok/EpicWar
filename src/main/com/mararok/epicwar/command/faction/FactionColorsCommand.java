/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.faction.Faction;

public class FactionColorsCommand extends EpicWarCommand {

  public FactionColorsCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("colors")
        .description(plugin.getLanguage().getText("command.faction.colors"))
        .usage("\\ewf colors"));

  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    String[] messages = new String[Faction.Color.values().length + 1];
    messages[0] = getPlugin().getLanguage().getText("message.faction.supportedColors");
    int i = 1;

    Map<Faction.Color, Faction> colors = new EnumMap<Faction.Color, Faction>(Faction.Color.class);
    for (Faction faction : war.getFactionManager().findAll()) {
      colors.put(faction.getColor(), faction);
    }

    for (Entry<Faction.Color, Faction> entry : colors.entrySet()) {
      Faction.Color color = entry.getKey();
      Faction faction = entry.getValue();
      messages[i++] = color.getChatColor() + color.name() + ChatColor.RESET + ((faction != null) ? faction.getName() : "");
    }

    sender.sendMessage(messages);
    return true;
  }
}
