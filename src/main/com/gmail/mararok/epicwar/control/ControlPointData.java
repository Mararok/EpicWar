/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import com.gmail.mararok.epiccore.util.Position3D;

public class ControlPointData extends NamedControlAreaData {

  public int ownerId;
  public int sectorId;

  public Position3D position;
  public int radius;
  public ControlAreaPower power;
  public int[] connections;

}
