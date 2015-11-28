/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.mararok.epicwar;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface WarManager {
  public War findByPlayer(Player player);

  public War findByWorld(World world);

  public EpicWarPlugin getPlugin();
}
