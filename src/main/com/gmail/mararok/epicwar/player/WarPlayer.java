/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.language.Language;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.faction.Faction;

public class WarPlayer {
  private int id;
  private Faction faction;
  private PlayerStats stats;
  
  private Scoreboard gui;
  private Objective sidebar;
  
  private PlayerPosition currentPosition;

  private Player nativePlayer;
  private PlayerManager players;


  public WarPlayer(int id, Player nativePlayer, PlayerManager players) {
    this.nativePlayer = nativePlayer;
    this.players = players;
  }

  void setData(Faction faction, PlayerStats stats) {
    this.id = id;
    this.faction = faction;
    this.stats = stats;
    currentPosition = new PlayerPosition(this);
  }
  
  public void updatePosition(PlayerMoveEvent event) {
    currentPosition.update(event);
  }
  
  /*private void initStatsGUI() {
    ScoreboardManager sbm = Bukkit.getScoreboardManager();
    GUI = sbm.getNewScoreboard();
    Sidebar = GUI.registerNewObjective("stats", "dummy");
    Sidebar.setDisplayName("Stats");
    Sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
    Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:"))
        .setScore(Info.points);
    Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:"))
        .setScore(Info.kills);
    Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Deaths:"))
        .setScore(Info.deaths);

    getBPlayer().setScoreboard(GUI);

  }*/

  public final void updateLocation(PlayerMoveEvent event) {
    if (hasFaction()) {
      Location currentLoc = event.getTo();
      int x = currentLoc.getBlockX(), y = currentLoc.getBlockY(), z = currentLoc
          .getBlockZ();
      if (LastBlockX != x || LastBlockY != y || LastBlockZ != z) {
        LastBlockX = x;
        LastBlockY = y;
        LastBlockZ = z;
        updateLastSector(event);
        updateLastControlPoint();
      }
    }
  }

  private final void updateLastSector(PlayerMoveEvent event) {
    if (checkIsNotInLastSector()) {
      Sector lastSector = getWar().getSectors().getFromLocation(event.getTo());
      if (lastSector == getWar().getSectors().getByID(0)) {
        if (LastSector != lastSector) {
          LastPoint = getWar().getControlPoints().getByID(0);
          LastSector = lastSector;
          sendMessage("You are on wild");
        }
      } else if (checkSectorIsEnemyCapital(lastSector)) {
        event.setCancelled(true);
        sendMessage("You can't go to enemy faction capital sector");
      } else if (LastSector != lastSector) {
        LastSector = lastSector;
        LastPoint = getWar().getControlPoints().getByID(0);
        sendMessage("Entering Sector: " + LastSector.getInfo().name);
      }
    }
  }

  private final boolean checkIsNotInLastSector() {
    return !LastSector.isPointInner(LastBlockX, LastBlockZ);
  }

  private final boolean checkSectorIsEnemyCapital(Sector lastSector) {
    return getWar().getFactions().isCapital(lastSector)
        && lastSector.getInfo().ownerFactionID != getInfo().factionID;
  }

  private final void updateLastControlPoint() {
    if (!LastPoint.isWithin(this)) {
      LastPoint.removeOccupant(this);
      for (ControlPoint cp : LastSector.getControlPoints()) {
        if (cp.isWithin(this)) {
          cp.addOccupant(this);
          LastPoint = cp;
          return;
        }
      }
      LastPoint = getWar().getControlPoints().getByID(0);
    }
  }

  public void onQuite() {
    if (hasFaction()) {
      getFaction().onMemberServerQuite(this);
    }

    LastPoint.removeOccupant(this);
  }

  public void onKill(WarPlayer victim) {
    Info.points += getWar().getInfo().pointsKill;
    ++Info.kills;
    Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:"))
        .setScore(Info.points);
    Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:"))
        .setScore(Info.kills);
    try {
      PreparedStatement st = DatabaseConnection.get().getCachedQuery(
          SQLID_AddPlayerKill);
      st.setInt(1, getWar().getInfo().pointsKill);
      st.setInt(2, getID());
      st.executeUpdate();
      DatabaseConnection.get().commit();
    } catch (SQLException e) {
      getPlayers().getPlugin().logCriticalException(e);
      DatabaseConnection.get().rollback();
    }
  }

  public void onDeath(WarPlayer killer) {
    Info.points -= getWar().getInfo().pointsDeath;
    if (hasFaction()) {
      getBPlayer().teleport(getFaction().getSpawnLocation());
    }

    ++Info.deaths;
    Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:"))
        .setScore(Info.points);
    Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Deaths:"))
        .setScore(Info.deaths);
    try {
      PreparedStatement st = DatabaseConnection.get().getCachedQuery(
          SQLID_AddPlayerDeath);
      st.setInt(1, getWar().getInfo().pointsDeath);
      st.setInt(2, getID());
      st.executeUpdate();
      DatabaseConnection.get().commit();
    } catch (SQLException e) {
      getPlayers().getPlugin().logCriticalException(e);
      DatabaseConnection.get().rollback();
    }
  }

