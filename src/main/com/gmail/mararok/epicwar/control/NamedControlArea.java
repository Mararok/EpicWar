/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

public abstract class NamedControlArea extends ControlArea {
  private String name;
  private String description;
  
  public NamedControlArea(int id) {
    super(id);
  }
  
  public String getName() {
    return name;
  }
  
  public String getNameWithoutWhitespace() {
    return name.replace(' ','_');
  }
  
  public void setName(String newName) {
    name = newName;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String newDescription) {
    description = newDescription;
  }
}
