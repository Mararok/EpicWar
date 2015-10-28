/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.impl;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.impl.ControlManagerImpl;
import com.gmail.mararok.epicwar.faction.FactionManager;
import com.gmail.mararok.epicwar.player.impl.PlayerManagerImpl;

public interface WarComponentsFactory {

  public PlayerManagerImpl newPlayerManager(War war);

  public FactionManager newFactionManager(War war);

  public ControlManagerImpl newControlManager(War war);
}
