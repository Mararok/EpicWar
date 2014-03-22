/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.util.Random;

public class UMath {
	public static double getDistance2D(int x1, int z1, int x2, int z2) {
		return Math.sqrt( (x2-x1)*(x2-x1) + (z2-z1)*(z2-z1) );
	}
	
	public static double getDistance3D(int x1, int y1, int z1, int x2, int y2, int z2) {
		return Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) + (z1-z2)*(z1-z2) );
	}
	
	public static boolean isPointWithinCircle2D(int x1, int z1, int x2, int z2, int radius) {
		return Math.floor(getDistance2D(x1,z1, x2,z2)) <= radius;
	}
	
	public static boolean isPointWithinCircle3D(int x1, int y1, int z1, int x2, int y2, int z2, int radius) {
		return Math.floor(getDistance3D(x1,y1,z1, x2,y2,z2)) <= radius;
	}
	
	public static int getRandomIntFromRange(Random generator, int min, int max) {
		return generator.nextInt(max-min)+min;
	}
}
