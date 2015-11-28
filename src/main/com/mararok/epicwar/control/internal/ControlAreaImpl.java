/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import com.mararok.epiccore.entity.ObservedEntity;
import com.mararok.epicwar.control.ControlArea;
import com.mararok.epicwar.control.ControlAreaData;

public abstract class ControlAreaImpl extends ObservedEntity implements ControlArea {

  public ControlAreaImpl(ControlAreaData data) {
    super(data.id);
  }
}
