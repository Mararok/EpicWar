/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

public interface SubsectorDao {
  
  public int findControlPointId(Subsector subsector);
  public void setControlPointId(Subsector subsector);
  
  public int addNew(Subsector subsector);
}
