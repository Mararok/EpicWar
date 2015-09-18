/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.event;

import com.gmail.mararok.epicwar.EpicWarEvent;
import com.gmail.mararok.epicwar.control.impl.ControlAreaImpl;

public abstract class ControlAreaEvent extends EpicWarEvent {
  private ControlAreaImpl controlArea;
  
  public ControlAreaEvent(ControlAreaImpl controlArea) {
    this.controlArea = controlArea;
  }
  
  public ControlAreaImpl getControlArea() {
    return controlArea;
  }
  

}
