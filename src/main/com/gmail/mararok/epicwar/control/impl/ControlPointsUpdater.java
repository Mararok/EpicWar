/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import org.bukkit.scheduler.BukkitRunnable;

public class ControlPointsUpdater extends BukkitRunnable {
  private ControlPointRepository controlPoints;
  private boolean enabled = false;

  public ControlPointsUpdater(ControlPointRepository controlPoints) {
    this.controlPoints = controlPoints;
  }

  @Override
  public void run() {
    if (isEnabled()) {
      controlPoints.update();
    }
  }

  public void setEnabled(boolean enable) {
    enabled = enable;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
