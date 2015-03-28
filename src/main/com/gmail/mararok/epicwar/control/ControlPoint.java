/**
 * EpicWar
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.bukkit.util.UMath;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.util.DataObject;
import com.gmail.mararok.epicwar.util.DescriptionInfo;

public class ControlPoint extends ControlArea {

  private DescriptionInfo description;
  private SubsectorList subsectors;
  
  private Location position;
  private int radius;
  private AreaPower power;
  
  private Sector sector;
  private Faction owner;
  
  private Occupiers occupiers;
  
  public ControlPoint(int id) {
    super(id);
    occupiers = new Occupiers(this);
  }
  
  public Occupiers getOccupiers() {
    return occupiers;
  }
  
  public void addOccupant(WarPlayer occupant) {
    if (occupant.getInfo().factionID == owner.getID()) {
      addOwnerOccupant(occupant);
    } else {
      addEnemyOccupant(occupant);
    }
    occupiers.add(occupant);
  }

  private void addOwnerOccupant(WarPlayer occupant) {
    ++ownerOccupiersAmount;
    sendMessageToNewDefender(occupant);
  }

  private void sendMessageToNewDefender(WarPlayer defender) {
    String defenderMessage = String.format(
        "You are currently defending %1$s point(%2$d / %3$d), A: %4$d D: %5$d",
        getName(), getInfo().power, getInfo().maxPower, enemyOccupiersAmount,
        ownerOccupiersAmount);

    defender.sendMessage(defenderMessage);
  }

  private void addEnemyOccupant(WarPlayer occupant) {
    ++enemyOccupiersAmount;
    sendMessageToNewAttacker(occupant);
  }

  private void sendMessageToNewAttacker(WarPlayer attacker) {
    String attackerMessage = String.format(
        "You are attack %1$s point(%2$d / %3$d), A: %4$d D: %5$d", getName(),
        getInfo().power, getInfo().maxPower, enemyOccupiersAmount,
        ownerOccupiersAmount);
    attacker.sendMessage(attackerMessage);
  }

  public void removeOccupant(WarPlayer occupant) {
    if (occupant.getInfo().factionID == owner.getID()) {
      removeFriendOccupantPlayer(occupant);
    } else {
      removeEnemyOccupantPlayer(occupant);
    }
    occupiers.remove(occupant);
    sendMessageToLeavingOccupant(occupant);
  }

  private void removeFriendOccupantPlayer(WarPlayer warPlayer) {
    --ownerOccupiersAmount;
  }

  private void removeEnemyOccupantPlayer(WarPlayer warPlayer) {
    --enemyOccupiersAmount;
  }

  private void sendMessageToLeavingOccupant(WarPlayer occupant) {
    String leavingMessage = String.format(
        "You are leaving the area of %1$s (%2$d / %3$d), A: %4$d D: %5$d",
        getName(), getInfo().power, getInfo().maxPower, enemyOccupiersAmount,
        ownerOccupiersAmount);
    occupant.sendMessage(leavingMessage);
  }

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
    short[] factionOccupants = new short[controlPoints.getWar().getFactions()
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

  public boolean isWithin(WarPlayer warPlayer) {
    return (int) UMath.getDistance3D(warPlayer.getBlockX(),
        warPlayer.getBlockY(), warPlayer.getBlockZ(), info.centerX,
        info.centerY, info.centerZ) <= info.radius;
  }

  public Location getLocation() {
    Position3D position = info.position;
    return new Location(getControlPoints().getWorld(),position.x,position.y,position.z);
  }

  public Faction setOwner(Faction newOwner) {
    Faction oldOwner = owner;
    owner = newOwner;
    return oldOwner;
  }

  public Faction getOwner() {
    return owner;
  }

  public Sector getSector() {
    return sector;
  }

  public String getDescription() {
    return description.clone();
  }

  @Override
  public War getWar() {
    return sector.getWar();
  }

}
