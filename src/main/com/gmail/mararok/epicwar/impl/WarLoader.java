/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.impl;

import com.gmail.mararok.epicwar.WarManager;

public abstract class WarLoader {
  public WarManager warManager;
  public WarComponentsFactory componentsFactory;
  
  public abstract WarImpl load(String warName) throws Exception;
  
}
