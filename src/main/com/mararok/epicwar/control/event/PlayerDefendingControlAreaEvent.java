/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.event;

import org.bukkit.event.HandlerList;

import com.mararok.epicwar.control.ControlArea;
import com.mararok.epicwar.player.WarPlayer;

public class PlayerDefendingControlAreaEvent extends PlayerControlAreaEvent {

  public PlayerDefendingControlAreaEvent(WarPlayer player, ControlArea controlArea) {
    super(player, controlArea);
  }

  private static final HandlerList handlers = new HandlerList();

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
