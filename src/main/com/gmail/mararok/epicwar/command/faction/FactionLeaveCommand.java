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
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;

public class FactionLeaveCommand extends PluginCommand {

  public FactionLeaveCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "leave");
    setOnlyPlayer();
    setDescription(Language.CD_FACTION_LEAVE);
    setUsage("/ewf leave");
  }

  @Override
  public boolean onCommandAsPlayer(WarPlayerImpl player, CommandArguments arguments) {
    player.leaveFromFaction();
    return true;
  }

}
