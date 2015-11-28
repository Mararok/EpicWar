/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mararok.epicwar.EpicWarPlugin;

public class EnviromentListener implements Listener {
  EpicWarPlugin plugin;

  public EnviromentListener(EpicWarPlugin plugin) {
    this.plugin = plugin;
  }

  public void registerEvents() {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerChat(AsyncPlayerChatEvent event) {
  }
}
