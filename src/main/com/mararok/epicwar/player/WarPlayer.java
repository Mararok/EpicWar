/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player;

import org.bukkit.entity.Player;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.Subsector;
import com.mararok.epicwar.faction.Faction;

public interface WarPlayer {

  public int getId();

  public PlayerStats getStats();

  public Faction getFaction();

  public void addKill();

  public void addDeath();

  public void addPoints(int points);

  public boolean isInWarArea();

  public Subsector getSubsector();

  public ControlPoint getControlPoint();

  public Sector getSector();

  public War getWar();

  public Player getNativePlayer();

}
