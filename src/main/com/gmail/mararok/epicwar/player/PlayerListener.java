/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public class PlayerListener implements Listener {
	private PlayerManager Players;
	public PlayerListener(PlayerManager players) {
		if (players.getPlugin().isValid()) {
			Players = players;
			registerEvents(players.getPlugin());
		}
	}
	
	private void registerEvents(EpicWarPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this,plugin);
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerLogin(PlayerJoinEvent event) {
		getPlayers().onPlayerJoin(event.getPlayer());
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerQuite(PlayerQuitEvent event) {
		getPlayers().onPlayerQuite(event.getPlayer());
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		getPlayers().onPlayerChangedWorld(event.getPlayer());
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player victim = event.getEntity();
		Player killer = victim.getKiller();
		if (killer.getType() == EntityType.PLAYER) {
			getPlayers().onPlayerDeath(victim);
		}
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		getPlayers().onPlayerMove(event);
	}
	
	@EventHandler (priority = EventPriority.MONITOR) 
	public void onPlayerInteract(PlayerInteractEvent event) {
		getPlayers().onPlayerInteract(event);
	}	
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
		getPlayers().onNameTag(event);
	}
	
	private PlayerManager getPlayers() {
		return Players;
	}

	
}
