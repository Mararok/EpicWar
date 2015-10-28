/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction.internal;

import org.bukkit.Location;

import com.gmail.mararok.epiccore.entity.ObservedEntity;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.faction.FactionBannerPattern;
import com.gmail.mararok.epicwar.faction.FactionColor;
import com.gmail.mararok.epicwar.faction.FactionData;

public class FactionImpl extends ObservedEntity implements Faction {
  private String name;
  private String shortcut;
  private String description;
  private FactionColor color;
  private FactionBannerPattern bannerPattern;
  private Location spawnLocation;

  private War war;

  public FactionImpl(FactionData data, War war) {
    super(data.id);
    name = data.name;
    shortcut = data.shortcut;
    description = data.description;
    color = data.color;
    bannerPattern = data.bannerPattern;
    this.spawnLocation = new Location(war.getWorld(), data.spawnPosition.x, data.spawnPosition.y, data.spawnPosition.z);
    this.war = war;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String newName) {
    name = newName;
    onChangeProperty("name", newName);
  }

  @Override
  public String getShortcut() {
    return shortcut;
  }

  @Override
  public void setShortcut(String newShortcut) {
    shortcut = newShortcut;
    onChangeProperty("shortcut", newShortcut);
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String newDescription) {
    description = newDescription;
    onChangeProperty("description", newDescription);
  }

  @Override
  public FactionColor getColor() {
    return color;
  }

  @Override
  public FactionBannerPattern getBannerPattern() {
    return bannerPattern;
  }

  @Override
  public void setBannerPattern(FactionBannerPattern newBannerPattern) {
    bannerPattern = newBannerPattern;
    onChangeProperty("bannerPattern", newBannerPattern.serialize());
  }


  @Override
  public War getWar() {
    return war;
  }

  @Override
  public Location getSpawnLocation() {
    return spawnLocation;
  }

  @Override
  public void setSpawnLocation(Location location) {
    spawnLocation = location;
  }

  /*
   * public void addMember(WarPlayerImpl player) { FactionDao dao = factionManager.getDao(); try { PreparedStatement st =
   * db.getCachedQuery(SQLID_SetPlayerFaction); st.setInt(1, info.id); st.setInt(2, player.getID()); st.executeUpdate();
   * 
   * st = db.getCachedQuery(SQLID_AddMember); st.setInt(1, info.id); st.executeUpdate();
   * 
   * db.commit(); player.getInfo().factionID = getID(); ++info.members; onMemberServerJoin(player); teleport2Capital(player); } catch (SQLException e) {
   * db.rollback(); getFactions().getPlugin().logCriticalException(e); } }
   * 
   * public void removeMember(WarPlayerImpl player) { DatabaseConnection db = DatabaseConnection.get(); try { PreparedStatement st =
   * db.getCachedQuery(SQLID_SetPlayerFaction); st.setInt(1, 0); st.setInt(2, player.getID()); st.executeUpdate();
   * 
   * st = db.getCachedQuery(SQLID_RemoveMember); st.setInt(1, info.id); st.executeUpdate();
   * 
   * db.commit(); --info.members; player.getInfo().factionID = 0; player.getBPlayer().setPlayerListName(player.getName()); onMemberServerQuite(player); } catch
   * (SQLException e) { factionManager.getPlugin().logCriticalException(e); db.rollback(); } }
   * 
   * public void onMemberServerJoin(WarPlayerImpl player) { player.getBPlayer().setPlayerListName(getInfo().color + player.getName());
   * onlineMembers.add(player); }
   * 
   * public void onMemberServerQuite(WarPlayerImpl player) { onlineMembers.remove(player); }
   * 
   * public void teleport2Capital(WarPlayerImpl player) { player.getBPlayer().teleport(getSpawnLocation()); }
   * 
   * 
   * public void onCapturePoint(ControlPointImpl point) { sendFormatMessage2OnlineMembers(Language.FACTION_CAPTURED_POINT, point.getName(),
   * point.getSector().getName()); }
   * 
   * public void onLostPoint(ControlPointImpl point) { sendFormatMessage2OnlineMembers(Language.FACTION_LOST_POINT, point.getName(),
   * point.getSector().getName()); }
   * 
   * public void onCaptureSector(SectorImpl sector) { addControlledSectors(); sendFormatMessage2OnlineMembers(Language.FACTION_CAPTURED_SECTOR,
   * sector.getName()); }
   * 
   * private void addControlledSectors() { ++info.controlledSectors; updateControlledSectors(); }
   * 
   * public void onLostSector(SectorImpl sector) { subControlledSectors(); sendFormatMessage2OnlineMembers(Language.FACTION_LOST_SECTOR, sector.getName()); }
   * 
   * private void subControlledSectors() { --info.controlledSectors; updateControlledSectors(); }
   * 
   * public void sendFormatMessage2OnlineMembers(Language langMessage, Object... args) { String message = String.format(langMessage.toString(), args); for
   * (WarPlayerImpl member : onlineMembers) { member.sendMessage(message); } }
   * 
   */

}
