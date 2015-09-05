/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class EventManager {
  private JavaPlugin plugin;
  private boolean logging;
  private boolean enabled;
  
  public EventManager(JavaPlugin plugin) {
    this.plugin = plugin;
  }
  
  public void callEvent(Event event) {
    if (enabled) {
      Bukkit.getServer().getPluginManager().callEvent(event);
      
      if (logging) {
        plugin.getLogger().info("[EVENT] "+event);
      }
    } else {
      if (logging) {
        plugin.getLogger().info("[EVENT DISABLED] "+event);
      }
    }

  }
  
  public void setEnabled(boolean enable) {
    enabled = enable;
  }
  
  public void setLogging(boolean logging) {
    this.logging = logging;
  }
} 
