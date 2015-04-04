/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.banner.PatternType;

import com.gmail.mararok.bukkit.util.Disposable;
import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class Faction implements Disposable {
  private int id;
  private String name;
  private String shortcut;
  private String description;
  private FactionAppearance appearance;
  private Sector capitalSector;
  
  private List<WarPlayer> onlineMembers;
  private FactionManager factions;

  public Faction(int id, FactionManager factions) {
    this.factions = factions;
    onlineMembers = new LinkedList<WarPlayer>()
  }

  public void addMember(WarPlayer player) {
    FactionDao dao = factions.getDao();
    try {
      PreparedStatement st = db.getCachedQuery(SQLID_SetPlayerFaction);
      st.setInt(1, info.id);
      st.setInt(2, player.getID());
      st.executeUpdate();

      st = db.getCachedQuery(SQLID_AddMember);
      st.setInt(1, info.id);
      st.executeUpdate();

      db.commit();
      player.getInfo().factionID = getID();
      ++info.members;
      onMemberServerJoin(player);
      teleport2Capital(player);
    } catch (SQLException e) {
      db.rollback();
      getFactions().getPlugin().logCriticalException(e);
    }
  }

  public void removeMember(WarPlayer player) {
    DatabaseConnection db = DatabaseConnection.get();
    try {
      PreparedStatement st = db.getCachedQuery(SQLID_SetPlayerFaction);
      st.setInt(1, 0);
      st.setInt(2, player.getID());
      st.executeUpdate();

      st = db.getCachedQuery(SQLID_RemoveMember);
      st.setInt(1, info.id);
      st.executeUpdate();

      db.commit();
      --info.members;
      player.getInfo().factionID = 0;
      player.getBPlayer().setPlayerListName(player.getName());
      onMemberServerQuite(player);
    } catch (SQLException e) {
      factions.getPlugin().logCriticalException(e);
      db.rollback();
    }
  }

  public void onMemberServerJoin(WarPlayer player) {
    player.getBPlayer().setPlayerListName(getInfo().color + player.getName());
    onlineMembers.add(player);
  }

  public void onMemberServerQuite(WarPlayer player) {
    onlineMembers.remove(player);
  }

  public Location getSpawnLocation() {
    return spawnLocation;
  }

  public void setSpawnLocation(Location spawnLocation) {
    spawnLocation = spawnLocation;
    info.spawnX = spawnLocation.getBlockX();
    info.spawnY = spawnLocation.getBlockY();
    info.spawnZ = spawnLocation.getBlockZ();
    try {
      PreparedStatement st = DatabaseConnection.get().getCachedQuery(
          SQLID_SetSpawn);
      st.setInt(1, info.spawnX);
      st.setInt(2, info.spawnY);
      st.setInt(3, info.spawnZ);
      st.setInt(4, info.id);
      st.executeUpdate();
      DatabaseConnection.get().commit();
    } catch (SQLException e) {

      DatabaseConnection.get().rollback();
      e.printStackTrace();
    }
  }

  public Sector getCapitalSector() {
    return capitalSector;
  }
  
  public void setCapitalSector(Sector sector) {
    capitalSector = sector;
  }

  public void teleport2Capital(WarPlayer player) {
    player.getBPlayer().teleport(getSpawnLocation());
  }


  public void onCapturePoint(ControlPoint point) {
    sendFormatMessage2OnlineMembers(Language.FACTION_CAPTURED_POINT,
        point.getName(), point.getSector().getName());
  }

  public void onLostPoint(ControlPoint point) {
    sendFormatMessage2OnlineMembers(Language.FACTION_LOST_POINT,
        point.getName(), point.getSector().getName());
  }

  public void onCaptureSector(Sector sector) {
    addControlledSectors();
    sendFormatMessage2OnlineMembers(Language.FACTION_CAPTURED_SECTOR,
        sector.getName());
  }

  private void addControlledSectors() {
    ++info.controlledSectors;
    updateControlledSectors();
  }

  public void onLostSector(Sector sector) {
    subControlledSectors();
    sendFormatMessage2OnlineMembers(Language.FACTION_LOST_SECTOR,
        sector.getName());
  }

  private void subControlledSectors() {
    --info.controlledSectors;
    updateControlledSectors();
  }

  public void sendFormatMessage2OnlineMembers(Language langMessage,
      Object... args) {
    String message = String.format(langMessage.toString(), args);
    for (WarPlayer member : onlineMembers) {
      member.sendMessage(message);
    }
  }

  public int getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String newName) {
    name = newName;
  }
  
  public String getShortcut() {
    return shortcut;
  }
  
  public void setShortcut(String newShortcut) {
    shortcut = newShortcut.replace(' ','_').toUpperCase();
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String newDescription) {
    description = newDescription;
  }

  public FactionManager getFactions() {
    return factions;
  }

  @Override
  public void dispose() {
    
  }

}
