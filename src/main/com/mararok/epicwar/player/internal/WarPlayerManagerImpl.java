/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.mararok.epicwar.EpicWarEvent;
import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.player.WarPlayer;
import com.mararok.epicwar.player.WarPlayerManager;

public class WarPlayerManagerImpl implements WarPlayerManager {
  private Map<UUID, WarPlayerImpl> players;
  private WarPlayerMapper mapper;
  private War war;

  public WarPlayerManagerImpl(WarPlayerMapper mapper, War war) {
    players = new HashMap<UUID, WarPlayerImpl>();
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
    if (isFromThisWar(player)) {
      mapper.update((WarPlayerImpl) player);
    }
  }

  @Override
  public void delete(WarPlayer player) throws Exception {
    if (isFromThisWar(player)) {
      mapper.delete((WarPlayerImpl) player);
    }
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

  public void dispatchEvent(EpicWarEvent event) {
    war.getWarManager().getPlugin().getEventManager().dispatchEvent(event);
  }

  public void onServerReload(Collection<Player> playersOnline) {

  }

  public boolean isFromThisWar(WarPlayer player) {
    return getWar() == player.getWar();
  }

  @Override
  public War getWar() {
    return war;
  }

}
