/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import org.bukkit.entity.Player;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.control.Subsector;
import com.gmail.mararok.epicwar.faction.Faction;

public interface WarPlayer {
 
  public int getId();
  public Player getNativePlayer();
  public PlayerStats getStats();
  
  public Faction getFaction();
  
  public Subsector getSubsector();
  public ControlPoint getControlPoint();
  public Sector getSector();
  
  public War getWar();
}
