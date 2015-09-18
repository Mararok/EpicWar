/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

public interface SubsectorDAO {
  
  public int findControlPointId(SubsectorImpl subsector);
  public void setControlPointId(SubsectorImpl subsector);
  
  public int addNew(SubsectorImpl subsector);
}
