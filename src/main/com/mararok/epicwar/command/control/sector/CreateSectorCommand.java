/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.command.control.sector;

import org.bukkit.entity.Player;

import com.mararok.epiccore.command.CommandArguments;
import com.mararok.epiccore.command.CommandMetadata;
import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.command.EpicWarCommand;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.SectorData;

public class CreateSectorCommand extends EpicWarCommand {

  public CreateSectorCommand(EpicWarPlugin plugin) {
    super(plugin);

    setMetadata(CommandMetadata.command("create")
        .description(getLanguage().getText("command.sector.create"))
        .usage("/ews create <name>")
        .permission("epicwar.sector.create")
        .requiredArguments(1));
  }

  @Override
  protected boolean onCommandOnWarWorld(War war, Player sender, CommandArguments<EpicWarPlugin> arguments) throws Exception {
    SectorData data = new SectorData();
    data.name = arguments.join(0, " ");
    Sector sector = war.getSectorManager().create(data);

    sender.sendMessage(getLanguage().getFormatedText("message.sector.created", sector.getName(), sector.getId()));
    return true;
  }

}
