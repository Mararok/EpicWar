/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import java.util.LinkedList;
import java.util.List;

import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.event.ControlAreaEvent;
import com.mararok.epicwar.control.event.PlayerAttackingControlAreaEvent;
import com.mararok.epicwar.control.event.PlayerDefendingControlAreaEvent;
import com.mararok.epicwar.control.event.PlayerLeavingControlAreaEvent;
import com.mararok.epicwar.control.point.internal.ControlPointImpl;
import com.mararok.epicwar.control.point.internal.ControlPointManagerImpl;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.faction.FactionManager;
import com.mararok.epicwar.faction.internal.FactionManagerImpl;
import com.mararok.epicwar.player.WarPlayer;

public class Occupation {
  private int defenders;
  private List<WarPlayer> players = new LinkedList<WarPlayer>();

  private ControlPointImpl controlPoint;

  public Occupation(ControlPointImpl controlPoint) {
    this.controlPoint = controlPoint;
  }

  public void addPlayer(WarPlayer player) {
    if (!players.contains(player)) {
      if (isDefender(player)) {
        ++defenders;
        dispatchEvent(new PlayerDefendingControlAreaEvent(player, controlPoint));
      } else {
        ControlPointManagerImpl internal = (ControlPointManagerImpl) controlPoint.getWar().getControlPointManager();
        internal.addOccupied(controlPoint);
        dispatchEvent(new PlayerAttackingControlAreaEvent(player, controlPoint));
      }

      players.add(player);
    }
  }

  public void removePlayer(WarPlayer player) {
    if (players.contains(player)) {
      if (isDefender(player)) {
        --defenders;
      } else {
        ControlPointManagerImpl internal = (ControlPointManagerImpl) controlPoint.getWar().getControlPointManager();
        internal.removeOccupied(controlPoint);
      }

      players.remove(player);
      dispatchEvent(new PlayerLeavingControlAreaEvent(player, controlPoint));
    }
  }

  public boolean isDefender(WarPlayer player) {
    return player.getFaction() == controlPoint.getOwner();
  }

  public void update() throws Exception {
    int currentPower = controlPoint.getCurrentPower();
    int defendersPower = calculateDefendersPower();
    int attackersPower = calculateAttackersPower();

    controlPoint.setCurrentPower(currentPower + (defendersPower - attackersPower));
    if (controlPoint.canCapture()) {
      capture();
    }

  }

  public int calculateDefendersPower() {
    return defenders * controlPoint.getWar().getSettings().controlPoint.powerDefender;
  }

  public int calculateAttackersPower() {
    boolean[] factionHasAnyConnection = new boolean[FactionManagerImpl.MAX_FACTIONS];
    for (ControlPoint connection : controlPoint.getConnections()) {
      if (connection.getOwner() != null) {
        factionHasAnyConnection[connection.getOwner().getId()] = true;
      }
    }

    int attackers = 0;
    for (WarPlayer player : players) {
      if (!isDefender(player) && factionHasAnyConnection[player.getFaction().getId()]) {
        ++attackers;
      }
    }

    return attackers * controlPoint.getWar().getSettings().controlPoint.powerAttacker;
  }

  private void capture() throws Exception {
    Faction newOwner = findNewOwner();
    controlPoint.setOwner(newOwner);
    controlPoint.getWar().getControlPointManager().update(controlPoint);
  }

  private Faction findNewOwner() {
    FactionManager factionManager = controlPoint.getWar().getFactionManager();
    int[] factionOccupants = new int[FactionManagerImpl.MAX_FACTIONS];

    for (WarPlayer occupant : players) {
      ++factionOccupants[occupant.getFaction().getId()];
    }

    int newDefenders = factionOccupants[1];
    int newOwnerId = 1;
    for (int i = 2; i < factionOccupants.length; ++i) {
      if (newDefenders < factionOccupants[i]) {
        newDefenders = factionOccupants[i];
        newOwnerId = i;
      }
    }

    defenders = newDefenders;
    return factionManager.findById(newOwnerId);
  }

  private void dispatchEvent(ControlAreaEvent event) {
    controlPoint.getWar().getPlugin().getEventManager().dispatchEvent(event);
  }
}
