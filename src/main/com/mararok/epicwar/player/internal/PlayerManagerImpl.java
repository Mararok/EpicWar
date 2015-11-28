/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import com.mararok.epicwar.EpicWarEvent;
import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.player.PlayerManager;
import com.mararok.epicwar.player.WarPlayer;

import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

public class PlayerManagerImpl implements PlayerManager {
  private Map<UUID, WarPlayerImpl> players;
  private WarPlayerMapper mapper;
  private War war;

  public PlayerManagerImpl(WarPlayerMapper mapper, War war) {
    players = HashObjObjMaps.newMutableMap();
    this.mapper = mapper;
    this.war = war;
  }

  public WarPlayer tryJoin(Player nativePlayer) throws Exception {
    if (war.getWorld() == nativePlayer.getWorld()) {
      WarPlayer player = findByPlayer(nativePlayer);
      return player;
    }

    return null;
  }

  public void unload(Player nativePlayer) {
    if (war.getWorld() != nativePlayer.getWorld()) {
      players.remove(nativePlayer.getUniqueId());
    }
  }

  @Override
  public WarPlayer findByPlayer(Player nativePlayer) throws Exception {
    return findByUUID(nativePlayer.getUniqueId());
  }

  @Override
  public WarPlayer findByUUID(UUID uuid) throws Exception {
    WarPlayerImpl player = players.get(uuid);
    if (player == null) {
      player = mapper.findByUUID(uuid);
      players.put(uuid, player);
    }

    return player;
  }

  @Override
  public WarPlayer register(Player nativePlayer, Faction faction) throws Exception {
    return null;
  }

  @Override
  public void update(WarPlayer player) throws Exception {
  }

  @Override
  public void delete(WarPlayer player) throws Exception {
  }

  public void addKill(Player killer, Player victim) throws Exception {
    if (killer.getType() == EntityType.PLAYER && victim.getType() == EntityType.PLAYER) {
      WarPlayer warKiller = findByPlayer(killer);
      WarPlayer warVictim = findByPlayer(victim);
      if (warKiller != null && warVictim != null) {
        // warKiller.addKills();
        // warVictim.addDeaths();
      }
    }
  }

  public void updatePosition(PlayerMoveEvent event) {
    WarPlayerImpl warPlayer = players.get(event.getPlayer().getUniqueId());
    if (warPlayer != null) {
      warPlayer.updatePosition(event);
    }
  }

  public void dispatchEvent(EpicWarEvent event) {
    war.getWarManager().getPlugin().getEventManager().dispatchEvent(event);
  }

  public void onServerReload(Collection<Player> playersOnline) {

  }

  @Override
  public War getWar() {
    return war;
  }

}
