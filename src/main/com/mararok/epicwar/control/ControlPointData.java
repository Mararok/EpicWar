/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import com.mararok.epiccore.math.Position3D;

public class ControlPointData extends NamedControlAreaData {

  public int ownerId;
  public int sectorId;

  public Position3D position;
  public int radius;
  public int maxPower;
  public int[] connections;

}
