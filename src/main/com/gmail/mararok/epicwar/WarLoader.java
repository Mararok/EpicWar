/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

public abstract class WarLoader {
  public WarManager wars;
  public WarComponentsFactory componentsFactory;
  
  public abstract War load(String warName) throws Exception;
  
}
