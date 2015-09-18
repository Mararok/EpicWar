/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.util.List;

import com.gmail.mararok.epicwar.control.ControlPointData;

public interface ControlPointMapper {
  
  public ControlPointData findById(int id);
  public List<ControlPointData> findAll();
  public void update(ControlPointImpl controlPoint);
}
