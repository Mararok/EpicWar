/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

public interface ControlPointDao {
  
  ControlPoint[] loadAll()
  void setOwner(ControlPoint controlPoint);
}
