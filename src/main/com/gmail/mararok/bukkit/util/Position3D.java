/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util;

public class Position3D implements Cloneable {
  public long x;
  public long y;
  public long z;

  public Position3D() {}
  
  public Position3D(long x, long y, long z) {
    this.set(x,y,z);
  }
  
  public void set(long x, long y, long z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public double getDistance2DTo(Position3D other) {
    return UMath.getDistance2D(x,y,other.x,other.y);
  }
  
  public double getDistance3DTo(Position3D other) {
    return UMath.getDistance3D(x,y,z,other.x,other.y,other.z);
  }
  
  
  public Position3D clone() {
    try {
      return (Position3D)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError();
    }
  }
  @Override
  public String toString() {
    return "[" + x + "," + y + "," + z + "]";
  }
}
