/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.event;

import org.bukkit.event.HandlerList;

import com.gmail.mararok.epicwar.control.impl.ControlAreaImpl;
import com.gmail.mararok.epicwar.faction.internal.FactionImpl;

public class CapturedControlAreaEvent extends ControlAreaEvent {
  private FactionImpl oldOwner;
  
  public CapturedControlAreaEvent(ControlAreaImpl controlArea, FactionImpl oldOwner) {
    super(controlArea);
    this.oldOwner = oldOwner;
  }
  
  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers() {
    return handlers;
  }
 
  public static HandlerList getHandlerList() {
    return handlers;
  }

}
