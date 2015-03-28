/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util;

import java.util.Random;

public class UMath {
  public static double getDistance2D(long x1, long z1, long x2, long z2) {
    return Math.sqrt((x2 - x1)*(x2 - x1) + (z2 - z1)*(z2 - z1));
  }

  public static double getDistance3D(long x1, long y1, long z1, long x2, long y2, long z2) {
    return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2) + (z1 - z2)*(z1 - z2));
  }

  public static boolean isPointWithinCircle2D(long x1, long z1, long x2, long z2, long radius) {
    return Math.floor(getDistance2D(x1,z1,x2,z2)) <= radius;
  }

  public static boolean isPointWithinCircle3D(long x1, long y1, long z1, long x2, long y2, long z2, long radius) {
    return Math.floor(getDistance3D(x1,y1,z1,x2,y2,z2)) <= radius;
  }

  public static long getRandomIntegerFromRange(Random generator, int min, int max) {
    return generator.nextInt(max - min) + min;
  }
}
