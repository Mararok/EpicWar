/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.command.CommandArguments;
import com.gmail.mararok.epicwar.command.PluginParentCommand;
import com.gmail.mararok.epicwar.command.PluginCommand;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.faction.FactionManager;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class FactionSetSpawnCommand extends PluginCommand {

  public FactionSetSpawnCommand(EpicWarPlugin plugin, PluginParentCommand parent) {
    super(plugin, parent, "setspawn", true);
    setOnlyPlayer();
    setRequiredArgumentsAmount(1);
    setDescription(Language.CD_FACTION_SETSPAWN);
    setUsage("\\ewf setspawn <factionName>");
  }

  public boolean onCommandAsAdmin(WarPlayer admin, CommandArguments arguments) {
    String factionName = arguments.asString(0);
    FactionManager factions = admin.getWar().getFactions();
    if (factions.isExists(factionName)) {
      Faction faction = factions.getByName(factionName);
      if (isInFactionCapitalSector(admin, faction)) {
        faction.setSpawnLocation(admin.getLocation());
        admin.sendMessage("You sets new faction spawn");
      } else {
        admin
            .sendMessage("To set faction spawn location you must be in faction capital");
      }
    } else {
      admin.sendMessage("Faction " + factionName + " not exists");
    }

    return true;
  }

  private boolean isInFactionCapitalSector(WarPlayer admin, Faction faction) {
    return admin.getWar().getSectors().getFromLocation(admin.getLocation())
        .getID() == faction.getInfo().capitalSectorID;
  }

}
