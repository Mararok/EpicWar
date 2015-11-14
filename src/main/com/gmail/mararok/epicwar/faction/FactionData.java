/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.util.List;

import com.gmail.mararok.epiccore.Position3D;
import com.gmail.mararok.epiccore.entity.EntityData;

public class FactionData extends EntityData {
  public String name;
  public String shortcut;
  public String description;

  public Faction.Color color;
  public Faction.BannerPattern bannerPattern;

  public Position3D spawnPosition;

  @Override
  public List<String> toStringList() {
    List<String> list = super.toStringList();
    list.add(name);
    list.add(shortcut);
    list.add(description);
    list.add(Character.toString(color.getChatColor().getChar()));
    list.add(bannerPattern.serialize());
    list.add(Long.toString(spawnPosition.x));
    list.add(Long.toString(spawnPosition.y));
    list.add(Long.toString(spawnPosition.z));

    return list;
  }
}
