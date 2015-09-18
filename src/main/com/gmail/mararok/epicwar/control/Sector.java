/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.util.List;

import com.gmail.mararok.epicwar.control.impl.SectorManager;

/**
 * Its set of control points
 * 
 */
public interface Sector extends NamedControlArea {
  List<ControlPoint> getControlPoints();

  SectorManager getSectors();
}
