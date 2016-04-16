/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import java.util.logging.Level;

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
import com.mararok.epicwar.player.WarPlayer;

public class PlayerListener implements Listener {
  private WarManager warManager;
  private EpicWarPlugin plugin;

  public PlayerListener(WarManager warManager) {
    this.warManager = warManager;
    getPlugin().getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerLogin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    War war = warManager.findByWorld(player.getWorld());
    if (war != null) {
      WarPlayerManagerImpl playerManager = (WarPlayerManagerImpl) war.getPlayerManager();
      try {
        WarPlayer warPlayer = playerManager.tryJoin(player);
        if (warPlayer != null) {
          warPlayer.teleport(warPlayer.getFaction().getSpawnLocation());
          event.setJoinMessage(getPlugin().getLanguage().getFormatedText("war.player.login",
              warPlayer.getNativePlayer().getDisplayName(),
              warPlayer.getFaction().getName()));
        }
      } catch (Exception e) {
        onException(player, "Exception when player try joined to war", e);
      }
    }

  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuite(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    try {
      War war = warManager.findByWorld(player.getWorld());
      if (war != null) {
        WarPlayerManagerImpl playerManager = (WarPlayerManagerImpl) war.getPlayerManager();
        playerManager.unload(player);
      }
    } catch (Exception e) {
      onException(player, "Exception when player quite", e);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
    Player player = event.getPlayer();
    try {
      War war = warManager.findByWorld(event.getFrom());
      if (war != null) {
        WarPlayerManagerImpl playerManager = (WarPlayerManagerImpl) war.getPlayerManager();
        playerManager.unload(player);
      }

      war = warManager.findByWorld(player.getWorld());
      if (war != null) {
        WarPlayerManagerImpl playerManager = (WarPlayerManagerImpl) war.getPlayerManager();
        WarPlayer warPlayer = playerManager.tryJoin(player);
        if (warPlayer != null) {
          warPlayer.teleport(warPlayer.getFaction().getSpawnLocation());
        }
      }

    } catch (Exception e) {
      onException(player, "Exception when player changed world", e);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerDeath(PlayerDeathEvent event) {
    Player victim = event.getEntity();
    Player killer = victim.getKiller();

    if (killer.getType() == EntityType.PLAYER && victim.getType() == killer.getType()) {
      War war = warManager.findByWorld(victim.getWorld());
      if (war != null) {
        WarPlayerManagerImpl playerManager = (WarPlayerManagerImpl) war.getPlayerManager();
        try {
          playerManager.addKill(killer, victim);
          WarPlayer victimWar = playerManager.findByPlayer(victim);
          victimWar.teleport(victimWar.getFaction().getSpawnLocation());
        } catch (Exception e) {
          onException(killer, "Exception when player killed other", e);
          onException(victim, "Exception when player was killed by other", e);
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    War war = warManager.findByWorld(player.getWorld());
    if (war != null) {
      try {
        WarPlayer warPlayer = war.getPlayerManager().findByPlayer(player);
        if (warPlayer != null) {
          ((WarPlayerImpl) warPlayer).updatePosition(event.getTo());
        }
      } catch (Exception e) {
        onException(player, "Exception when player moved", e);
      }
    }
  }

  private void onException(Player player, String message, Exception e) {
    getPlugin().getLogger().log(Level.SEVERE, message, e);
    player.sendMessage("EPICWAR INTERNAL ERROR");
  }

  private EpicWarPlugin getPlugin() {
    return warManager.getPlugin();
  }

}
