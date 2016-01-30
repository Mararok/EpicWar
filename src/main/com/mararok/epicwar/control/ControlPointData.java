/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control;

import com.mararok.epiccore.math.Vector3i;

public class ControlPointData extends NamedControlAreaData {

  public int ownerId;
  public int sectorId;

  public Vector3i position;
  public int radius;
  public int maxPower;
  public int[] connections;

}
