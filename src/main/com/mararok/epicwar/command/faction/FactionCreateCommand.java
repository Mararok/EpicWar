/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.mararok.epiccore.Position3D;
import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.faction.FactionData;
import com.mararok.epicwar.faction.FactionManager;

public class FactionCreateCommand extends EpicWarCommand {
  public FactionCreateCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("create")
        .description(plugin.getLanguage().getText("command.faction.create"))
        .usage("\\ewf create <name> <colorName>")
        .permission("epicwar.faction.create")
        .requiredArgumentAmount(2));

  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    String factionName = arguments.get(0);
    String colorName = arguments.get(1);

    FactionManager factions = war.getFactionManager();
    if (factions.findByName(factionName) != null) {
      sender.sendMessage(getPlugin().getLanguage().getFormatedText("message.faction.exists", factionName));
      return true;
    }

    Faction.Color factionColor = Faction.Color.getByName(colorName);
    if (factionColor == null || factions.findByColor(factionColor) != null) {
      sender.sendMessage(getPlugin().getLanguage().getFormatedText("message.faction.colorNotSupportedOrUsed", factionColor));
      return true;
    }

    FactionData data = new FactionData();
    data.name = factionName;
    data.color = factionColor;

    Location spawnLoc = sender.getLocation();
    data.spawnPosition = new Position3D(spawnLoc.getBlockX(), spawnLoc.getBlockY(), spawnLoc.getBlockZ());

    factions.create(data);
    String message = getPlugin().getLanguage().getFormatedText("message.faction.created", factionName, factionColor.getChatColor());
    sender.sendMessage(message);
    getPlugin().getLogger().info(message);

    return true;
  }

}