  public void join2Faction(Faction faction) {
    getFaction().removeMember(this);
    faction.addMember(this);
    LastSector = getFaction().getCapitalSector();
    LastPoint.removeOccupant(this);
    LastPoint = getWar().getControlPoints().getByID(0);
  }

  public void leaveFromFaction() {
    if (!hasFaction()) {
      sendMessage("You must be in faction to leave");
    } else {
      LastPoint.removeOccupant(this);
      LastPoint = getWar().getControlPoints().getByID(0);
      LastSector = getWar().getSectors().getByID(0);

      getFaction().removeMember(this);
      sendMessage("You left from faction " + getFaction().getName());
      getWar().getFactions().getByID(0).addMember(this);
    }
  }

  public void sendMessage(String message) {
    getBPlayer().sendMessage(message);
  }

  public void sendFormatMessage(String formatMessage, Object... args) {
    sendMessage(String.format(formatMessage, args));
  }

  public void sendFormatMessage(Language langFormatMessage, Object... args) {
    sendMessage(String.format(langFormatMessage.toString(), args));
  }

  public void sendMessage(Language langMessage) {
    sendMessage(langMessage.toString());
  }

  public void sendMessage(String[] message) {
    getBPlayer().sendMessage(message);
  }

  public void teleport(Location location) {
    getBPlayer().teleport(location);
  }

  public void teleport2FactionCaptital() {
    getFaction().teleport2Capital(this);
  }

  public void joinToWar(War war) {
    if (isOnWar()) {
      leaveFromWar();
    }

    CurrentWar = war;
    loadInfo();
    getFaction().onMemberServerJoin(this);
    LastSector = getWar().getSectors().getByID(0);
    LastPoint = getWar().getControlPoints().getByID(0);
    LastPoint.addOccupant(this);
    Location location = BukkitPlayer.getLocation();
    updateLocation(new PlayerMoveEvent(BukkitPlayer, location, location));
    initStatsGUI();
  }

  public void leaveFromWar() {
    LastPoint.removeOccupant(this);
    LastPoint = null;
    LastSector = null;
    getFaction().onMemberServerQuite(this);
    CurrentWar = null;
  }

  private void loadInfo() {
    try {
      PreparedStatement st = DatabaseConnection.get().getCachedQuery(
          SQLID_LoadPlayerWarData);
      st.setInt(1, getWar().getID());
      st.setString(2, getName());
      ResultSet results = st.executeQuery();
      PlayerInfo info = getInfo();
      if (results.next()) {
        info.id = results.getInt(1);
        info.factionID = results.getInt(2);
        info.kills = results.getInt(3);
        info.deaths = results.getInt(4);
        info.points = results.getInt(5);
      } else {
        registerAtWar();
      }
    } catch (SQLException e) {
      getPlayers().getPlugin().logSevere(e.toString());
    }
  }

  private void registerAtWar() {
    try {
      PreparedStatement st = DatabaseConnection.get().getCachedQuery(
          SQLID_RegisterPlayerAtWar);
      st.setString(1, getUUID().toString());
      st.setInt(2, getWar().getID());
      st.executeUpdate();
      DatabaseConnection.get().commit();
      ResultSet rs = st.getGeneratedKeys();
      rs.next();
      getInfo().reset();
      getInfo().id = rs.getInt(1);
      getPlugin().logInfo(
          String.format("Registered player %s with id %d at %s world, war: %s",
              getName(), getID(), getWorld().getName(), getWar().getName()));
      sendMessage("Welcome, Your first time at " + getWar().getName() + " war");
    } catch (SQLException e) {
      getPlugin().logCriticalException(e);
      DatabaseConnection.get().rollback();
    }
  }

  public Player getNativePlayer() {
    return nativePlayer;
  }

  public UUID getUUID() {
    return nativePlayer.getUniqueId();
  }

  public Faction getFaction() {
    return faction;
  }

  public boolean hasFaction() {
    return (faction != null);
  }

  public Location getLocation() {
    return nativePlayer.getLocation();
  }

  public PlayerManager getPlayers() {
    return players;
  }
  
  public War getWar() {
    return players.getWar();
  }

  public Sector getSector() {
    return currentPosition.getSector();
  }

  public ControlPoint getControlPoint() {
    return currentPosition.getControlPoint();
  }

  public EpicWarPlugin getPlugin() {
    return getPlayers().getPlugin();
  }
  
  public int getId() {
    return id;
  }
}
