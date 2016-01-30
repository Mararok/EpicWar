/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction;

import com.mararok.epiccore.entity.EntityData;
import com.mararok.epiccore.math.Vector3i;

public class FactionData extends EntityData {
  public String name;
  public String shortcut;
  public String description;

  public Faction.Color color;
  public Faction.BannerPattern bannerPattern;

  public Vector3i spawnPosition;
}
