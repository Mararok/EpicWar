/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.event;

import com.gmail.mararok.epicwar.control.impl.ControlAreaImpl;
import com.gmail.mararok.epicwar.player.impl.WarPlayerImpl;

public abstract class PlayerControlAreaEvent extends ControlAreaEvent {
  private WarPlayerImpl player;

  public PlayerControlAreaEvent(WarPlayerImpl player, ControlAreaImpl controlArea) {
    super(controlArea);
    this.player = player;
  }
  
  public WarPlayerImpl getPlayer() {
    return player;
  }
}
