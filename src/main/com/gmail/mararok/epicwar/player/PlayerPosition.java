/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;

public class PlayerPosition {
   private Position3D position = new Position3D();
   private ControlPoint controlPoint;
   private Sector sector;
   
   private WarPlayer player;
   
   public PlayerPosition(WarPlayer player) {
     this.player = player;
   }

  public void update(PlayerMoveEvent event) {
    
  }
  
  public Position3D getPosition() {
    return position.clone();
  }
  
  public ControlPoint getControlPoint() {
    return controlPoint;
  }
  
  public Sector getSector() {
    return sector;
  }
  
  public WarPlayer getPlayer() {
    return player;
  }
}

