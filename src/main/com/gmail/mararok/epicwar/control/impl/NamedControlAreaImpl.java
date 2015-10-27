/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.NamedControlArea;
import com.gmail.mararok.epicwar.control.NamedControlAreaData;

public abstract class NamedControlAreaImpl extends ControlAreaImpl implements NamedControlArea {
  private String name;
  private String description;

  public NamedControlAreaImpl(NamedControlAreaData data, War war) {
    super(data, war);
    this.name = data.name;
    this.description = data.description;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String newName) {
    name = newName;
    onChangeProperty("name", newName);
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String newDescription) {
    description = newDescription;
    onChangeProperty("description", newDescription);
  }
}
