/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */

package com.mararok.epicwar;

import java.util.Collection;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface WarManager {
  public War findByPlayer(Player player);

  public War findByWorld(World world);

  public Collection<War> findAll();

  public EpicWarPlugin getPlugin();

}
