/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2016 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.event;

import com.mararok.epicwar.EpicWarEvent;
import com.mararok.epicwar.control.ControlPoint;

/**
 * Base class for all Control Point events
 */
public abstract class ControlPointEvent extends EpicWarEvent {

  private ControlPoint controlPoint;

  public ControlPointEvent(ControlPoint controlPoint) {
    this.controlPoint = controlPoint;
  }

  public ControlPoint getControlPoint() {
    return controlPoint;
  }

}
