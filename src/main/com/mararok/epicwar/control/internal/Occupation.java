/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import java.util.LinkedList;
import java.util.List;

import com.mararok.epicwar.control.ControlAreaPower;
import com.mararok.epicwar.control.event.ControlAreaEvent;
import com.mararok.epicwar.control.event.PlayerAttackingControlAreaEvent;
import com.mararok.epicwar.control.event.PlayerDefendingControlAreaEvent;
import com.mararok.epicwar.control.event.PlayerLeavingControlAreaEvent;
import com.mararok.epicwar.control.point.internal.ControlPointImpl;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.faction.FactionManager;
import com.mararok.epicwar.player.WarPlayer;

public class Occupation {
  private int defenders;
  private int attackers;
  private List<WarPlayer> players = new LinkedList<WarPlayer>();

  private ControlPointImpl controlPoint;

  public Occupation(ControlPointImpl controlPoint) {
    this.controlPoint = controlPoint;
  }

  public void addPlayer(WarPlayer player) {
    if (isDefender(player)) {
      addDefender(player);
    } else {
      addAttacker(player);
    }

    players.add(player);
  }

  private void addDefender(WarPlayer player) {
    ++defenders;
    dispatchEvent(new PlayerDefendingControlAreaEvent(player, controlPoint));
  }

  private void addAttacker(WarPlayer occupant) {
    ++attackers;
    dispatchEvent(new PlayerAttackingControlAreaEvent(occupant, controlPoint));
  }

  public void removePlayer(WarPlayer player) {
    if (isDefender(player)) {
      removeDefender(player);
    } else {
      removeAttacker(player);
    }

    players.remove(player);
    dispatchEvent(new PlayerLeavingControlAreaEvent(player, controlPoint));
  }

  private void removeDefender(WarPlayer player) {
    --defenders;
  }

  private void removeAttacker(WarPlayer player) {
    --defenders;
  }

  public boolean isDefender(WarPlayer player) {
    return player.getFaction() == controlPoint.getOwner();
  }

  public void update() {
    int defendersPower = getDefendersPower();
    int attackersPower = getAttackersPower();

    ControlAreaPower power = controlPoint.getPower();
    power.set(power.getCurrent() + (defendersPower - attackersPower));
    if (power.canCapture()) {
      capture();
    }

  }

  public int getDefendersPower() {
    return defenders * controlPoint.getWar().getSettings().controlPoint.powerDefender;
  }

  public int getAttackersPower() {
    return attackers * controlPoint.getWar().getSettings().controlPoint.powerAttacker;
  }

  private void capture() {
    Faction newOwner = findNewOwner();
    controlPoint.setOwner(newOwner);
    controlPoint.getWar();
  }

  private Faction findNewOwner() {
    FactionManager factionManager = controlPoint.getWar().getFactionManager();
    short[] factionOccupants = new short[factionManager.findAll().size() + 1];

    for (WarPlayer occupant : players) {
      ++factionOccupants[occupant.getFaction().getId()];
    }

    short newDefenders = factionOccupants[1];
    int newOwnerId = 1;
    for (int i = 2; i < factionOccupants.length; ++i) {
      if (newDefenders < factionOccupants[i]) {
        newDefenders = factionOccupants[i];
        newOwnerId = i;
      }
    }

    attackers = defenders - (attackers - newDefenders);
    defenders = newDefenders;

    return factionManager.findById(newOwnerId);
  }

  /*
   * private void capture() {
   * Faction faction = findNewOwner();
   * 
   * enemyOccupiersAmount -= newOccupant.amount;
   * enemyOccupiersAmount += ownerOccupiersAmount;
   * 
   * info.ownerFactionID = newOccupant.factionID;
   * ownerOccupiersAmount = newOccupant.amount;
   * 
   * info.power = 1;
   * try {
   * PreparedStatement st = DatabaseConnection.get().prepareQuery("UPDATE ew_ControlPoints SET ownerID = ? WHERE id = ?");
   * st.setInt(1, info.ownerFactionID);
   * st.setInt(2, getID());
   * st.executeUpdate();
   * DatabaseConnection.get().commit();
   * } catch (SQLException e) {
   * e.printStackTrace();
   * }
   * String newOwnerName = controlPoints.getWar().getFactionManager().getByID(info.ownerFactionID).getName();
   * for (WarPlayerImpl player : occupiers) {
   * if (player.getInfo().factionID == info.ownerFactionID) {
   * player.sendMessage("Your faction captured " + getName() + " point");
   * } else {
   * player.sendMessage("Faction" + newOwnerName + " captured " + getName() + " point");
   * }
   * }
   * controlPoints.onCapture(this);
   * }
   * 
   */

  private void dispatchEvent(ControlAreaEvent event) {
    controlPoint.getWar().getPlugin().getEventManager().dispatchEvent(event);
  }
}
