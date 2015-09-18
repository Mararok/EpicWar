/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.gmail.mararok.epicwar;

import org.bukkit.World;

public interface WarManager {
  public War get(World world);
  public EpicWarPlugin getPlugin();
}
