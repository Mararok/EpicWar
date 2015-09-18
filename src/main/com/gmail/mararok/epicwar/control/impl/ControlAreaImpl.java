/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import com.gmail.mararok.bukkit.util.entity.Entity;
import com.gmail.mararok.epicwar.control.ControlArea;

public abstract class ControlAreaImpl extends Entity implements ControlArea {
  public ControlAreaImpl(int id) {
    super(id);
  }
}
