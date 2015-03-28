/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.util;

public class DescriptionInfo implements Cloneable {
  public String name;
  public String description;

  public String getNameWithoutWhitespace() {
    return name.replace(' ','_');
  }
  
  public DescriptionInfo clone() throws CloneNotSupportedException {
    return (DescriptionInfo) super.clone();
  }
}
