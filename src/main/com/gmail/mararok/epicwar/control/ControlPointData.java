/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.epicwar.control.impl.NamedControlAreaData;

public class ControlPointData extends NamedControlAreaData {

  public int ownerId;
  public int sectorId;
  
  public Position3D location;
  public int radius;
  public ControlAreaPower power;
  
}
