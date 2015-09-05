/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityData {
  public int id;
  
  public List<String> toStringList() {
    List<String> list = new ArrayList<String>();
    if (id != 0) {
      list.add(Integer.toString(id));
    }
    return list;
  }
}
