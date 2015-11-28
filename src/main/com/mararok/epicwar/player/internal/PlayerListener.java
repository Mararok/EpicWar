/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mararok.epicwar.EpicWarPlugin;
import com.mararok.epicwar.War;
import com.mararok.epicwar.WarManager;

public class PlayerListener implements Listener {
  private boolean enabled = false;
  private WarManager warManager;

  public PlayerListener(WarManager warManager) {
    this.warManager = warManager;

    EpicWarPlugin plugin = warManager.getPlugin();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerLogin(PlayerJoinEvent event) {
    if (!isEnabled()) {
      return;
    }

    Player player = event.getPlayer();
    War war = warManager.findByWorld(player.getWorld());
    if (war != null) {
      PlayerManagerImpl playerManager = (PlayerManagerImpl) war.getPlayerManager();
      try {
        playerManager.tryJoin(player);
      } catch (Exception e) {
        player.sendMessage("INTERNAL ERROR");
      }
    }

  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuite(PlayerQuitEvent event) {
    if (!isEnabled()) {
      return;
    }

    Player player = event.getPlayer();
    War war = warManager.findByWorld(player.getWorld());
    if (war != null) {
      PlayerManagerImpl playerManager = (PlayerManagerImpl) war.getPlayerManager();
      playerManager.unload(player);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
    if (!isEnabled()) {
      return;
    }

    Player player = event.getPlayer();

    War war = warManager.findByWorld(event.getFrom());
    if (war != null) {
      PlayerManagerImpl playerManager = (PlayerManagerImpl) war.getPlayerManager();
      playerManager.unload(player);
    }

    war = warManager.findByWorld(player.getWorld());
    if (war != null) {
      PlayerManagerImpl playerManager = (PlayerManagerImpl) war.getPlayerManager();
      try {
        playerManager.tryJoin(player);
      } catch (Exception e) {
        player.sendMessage("INTERNAL ERROR");
      }
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerDeath(PlayerDeathEvent event) {
    if (!isEnabled()) {
      return;
    }

    Player victim = event.getEntity();
    Player killer = victim.getKiller();

    if (killer.getType() == EntityType.PLAYER && victim.getType() == killer.getType()) {
      War war = warManager.findByWorld(victim.getWorld());
      if (war != null) {
        PlayerManagerImpl playerManager = (PlayerManagerImpl) war.getPlayerManager();
        try {
          playerManager.addKill(killer, victim);
        } catch (Exception e) {
          killer.sendMessage("INTERNAL ERROR");
          victim.sendMessage("INTERNAL ERROR");
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerMove(PlayerMoveEvent event) {
    if (!isEnabled()) {
      return;
    }

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

}
