/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.airdrop;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gmail.mararok.epicwar.utility.ChanceGenerator;

public class AirdropItem {
	private Material ItemType;
	private int MinAmount;
	private int MaxAmount;
	private int Chance;
	public AirdropItem(Material itemType,int minAmount, int maxAmount, int chance) {
		ItemType = itemType;
		MinAmount = (minAmount < itemType.getMaxStackSize())?minAmount:itemType.getMaxStackSize()-1;
		MaxAmount = (maxAmount >= itemType.getMaxStackSize())?maxAmount:itemType.getMaxStackSize();
		Chance = chance;
	}
	
	public ItemStack getRandom(Random generator) {
		if (ChanceGenerator.calcChance(Chance)) {
			ItemStack stack = new ItemStack(ItemType,generator.nextInt(MaxAmount-MinAmount)+MinAmount);
			return stack;
		}
		
		return null;
	}
}
