/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.command.war;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.Language;
import com.gmail.mararok.epicwar.command.PluginParentCommand;

public class WarCommands extends PluginParentCommand {

	public WarCommands(EpicWarPlugin plugin, PluginParentCommand parent) {
		super(plugin, parent,"War");
		setDescription(Language.CD_WAR);
		setUsage("\\eww [subcommand]");
		addCommand(new WarListCommand(getPlugin(),this));
	}


}
