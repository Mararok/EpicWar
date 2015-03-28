/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.util.ArrayList;
import java.util.List;

public class SubsectorList {
  private List<Subsector> list;
  
  public SubsectorList() {
    list = new ArrayList<Subsector>();
  }
  
  public SubsectorList(List<Subsector> list) {
    this.list = list;
  }
  
  public void add(Subsector subsector) {
    list.add(subsector);
  }
  
  public void remove(Subsector subsector) {
    list.remove(subsector);
  }
  
  public Subsector get(int index) {
    return list.get(index);
  }
  
  public int size() {
    return list.size();
  }
}
