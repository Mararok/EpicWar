/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginConfig implements Disposable {
  private JavaPlugin plugin;
  private boolean saved = false;

  public PluginConfig(JavaPlugin plugin) {
    this.plugin = plugin;
    plugin.saveDefaultConfig();
  }

  protected Configuration getConfig() {
    return plugin.getConfig();
  }
  
  @Override
  public void dispose() {
    if (!saved) {
      save();
    }
  }

  protected void needSave() {
    saved = false;
  }

  private void save() {
    if (!saved) {
      plugin.saveConfig();
      saved = true;
    }
  }
}
