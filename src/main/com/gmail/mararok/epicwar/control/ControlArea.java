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
  
  /** Returns current faction area owner. 
   */
  public abstract Faction getOwner();
  
  /** Sets new faction area owner and returns old faction owner.
   */
  public Faction setOwner(Faction newOwner) {
    return getOwner();
  }
  
  public int getId() {
    return id;
  }
  
  public abstract War getWar();
  
}
