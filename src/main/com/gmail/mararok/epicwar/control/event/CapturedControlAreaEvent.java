/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.event;

import org.bukkit.event.HandlerList;

import com.gmail.mararok.epicwar.control.ControlArea;
import com.gmail.mararok.epicwar.faction.Faction;

public class CapturedControlAreaEvent extends ControlAreaEvent {
  private Faction previousOwner;

  public CapturedControlAreaEvent(ControlArea controlArea, Faction previousOwner) {
    super(controlArea);
    this.previousOwner = previousOwner;
  }

  public Faction getPreviousOwner() {
    return previousOwner;
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
