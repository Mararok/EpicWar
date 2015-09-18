/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import com.gmail.mararok.epicwar.control.NamedControlArea;

public abstract class NamedControlAreaImpl extends ControlAreaImpl implements NamedControlArea {
  private String shortName;
  private String name;
  private String description = "";
  
  public NamedControlAreaImpl(int id, String shortName, String name) {
    super(id);
    this.shortName = shortName;
    this.name = name;
  }
  
  @Override
  public String getShortName() {
    return shortName;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  public void setName(String newName) {
    name = newName;
    propertiesObserver.onChangeProperty("name",newName);
  }
  
  @Override
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String newDescription) {
    description = newDescription;
    propertiesObserver.onChangeProperty("description",newDescription);
  }
}
