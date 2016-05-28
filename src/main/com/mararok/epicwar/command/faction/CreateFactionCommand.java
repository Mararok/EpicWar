/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.math.Vector3i;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.faction.FactionData;
import com.mararok.epicwar.faction.FactionManager;

public class CreateFactionCommand extends EpicWarCommand {
  public CreateFactionCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("create")
        .description(plugin.getLanguage().getText("command.faction.create"))
        .usage("/ewf create <name> <shortcut> <colorName>")
        .permission("epicwar.faction.create")
        .requiredArguments(3));

  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    String factionName = arguments.get(0);
    String shortcut = arguments.get(1);
    String colorName = arguments.get(2);

    FactionManager factions = war.getFactionManager();
    if (factions.findByName(factionName) != null) {
      sender.sendMessage(getPlugin().getLanguage().getFormatedText("message.faction.exists", factionName));
      return true;
    }

    if (factions.findByShortcut(shortcut) != null) {
      sender.sendMessage(getPlugin().getLanguage().getFormatedText("message.faction.exists", factionName));
      return true;
    }

    Faction.Color factionColor = Faction.Color.getByName(colorName);
    if (factionColor == null || factions.findByColor(factionColor) != null) {
      sender.sendMessage(getPlugin().getLanguage().getFormatedText("message.faction.colorNotSupportedOrUsed", colorName));
      return true;
    }

    FactionData data = new FactionData();
    data.name = factionName;
    data.shortcut = shortcut;
    data.color = factionColor;

    Location spawnLoc = sender.getLocation();
    data.spawnPosition = new Vector3i(spawnLoc.getBlockX(), spawnLoc.getBlockY(), spawnLoc.getBlockZ());

    factions.create(data);
    String message = getPlugin().getLanguage().getFormatedText("message.faction.created", factionName, factionColor.getChatColor());
    sender.sendMessage(message);
    getPlugin().getLogger().info(message);

    return true;
  }

}
