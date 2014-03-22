/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.metadata.MetadataValue;

import com.gmail.mararok.epicwar.airdrop.Airdrop;

public class EnviromentListener implements Listener {
	EpicWarPlugin Plugin;
	public EnviromentListener(EpicWarPlugin plugin) {
		Plugin = plugin;
		registerEvents();
	}
	
	private void registerEvents() {
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		Entity entity = event.getEntity(); 
		if (entity instanceof FallingBlock && event.getTo() == Material.ANVIL) {
			FallingBlock block = (FallingBlock)entity;
			if (block.hasMetadata("airdrop")) {
				MetadataValue value = block.getMetadata("airdrop").get(0);
				Airdrop airdrop = (Airdrop)value;
				event.setCancelled(true);
				airdrop.onFalled(event.getBlock());
			}
		}
	}
}
