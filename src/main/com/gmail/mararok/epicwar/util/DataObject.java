/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.util;

public interface DataObject<InfoType> {
  public int getID();

  public String getName();

  public InfoType getInfo();
}
