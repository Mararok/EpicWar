/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.mararok.epicwar.EpicWarEvent;
import com.mararok.epicwar.War;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.player.WarPlayer;
import com.mararok.epicwar.player.WarPlayerData;
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
    WarPlayer player = findByPlayer(nativePlayer);
    return player;
  }

  /**
   * @TODO remove from ocupation
   */
  public void unload(Player nativePlayer) {
    players.remove(nativePlayer.getUniqueId());
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
      if (player != null) {
        players.put(uuid, player);
      }
    }

    return player;
  }

  @Override
  public WarPlayer register(Player nativePlayer, Faction faction) throws Exception {
    if (findByPlayer(nativePlayer) != null) {
      WarPlayerData data = new WarPlayerData();
      data.factionId = faction.getId();
      data.uuid = nativePlayer.getUniqueId();
      WarPlayerImpl player = mapper.insert(data);

      players.put(nativePlayer.getUniqueId(), player);
      return player;
    }

    return null;
  }

  @Override
  public void update(WarPlayer player) throws Exception {
    WarPlayerImpl entity = (WarPlayerImpl) player;
    mapper.update(entity);
    entity.clearChanges();
  }

  /**
   * @TODO adds remove from ocupation.
   */
  @Override
  public void delete(WarPlayer player) throws Exception {
    unload(player.getNativePlayer());
    mapper.delete((WarPlayerImpl) player);
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

  @Override
  public War getWar() {
    return war;
  }

}
