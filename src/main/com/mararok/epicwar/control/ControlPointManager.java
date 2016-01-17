/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import java.util.Collection;

import com.mararok.epicwar.War;

public interface ControlPointManager {

  public ControlPoint findById(int id);

  public Collection<ControlPoint> findAll();

  public ControlPoint create(ControlPointData data) throws Exception;

  public void update(ControlPoint controlPoint) throws Exception;

  public War getWar();
}
