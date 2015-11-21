/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.sector;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.player.WarPlayer;

public class SectorInfoCommand extends EpicWarCommand {

  public SectorInfoCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("info")
        .description(plugin.getLanguage().getText("command.sector.info"))
        .usage("\\ews info"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    return true;
  }

  @Override
  protected boolean onCommandAsWarPlayer(WarPlayer sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {

    return true;
  }

  /*
   * @Override
   * public boolean onCommandOnW(WarPlayerImpl player, CommandArguments arguments) {
   * if (player.hasFaction()) {
   * player.sendMessage("You are at " + player.getSector().getName()
   * + " sector");
   * } else {
   * SectorImpl sector = player.getWar().getSectorManager()
   * .getFromLocation(player.getLocation());
   * player.sendMessage("You are at " + sector.getName() + " sector");
   * }
   * 
   * return true;
   * }
   */

}
