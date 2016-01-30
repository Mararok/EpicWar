/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import java.util.Collection;

import com.mararok.epicwar.War;

/**
 * Manage all control points of one war
 */
public interface ControlPointManager {

  public ControlPoint findById(int id);

  public Collection<ControlPoint> findAll();

  public ControlPoint create(ControlPointData data) throws Exception;

  public void update(ControlPoint controlPoint) throws Exception;

  /**
   * Rendering control point representation in MC
   * 
   * @param controlPoint
   * @param updateOnly When true changing only colorable elements of control point
   */
  void render(ControlPoint controlPoint, boolean updateOnly);

  public War getWar();

}
