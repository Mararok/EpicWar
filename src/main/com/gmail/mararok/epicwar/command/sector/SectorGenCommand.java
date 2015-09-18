/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.sector;

import java.sql.SQLException;

import com.gmail.mararok.bukkit.util.command.CommandArguments;
import com.gmail.mararok.bukkit.util.command.ParentPluginCommand;
import com.gmail.mararok.bukkit.util.command.PluginCommand;
import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;
import com.gmail.mararok.epicwar.sector.SectorExistsException;
import com.gmail.mararok.epicwar.sector.SectorsGeneratorConfigException;
import com.gmail.mararok.epicwar.sector.SimpleSectorsGenerator;

public class SectorGenCommand extends PluginCommand {

  public SectorGenCommand(EpicWarPlugin plugin, ParentPluginCommand parent) {
    super(plugin, parent, "gen", true);
    setOnlyPlayer();
    setDescription(Language.CD_SECTOR_GEN);
    setUsage("\\ews gen");
  }

  @Override
  public boolean onCommandAsAdmin(WarPlayerImpl admin, CommandArguments arguments) {

    SimpleSectorsGenerator generator = new SimpleSectorsGenerator();
    try {

      generator.setConfig(admin.getWar().getSectorsGeneratorConfig());
      admin.getWar().getSectorManager()
          .createFromList(generator.generate(admin.getLocation()));
      admin.sendMessage("You created sectors");
      return true;

    } catch (SectorsGeneratorConfigException e) {
      admin.sendMessage(e.getMessage());
    } catch (SectorExistsException e) {
      admin.sendMessage("Sector with name " + e.getName() + " exists!");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

}
