/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.faction;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.faction.Faction;

public class FactionSetSpawnCommand extends EpicWarCommand {

  public FactionSetSpawnCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("setspawn")
        .description(plugin.getLanguage().getText("command.faction.setspawn"))
        .usage("\\ewf setspawn <factionShortcut>")
        .requiredArgumentAmount(1)
        .permission("epicwar.faction.setspawn"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    String factionShortcut = arguments.get(0);
    Faction faction = war.getFactionManager().findByShortcut(factionShortcut);
    if (faction != null) {
      faction.setSpawnLocation(sender.getLocation());
      war.getFactionManager().update(faction);
      sender.sendMessage(getPlugin().getLanguage().getFormatedText("messages.faction.setspawn.finished", faction.getShortcut(), faction.getSpawnLocation()));
    } else {
      sender.sendMessage(getPlugin().getLanguage().getFormatedText("messages.faction.notExists", factionShortcut));
    }
    return true;
  }

}
