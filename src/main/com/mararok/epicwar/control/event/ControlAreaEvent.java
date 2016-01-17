/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.event;

import com.mararok.epicwar.EpicWarEvent;
import com.mararok.epicwar.control.ControlArea;

public abstract class ControlAreaEvent<T extends ControlArea> extends EpicWarEvent {
  private T controlArea;

  public ControlAreaEvent(T controlArea) {
    this.controlArea = controlArea;
  }

  public T getControlArea() {
    return controlArea;
  }

}
