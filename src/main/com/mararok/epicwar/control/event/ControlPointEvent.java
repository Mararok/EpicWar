/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2016 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.event;

import com.mararok.epicwar.control.ControlPoint;

public abstract class ControlPointEvent extends ControlAreaEvent<ControlPoint> {

  public ControlPointEvent(ControlPoint controlPoint) {
    super(controlPoint);
  }

}
