/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import java.util.Collection;

import com.mararok.epicwar.War;

public interface ControlPointManager {
  ControlPoint findById(int id);

  Collection<ControlPoint> findAll();

  ControlPoint create(ControlPointData data) throws Exception;

  void update(ControlPoint controlPoint) throws Exception;

  void delete(ControlPoint controlPoint) throws Exception;

  War getWar();
}
