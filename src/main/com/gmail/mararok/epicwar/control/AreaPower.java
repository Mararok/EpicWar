/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

public class AreaPower {
  private int current;
  private int max;
  
  public AreaPower(int max) {
    this(max,max);
  }
  
  public AreaPower(int current, int max) {
    this.current = current;
    this.max = max;
  }
 
  
  public int getCurrent() {
    return current;
  }
  
  public void set(int value) {
    current = Math.min(value,max);
  }
  
  public void reset() {
    current = max;
  }
  
  public boolean canCapture() {
    return current <= 0;
  }
  
  public int getMax() {
    return max;
  }
  
  public void setMax(int newMax) {
    max = newMax;
  }
}
