/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.faction;

import com.gmail.mararok.epiccore.command.CommandArguments;
import com.gmail.mararok.epiccore.command.ParentPluginCommand;
import com.gmail.mararok.epiccore.command.PluginCommand;
import com.gmail.mararok.epiccore.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.faction.FactionManager;
import com.gmail.mararok.epicwar.faction.internal.FactionImpl;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;

public class FactionSetSpawnCommand extends PluginCommand {

  public FactionSetSpawnCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "setspawn", true);
    setOnlyPlayer();
    setRequiredArgumentsAmount(1);
    setDescription(Language.CD_FACTION_SETSPAWN);
    setUsage("\\ewf setspawn <factionName>");
  }

  public boolean onCommandAsAdmin(WarPlayerImpl admin, CommandArguments arguments) {
    String factionName = arguments.asString(0);
    FactionManager factions = admin.getWar().getFactionManager();
    if (factions.isExists(factionName)) {
      FactionImpl faction = factions.getByName(factionName);
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

  private boolean isInFactionCapitalSector(WarPlayerImpl admin, FactionImpl faction) {
    return admin.getWar().getSectorManager().getFromLocation(admin.getLocation())
        .getID() == faction.getInfo().capitalSectorID;
  }

}
