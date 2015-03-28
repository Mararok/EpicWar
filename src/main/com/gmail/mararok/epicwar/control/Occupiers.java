/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.epicwar.control.event.ControlAreaEvent;
import com.gmail.mararok.epicwar.control.event.PlayerAttackingControlAreaEvent;
import com.gmail.mararok.epicwar.control.event.PlayerDefendingControlAreaEvent;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class Occupiers {
  private int ownerOccupiersAmount;
  private int enemyOccupiersAmount;
  private List<WarPlayer> players = new LinkedList<WarPlayer>();
  
  private ControlArea controlArea;
  
  public Occupiers(ControlArea controlArea) {
    this.controlArea = controlArea;
  }
  
  public void addOccupant(WarPlayer occupant) {
    if (isOwnerOccupant(occupant)) {
      addOwnerOccupant(occupant);
    } else {
      addEnemyOccupant(occupant);
    }
    
    players.add(occupant);
  }

  private void addOwnerOccupant(WarPlayer occupant) {
    ++ownerOccupiersAmount;
    PlayerDefendingControlAreaEvent event = new PlayerDefendingControlAreaEvent(occupant,controlArea);
    sendEvent(event);
  }

  /*private void sendMessageToNewDefender(WarPlayer defender) {
    String defenderMessage = String.format(
        "You are currently defending %1$s point(%2$d / %3$d), A: %4$d D: %5$d",
        getName(), getInfo().power, getInfo().maxPower, enemyOccupiersAmount,
        ownerOccupiersAmount);

    defender.sendMessage(defenderMessage);
  }*/

  

  private void addEnemyOccupant(WarPlayer occupant) {
    ++enemyOccupiersAmount;
    PlayerAttackingControlAreaEvent event = new PlayerAttackingControlAreaEvent(occupant,controlArea);
    sendEvent(event);
  }

  /*private void sendMessageToNewAttacker(WarPlayer attacker) {
    String attackerMessage = String.format(
        "You are attack %1$s point(%2$d / %3$d), A: %4$d D: %5$d", getName(),
        getInfo().power, getInfo().maxPower, enemyOccupiersAmount,
        ownerOccupiersAmount);
    attacker.sendMessage(attackerMessage);
  }*/

  private void sendEvent(ControlAreaEvent event) {
    
  }
  
  public void removeOccupant(WarPlayer occupant) {
    if (isOwnerOccupant(occupant)) {
      removeFriendOccupantPlayer(occupant);
    } else {
      removeEnemyOccupantPlayer(occupant);
    }
    occupiers.remove(occupant);
    sendMessageToLeavingOccupant(occupant);
  }

  
  public boolean isOwnerOccupant(WarPlayer occupant) {
    return occupant.getFaction() == controlArea.getOwner();
  }
  
  private void removeFriendOccupantPlayer(WarPlayer warPlayer) {
    --ownerOccupiersAmount;
  }

  private void removeEnemyOccupantPlayer(WarPlayer warPlayer) {
    --enemyOccupiersAmount;
  }

  /*private void sendMessageToLeavingOccupant(WarPlayer occupant) {
    String leavingMessage = String.format(
        "You are leaving the area of %1$s (%2$d / %3$d), A: %4$d D: %5$d",
        getName(), getInfo().power, getInfo().maxPower, enemyOccupiersAmount,
        ownerOccupiersAmount);
    occupant.sendMessage(leavingMessage);
  }*/

  public void update() {
    if (isUnderSige()) {
      int subedPower = (enemyOccupiersAmount * getPowerPerAttacker() - ownerOccupiersAmount
          * getPowerPerDefender());
      subPower(subedPower);
      updateWool();
      sendMessageToOccupiers();

      if (canCapture()) {
        capture();
      }

    } else if (info.power < info.maxPower && ownerOccupiersAmount > 0) {
      addPower((ownerOccupiersAmount - enemyOccupiersAmount)
          * getPowerPerDefender());
      updateWool();
      String defenderMessage = "You are currently defending " + getName()
          + " power: " + info.power + " / " + info.maxPower;
      for (WarPlayer player : occupiers) {
        player.sendMessage(defenderMessage);
      }
    }
  }

  private void sendMessageToOccupiers() {
    String defenderMessage = String.format(
        "You are currently defending %1$s point(%2$d / %3$d), A: %4$d D: %5$d",
        getName(), getInfo().power, getInfo().maxPower, enemyOccupiersAmount,
        ownerOccupiersAmount);

    String enemyMessage = String.format(
        "You are capturing %1$s point(%2$d / %3$d), A: %4$d D: %5$d",
        getName(), getInfo().power, getInfo().maxPower, enemyOccupiersAmount,
        ownerOccupiersAmount);

    for (WarPlayer player : occupiers) {
      if (player.getInfo().factionID == info.ownerFactionID) {
        player.sendMessage(defenderMessage);
      } else {
        player.sendMessage(enemyMessage);
      }
    }
  }


  public boolean isUnderSige() {
    return ownerOccupiersAmount < enemyOccupiersAmount;
  }

  public boolean canCapture() {
    return (info.power == 0);
  }

  private void capture() {
    OccupantInfo newOccupant = findNewFactionOccupant();

    enemyOccupiersAmount -= newOccupant.amount;
    enemyOccupiersAmount += ownerOccupiersAmount;

    info.ownerFactionID = newOccupant.factionID;
    ownerOccupiersAmount = newOccupant.amount;

    info.power = 1;
    try {
      PreparedStatement st = DatabaseConnection.get().prepareQuery(
          "UPDATE ew_ControlPoints SET ownerID = ? WHERE id = ?");
      st.setInt(1, info.ownerFactionID);
      st.setInt(2, getID());
      st.executeUpdate();
      DatabaseConnection.get().commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    String newOwnerName = controlPoints.getWar().getFactions()
        .getByID(info.ownerFactionID).getName();
    for (WarPlayer player : occupiers) {
      if (player.getInfo().factionID == info.ownerFactionID) {
        player.sendMessage("Your faction captured " + getName() + " point");
      } else {
        player.sendMessage("Faction" + newOwnerName + " captured " + getName()
            + " point");
      }
    }
    controlPoints.onCapture(this);
  }

  private OccupantInfo findNewFactionOccupant() {
    short[] factionOccupants = new short[controlArea.getWar().getFactions()
        .size() + 1];

    for (WarPlayer occupant : occupiers) {
      ++factionOccupants[occupant.getInfo().factionID];
    }
    OccupantInfo factionOccupant = new OccupantInfo(1);
    factionOccupant.amount = factionOccupants[1];

    for (int i = 2; i < factionOccupants.length; ++i) {
      if (factionOccupant.amount < factionOccupants[i]) {
        factionOccupant.amount = factionOccupants[i];
        factionOccupant.factionID = i;
      }
    }

    return factionOccupant;
  }
  
}
