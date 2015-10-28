/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.gmail.mararok.epiccore.command.CommandArguments;
import com.gmail.mararok.epiccore.command.ParentPluginCommand;
import com.gmail.mararok.epiccore.command.PluginCommand;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.control.impl.SectorImpl;
import com.gmail.mararok.epicwar.faction.FactionData;
import com.gmail.mararok.epicwar.faction.FactionManager;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;
import com.sun.javafx.css.converters.ColorConverter;

public class FactionCreateCommand extends PluginCommand {
  public FactionCreateCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "create", true);
    setOnlyPlayer();
    setRequiredArgumentsAmount(2);
    setDescription(Language.CD_FACTION_CREATE);
    setUsage("\\ewf create <name> <colorName>");
  }

  @Override
  public boolean onCommandAsAdmin(WarPlayerImpl admin, CommandArguments arguments) {
    FactionManager factions = admin.getWar().getFactionManager();
    String factionName = arguments.asString(0);

    if (factions.isExists(factionName)) {
      admin.sendFormatMessage(Language.FACTION_EXISTS, factionName);
      return false;
    }

    String colorName = arguments.asString(1).toUpperCase();
    ChatColor color = ChatColor.valueOf(colorName);
    if (color != null && !ColorConverter.isSupported(color)
        && factions.isColorUsed(color)) {
      admin.sendFormatMessage(Language.FACTION_COLOR_NOT_SUPPORTED_OR_USED,
          colorName);
      return false;
    }

    SectorImpl capitalSector = admin.getWar().getSectorManager()
        .getFromLocation(admin.getLocation());
    if (capitalSector.getID() == 0) {
      admin.sendMessage(Language.FACTION_CANT_CREATE_ON_WILD);
      return false;
    }

    if (factions.isCapital(capitalSector)) {
      admin.sendMessage(Language.FACTION_CANT_CREATE_ON_OTHER_CAPITAL);
      return false;
    }

    FactionData info = new FactionData();
    info.name = factionName;
    info.color = color;

    Location spawnLoc = admin.getLocation();
    info.spawnX = spawnLoc.getBlockX();
    info.spawnY = spawnLoc.getBlockY();
    info.spawnZ = spawnLoc.getBlockZ();

    info.capitalSectorID = capitalSector.getID();

    factions.create(info);

    sendSuccessMessage(admin, info);
    return true;
  }

  private void sendSuccessMessage(WarPlayerImpl player, FactionData info) {
    player.sendFormatMessage(Language.FACTION_CREATED, info.color + info.name);
    getPlugin().logInfo(
        player.getName() + " created faction " + info.color + info.name);
  }

}
