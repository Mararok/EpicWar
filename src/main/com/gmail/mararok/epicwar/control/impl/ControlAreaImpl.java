/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import com.gmail.mararok.epiccore.entity.ObservedEntity;
import com.gmail.mararok.epicwar.control.ControlArea;
import com.gmail.mararok.epicwar.control.ControlAreaData;

public abstract class ControlAreaImpl extends ObservedEntity implements ControlArea {

  public ControlAreaImpl(ControlAreaData data) {
    super(data.id);
  }
}
