/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import org.bukkit.entity.Player;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.impl.WarPlayerRepository;

public class PlayerService {
  private WarPlayerRepository repository;
  private War war;
  
  public PlayerService(WarPlayerRepository repository, War war) {
    this.repository = repository;
  }
  
  public WarPlayer registerToWar(Player player, int factionId) {
    WarPlayer p = repository.find(player);
    if (p == null) {
      Faction faction = war.getFactionService().findById(factionId);
      if (faction != null) {
        repository.add()
      }
    }
  }
  
  public WarPlayer joinToWar(Player player) {
    
  }
  
  public void joinToFaction(WarPlayer player, int factionId) {
    if (player.getWar() == war && faction.getWar() == war) {
      
    }
  }
}
