/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.event;

import com.gmail.mararok.epicwar.control.ControlArea;
import com.gmail.mararok.epicwar.player.WarPlayer;

public abstract class PlayerControlAreaEvent extends ControlAreaEvent {
  private WarPlayer player;

  public PlayerControlAreaEvent(WarPlayer player, ControlArea controlArea) {
    super(controlArea);
    this.player = player;
  }
  
  public WarPlayer getPlayer() {
    return player;
  }
}
