/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2016 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.event;

import org.bukkit.event.HandlerList;

import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.faction.Faction;

public class CapturedControlPointEvent extends ControlPointEvent {
  private Faction previousOwner;

  public CapturedControlPointEvent(ControlPoint controlPoint, Faction previousOwner) {
    super(controlPoint);
    this.previousOwner = previousOwner;
  }

  public Faction getPreviousOwner() {
    return previousOwner;
  }

  private static final HandlerList handlers = new HandlerList();

  @Override
  public HandlerList getHandlers() {
    return getHandlerList();
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
