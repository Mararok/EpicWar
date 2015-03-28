/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import org.bukkit.World;
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

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.War;

public class PlayerListener implements Listener {
  private boolean enabled = false;
  private PlayerManager players;

  public PlayerListener(PlayerManager players) {
      this.players = players;
      players.getPlugin().getServer().getPluginManager().registerEvents(this,players.getPlugin());
  }
  
  public void enable() {
    enabled = true;
  }
  
  public void disable() {
    enabled = true;
  }
  
  public boolean isEnabled() {
    return enabled;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerLogin(PlayerJoinEvent event) {
    if (!isEnabled()) {
      return;
    }
    
    players.onJoined(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuite(PlayerQuitEvent event) {
    if (!isEnabled()) {
      return;
    }
    
    players.onLeft(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
    if (!isEnabled()) {
      return;
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerDeath(PlayerDeathEvent event) {
    if (!isEnabled()) {
      return;
    }
    
    event.
    Player victim = event.getEntity();
    Player killer = victim.getKiller();
    players.onKill(killer,victim);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerMove(PlayerMoveEvent event) {
    if (!isEnabled()) {
      return;
    }
    
    
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (!isEnabled()) {
      return;
    }
  }

}
