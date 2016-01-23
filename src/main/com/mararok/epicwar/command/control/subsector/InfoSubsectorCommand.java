/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.subsector;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epiccore.misc.MessageBuilder;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.Subsector;
import com.mararok.epicwar.player.WarPlayer;

public class InfoSubsectorCommand extends EpicWarCommand {

  public InfoSubsectorCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("info")
        .description(getLanguage().getText("command.subsector.info"))
        .usage("\\ewss info [chunkX] [chunkZ]"));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    if (arguments.hasAny()) {
      if (!(arguments.isExists(0) && arguments.isNumber(0)) || !(arguments.isExists(1) && arguments.isNumber(1))) {
        return false;
      }

      int chunkX = arguments.asInt(0);
      int chunkZ = arguments.asInt(1);
      sendInfo(sender, war.getSubsectorMap().get(chunkX, chunkZ));

      return true;
    } else {
      return super.onCommandOnWarWorld(war, sender, arguments);
    }

  }

  @Override
  protected boolean onCommandAsWarPlayer(WarPlayer sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    sendInfo(sender.getNativePlayer(), sender.getSubsector());
    return true;
  }

  private void sendInfo(Player sender, Subsector subsector) {
    if (subsector != null) {
      sender.sendMessage(MessageBuilder.message()
          .line("[" + subsector.getId() + "] " + subsector.getChunkX() + " " + subsector.getChunkZ())
          .line(getLanguage().getFormatedText("message.subsector.info.controlPoint", subsector.getControlPoint().getId(), subsector.getControlPoint().getName()))
          .toArray());
    }
  }

}
