/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

public interface NamedControlArea extends ControlArea {
  public String getName();

  public void setName(String newName);

  public String getDescription();

  public void setDescription(String newDescription);
}
