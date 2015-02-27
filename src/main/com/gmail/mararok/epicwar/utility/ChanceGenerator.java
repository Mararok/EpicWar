/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.util.Random;

public class ChanceGenerator {
	public static boolean calcChance(int chance) {
		if (chance == 100) {
			return true;
		} 
		
		if (chance != 0) {
			Random Generator = new Random();
			int num = Generator.nextInt(100);
			if (num != 0 && num <= chance) {
				return true;
			}
		}
		
		return false;
	}
}
