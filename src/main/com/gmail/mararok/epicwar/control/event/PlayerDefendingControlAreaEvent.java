/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.event;

import org.bukkit.event.HandlerList;

import com.gmail.mararok.epicwar.control.ControlArea;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class PlayerDefendingControlAreaEvent extends PlayerControlAreaEvent {
  
  public PlayerDefendingControlAreaEvent(WarPlayer player, ControlArea controlArea) {
    super(player,controlArea);
  }
  
  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers() {
    return handlers;
  }
 
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
