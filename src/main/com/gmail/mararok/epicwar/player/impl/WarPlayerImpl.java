/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mararok.bukkit.util.entity.ObservedEntity;
import com.gmail.mararok.epicwar.War;

import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.control.Subsector;

import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.PlayerStats;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class WarPlayerImpl extends ObservedEntity implements WarPlayer {
  private Player nativePlayer;
  private PlayerStats stats;
  private Faction faction;
  
  private PlayerPosition currentPosition;

  public WarPlayerImpl(int id, Player nativePlayer, Faction faction, PlayerStats stats) {
    super(id);
    this.nativePlayer = nativePlayer;
    this.faction = faction;
    this.stats = stats; 
    currentPosition = new PlayerPosition(this);
  }

  public void updatePosition(PlayerMoveEvent event) {
    currentPosition.update(event);
  }

  /*
   * private void initStatsGUI() { ScoreboardManager sbm = Bukkit.getScoreboardManager(); GUI = sbm.getNewScoreboard(); Sidebar =
   * GUI.registerNewObjective("stats", "dummy"); Sidebar.setDisplayName("Stats"); Sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
   * Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:")) .setScore(Info.points); Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN +
   * "Kills:")) .setScore(Info.kills); Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Deaths:")) .setScore(Info.deaths);
   * 
   * getBPlayer().setScoreboard(GUI);
   * 
   * }
   */

  /*
   * public final void updateLocation(PlayerMoveEvent event) { if (hasFaction()) { Location currentLoc = event.getTo(); int x = currentLoc.getBlockX(), y =
   * currentLoc.getBlockY(), z = currentLoc .getBlockZ(); if (LastBlockX != x || LastBlockY != y || LastBlockZ != z) { LastBlockX = x; LastBlockY = y;
   * LastBlockZ = z; updateLastSector(event); updateLastControlPoint(); } } }
   * 
   * private final void updateLastSector(PlayerMoveEvent event) { if (checkIsNotInLastSector()) { Sector lastSector =
   * getWar().getSectors().getFromLocation(event.getTo()); if (lastSector == getWar().getSectors().getByID(0)) { if (LastSector != lastSector) { LastPoint =
   * getWar().getControlPoints().getByID(0); LastSector = lastSector; sendMessage("You are on wild"); } } else if (checkSectorIsEnemyCapital(lastSector)) {
   * event.setCancelled(true); sendMessage("You can't go to enemy faction capital sector"); } else if (LastSector != lastSector) { LastSector = lastSector;
   * LastPoint = getWar().getControlPoints().getByID(0); sendMessage("Entering Sector: " + LastSector.getInfo().name); } } }
   * 
   * private final boolean checkIsNotInLastSector() { return !LastSector.isPointInner(LastBlockX, LastBlockZ); }
   * 
   * private final boolean checkSectorIsEnemyCapital(Sector lastSector) { return getWar().getFactions().isCapital(lastSector) &&
   * lastSector.getInfo().ownerFactionID != getInfo().factionID; }
   * 
   * private final void updateLastControlPoint() { if (!LastPoint.isWithin(this)) { LastPoint.removeOccupant(this); for (ControlPoint cp :
   * LastSector.getControlPoints()) { if (cp.isWithin(this)) { cp.addOccupant(this); LastPoint = cp; return; } } LastPoint =
   * getWar().getControlPoints().getByID(0); } }
   */

  /*
   * public void onQuite() { if (hasFaction()) { getFaction().onMemberServerQuite(this); }
   * 
   * //LastPoint.removeOccupant(this); }
   * 
   * public void onKill(WarPlayerImpl victim) { Info.points += getWar().getInfo().pointsKill; ++Info.kills;
   * Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:")) .setScore(Info.points); Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN +
   * "Kills:")) .setScore(Info.kills); try { PreparedStatement st = DatabaseConnection.get().getCachedQuery( SQLID_AddPlayerKill); st.setInt(1,
   * getWar().getInfo().pointsKill); st.setInt(2, getID()); st.executeUpdate(); DatabaseConnection.get().commit(); } catch (SQLException e) {
   * getPlayerManager().getPlugin().logCriticalException(e); DatabaseConnection.get().rollback(); } }
   * 
   * public void onDeath(WarPlayerImpl killer) { Info.points -= getWar().getInfo().pointsDeath; if (hasFaction()) {
   * getBPlayer().teleport(getFaction().getSpawnLocation()); }
   * 
   * ++Info.deaths; Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Points:")) .setScore(Info.points);
   * Sidebar.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Deaths:")) .setScore(Info.deaths); try { PreparedStatement st =
   * DatabaseConnection.get().getCachedQuery( SQLID_AddPlayerDeath); st.setInt(1, getWar().getInfo().pointsDeath); st.setInt(2, getID()); st.executeUpdate();
   * DatabaseConnection.get().commit(); } catch (SQLException e) { getPlayerManager().getPlugin().logCriticalException(e); DatabaseConnection.get().rollback();
   * } }
   * 
   * public void join2Faction(FactionImpl faction) { getFaction().removeMember(this); faction.addMember(this); LastSector = getFaction().getCapitalSector();
   * LastPoint.removeOccupant(this); LastPoint = getWar().getControlPoints().getByID(0); }
   * 
   * public void leaveFromFaction() { if (!hasFaction()) { sendMessage("You must be in faction to leave"); } else { LastPoint.removeOccupant(this); LastPoint =
   * getWar().getControlPoints().getByID(0); LastSector = getWar().getSectorManager().getByID(0);
   * 
   * getFaction().removeMember(this); sendMessage("You left from faction " + getFaction().getName()); getWar().getFactionManager().getByID(0).addMember(this); }
   * }
   * 
   * public void sendMessage(String message) { getBPlayer().sendMessage(message); }
   * 
   * public void sendFormatMessage(String formatMessage, Object... args) { sendMessage(String.format(formatMessage, args)); }
   * 
   * public void sendMessage(String[] message) { getBPlayer().sendMessage(message); }
   * 
   * public void teleport(Location location) { getBPlayer().teleport(location); }
   * 
   * public void teleport2FactionCaptital() { getFaction().teleport2Capital(this); }
   * 
   * public void joinToWar(WarImpl war) { if (isOnWar()) { leaveFromWar(); }
   * 
   * CurrentWar = war; loadInfo(); getFaction().onMemberServerJoin(this); LastSector = getWar().getSectorManager().getByID(0); LastPoint =
   * getWar().getControlPoints().getByID(0); LastPoint.addOccupant(this); Location location = BukkitPlayer.getLocation(); updateLocation(new
   * PlayerMoveEvent(BukkitPlayer, location, location)); initStatsGUI(); }
   * 
   * public void leaveFromWar() { LastPoint.removeOccupant(this); LastPoint = null; LastSector = null; getFaction().onMemberServerQuite(this); CurrentWar =
   * null; }
   * 
   * private void loadInfo() { try { PreparedStatement st = DatabaseConnection.get().getCachedQuery( SQLID_LoadPlayerWarData); st.setInt(1, getWar().getID());
   * st.setString(2, getName()); ResultSet results = st.executeQuery(); PlayerData info = getInfo(); if (results.next()) { info.id = results.getInt(1);
   * info.factionID = results.getInt(2); info.kills = results.getInt(3); info.deaths = results.getInt(4); info.points = results.getInt(5); } else {
   * registerAtWar(); } } catch (SQLException e) { getPlayerManager().getPlugin().logSevere(e.toString()); } }
   * 
   * private void registerAtWar() { try { PreparedStatement st = DatabaseConnection.get().getCachedQuery( SQLID_RegisterPlayerAtWar); st.setString(1,
   * getUUID().toString()); st.setInt(2, getWar().getID()); st.executeUpdate(); DatabaseConnection.get().commit(); ResultSet rs = st.getGeneratedKeys();
   * rs.next(); getInfo().reset(); getInfo().id = rs.getInt(1); getPlugin().logInfo( String.format("Registered player %s with id %d at %s world, war: %s",
   * getName(), getID(), getWorld().getName(), getWar().getName())); sendMessage("Welcome, Your first time at " + getWar().getName() + " war"); } catch
   * (SQLException e) { getPlugin().logCriticalException(e); DatabaseConnection.get().rollback(); } }
   * 
   */

  @Override
  public Player getNativePlayer() {
    return nativePlayer;
  }

  @Override
  public PlayerStats getStats() {
    return stats;
  }
  
  @Override
  public Faction getFaction() {
    return faction;
  }
  
  public void setFaction(Faction newFaction) {
    faction = newFaction;
    onChangeProperty("factionId",faction.getId());
  }

  @Override
  public Sector getSector() {
    return currentPosition.getSector();
  }

  @Override
  public ControlPoint getControlPoint() {
    return currentPosition.getControlPoint();
  }

  @Override
  public Subsector getSubsector() {
    return currentPosition.getSubsector();
  }
  
  @Override
  public War getWar() {
    return faction.getWar();
  }

}
