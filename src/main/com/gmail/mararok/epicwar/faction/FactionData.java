/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.util.List;

import com.gmail.mararok.bukkit.util.entity.EntityData;

public class FactionData extends EntityData {
  public String name;
  public String shortcut;
  public String description;
  
  public FactionAppearance appearance;
  
  public int capitalSectorId;
  
  public List<String> toStringList() {
    List<String> list = super.toStringList();
    list.add(name);
    list.add(shortcut);
    list.add(description);
    list.add(Character.toString(appearance.color.getChatColor().getChar()));
    list.add(appearance.bannerPattern.serialize());
    list.add( Integer.toString(capitalSectorId));
    
    return list;
  }
}
