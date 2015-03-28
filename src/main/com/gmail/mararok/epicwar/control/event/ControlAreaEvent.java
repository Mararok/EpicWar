/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.event;

import com.gmail.mararok.epicwar.EpicWarEvent;
import com.gmail.mararok.epicwar.control.ControlArea;

public abstract class ControlAreaEvent extends EpicWarEvent {
  private ControlArea controlArea;
  
  public ControlAreaEvent(ControlArea controlArea) {
    this.controlArea = controlArea;
  }
  
  public ControlArea getControlArea() {
    return controlArea;
  }
  

}
