/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import org.bukkit.block.banner.PatternType;

import com.gmail.mararok.bukkit.util.Position3D;

public class FactionInfo implements Cloneable {
   int id;
  
  
  public Position3D spawnPosition;
  public int capitalSectorId;

  public FactionInfo clone() {
    try {
      return (FactionInfo) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.toString());
    }
  }

  @Override
  public String toString() {
    return (new StringBuilder("F[")).append(id).append("] ").
        append(" ").append(shortcut).append(" ").append(color.getChatColor() + name).
        append(" DESC: ").
        append(description).

        append(" BANNER:").append(bannerPatternType.name()).
        
        append("SPAWN: ").
        append(spawnPosition).

        append(" CapitalSectorId: ").
        append(capitalSectorId).toString();
  }
}
