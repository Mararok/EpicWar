/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;

public abstract class ControlArea {
  private int id;
  
  public ControlArea(int id) {
    this.id = id;
  }
  
  public abstract Faction getOwner();
  public abstract Faction setOwner(Faction newOwner);
  
  public int getId() {
    return id;
  }
  
  public abstract War getWar();
  
}
