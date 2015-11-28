/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.event;

import com.mararok.epicwar.control.ControlArea;
import com.mararok.epicwar.player.WarPlayer;

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
