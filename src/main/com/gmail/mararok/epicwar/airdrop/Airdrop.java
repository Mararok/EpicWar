package com.gmail.mararok.epicwar.airdrop;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.utility.UMath;

/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */

public class Airdrop implements MetadataValue {
	private WarPlayer Summoner;
	private Random Generator;
	
	/*
	 * for random items
	 */
	public Airdrop(WarPlayer summoner) {
		Summoner = summoner;
		Generator = new Random(System.nanoTime());
		create();
	}
	
	private void create() {
		Location loc = findLocation();
		if (loc == null) {
			Summoner.sendMessage("airdrop can't be droped");
		} else {
			//airdropConfig config = Summoner.getPlayers().getWar().getairdropConfig();
			Summoner.getWorld().spawnFallingBlock(loc,Material.ANVIL,(byte) 0).setMetadata("airdrop", this);
			//Summoner.subPoints()
		}
	}
	
	private Location findLocation() {
		World world = Summoner.getWorld();
		AirdropConfig config = Summoner.getWar().getAirdropConfig();
		int height = UMath.getRandomIntFromRange(Generator, config.heightMin,config.heightMax);
		int radius = UMath.getRandomIntFromRange(Generator,config.radiusMin,config.radiusMax);
		Location loc = Summoner.getBPlayer().getLocation();
		boolean valid = false;
		
		loc.setX(loc.getBlockX()+((Generator.nextBoolean())?radius:-radius));
		loc.setY(height);
		loc.setZ(loc.getBlockZ()+((Generator.nextBoolean())?radius:-radius));
			
		Block block = world.getBlockAt(loc);
		if (block.getType() == Material.AIR && block.getRelative(BlockFace.DOWN).getType() == Material.AIR) {
			valid = true;
		}
		
		if (valid) {
			return loc;
		}
		
		return null;
	}

	public void onFalled(Block block) {
		Location loc = block.getLocation();
		Summoner.getPlayers().getPlugin().logInfo("airdrop falled at: "+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
		Summoner.sendMessage("airdrop falled, distance "+loc.distance(Summoner.getBPlayer().getLocation()));
		AirdropConfig config = Summoner.getWar().getAirdropConfig();
		int slots = UMath.getRandomIntFromRange(Generator, config.slotsMin,config.slotsMax);
		Summoner.getWorld().getBlockAt(loc).setType(Material.CHEST);
		Chest chest = (Chest)Summoner.getWorld().getBlockAt(loc).getState();
		ItemStack stack;
		for (int i = 0; i < slots;++i) {
			stack = config.ItemsDrop.get(Generator.nextInt(config.ItemsDrop.size())).getRandom(Generator);
			if (stack != null)
				chest.getBlockInventory().addItem(stack);
		}
		chest.update();
	}
	
	@Override
	public boolean asBoolean() {
		throw new RuntimeException("No value");
	}

	@Override
	public byte asByte() {
		throw new RuntimeException("No value");
	}

	@Override
	public double asDouble() {
		throw new RuntimeException("No value");
	}

	@Override
	public float asFloat() {
		throw new RuntimeException("No value");
	}

	@Override
	public int asInt() {
		throw new RuntimeException("No value");
	}

	@Override
	public long asLong() {
		throw new RuntimeException("No value");
	}

	@Override
	public short asShort() {
		throw new RuntimeException("No value");
	}

	@Override
	public String asString() {
		throw new RuntimeException("No value");
	}

	@Override
	public Plugin getOwningPlugin() {
		// TODO Auto-generated method stub
		return this.Summoner.getPlayers().getPlugin();
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object value() {
		return this;
	}
}
