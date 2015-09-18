/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.WarManager;
import com.gmail.mararok.epicwar.control.impl.ControlPointImpl;
import com.gmail.mararok.epicwar.control.impl.SectorImpl;
import com.gmail.mararok.epicwar.faction.impl.FactionImpl;
import com.gmail.mararok.epicwar.impl.WarImpl;
import com.gmail.mararok.epicwar.player.WarPlayer;

public class PlayerManagerImpl {
  private Map<Player, WarPlayer> players;
  
  private PlayerDAO playerDAO;
  private War war;

  public PlayerManagerImpl(PlayerDAO playerDAO, War war) {
    players = HashObjObjMaps.newMutableMap();
    this.playerDAO = playerDAO;
    this.war = war;
  }

  public void onPlayerJoined(Player player) {
    if (war.getWorld() == player.getWorld() && get(player) == null) {
      WarPlayerImpl warPlayer = new WarPlayerImpl(id, nativePlayer, players);
      warPlayer.joinToWar(war);
    }
  }
  
  public void onPlayerSelectedFaction(Player player, FactionImpl faction) {
    
  }
  
  private WarPlayerImpl newPlayer(Player player) {
    WarPlayerImpl warPlayer = new WarPlayerImpl(player,this);
    players.put(player, warPlayer);
    
    return warPlayer;
  }
  
  public void onLeft(Player player) {
    WarImpl war = getWars().getByWorld(player.getWorld());
    if (war != null) {
      WarPlayerImpl warPlayer = get(player);
      warPlayer.leaveFromWar();
      
      dataSet.remove(player);
      namesDataSet.remove(player.getName());
    }
  }
  

  public void onPlayerChangedWorld(Player player) {
    World world = player.getWorld();
    if (isWarWorld(world)) {
      WarPlayerImpl warPlayer = get(player);
      if (isAtWar(warPlayer)) {
        warPlayer.leaveFromWar();
      } else {
        warPlayer = new WarPlayerImpl(player, this);
        put(warPlayer);
      }

      warPlayer.joinToWar(getWars().getByWorld(world));
    } else {
      remove(player);
    }
  }

  public void onKill(Player killer, Player victim) {
    if (killer.getType() == EntityType.PLAYER && victim.getType() == killer.getType()) {
      WarPlayerImpl warKiller = get(killer);
      WarPlayerImpl warVictim = get(victim);
      if (warKiller != null && warVictim != null) {
        warKiller.onKill(warVictim);
        warVictim.onDeath(warKiller);
      }
    }
  }

  public void onPlayerMove(PlayerMoveEvent event) {
    WarPlayerImpl warPlayer = get(event.getPlayer());
    if (isAtWar(warPlayer)) {
      warPlayer.updateLocation(event);
    }
  }

  /*public void onPlayerInteract(PlayerInteractEvent event) {
    WarPlayer warPlayer = get(event.getPlayer());
    if (isAtWar(warPlayer) && warPlayer.hasFaction()) {
      switch (event.getAction()) {
      case LEFT_CLICK_BLOCK:
        if (event.getClickedBlock().hasMetadata("woolsMapMetadata")) {
          WoolsMapMetadata meta = (WoolsMapMetadata) event.getClickedBlock()
              .getMetadata("woolsMapMetadata").get(0);
          Sector sector = warPlayer.getWar().getSectors()
              .getByID(meta.SectorID);
          String[] messages = new String[4];
          messages[0] = "Sector Info";
          messages[1] = "Name: " + sector.getName();
          messages[2] = "Desc: " + sector.getInfo().desc;
          messages[3] = "Owner: " + sector.getOwner().getName();
          warPlayer.sendMessage(messages);
        }
        break;
      case RIGHT_CLICK_BLOCK:
        if (event.getClickedBlock().hasMetadata("woolsMapMetadata")) {
          WoolsMapMetadata meta = (WoolsMapMetadata) event.getClickedBlock()
              .getMetadata("woolsMapMetadata").get(0);
          Sector sector = warPlayer.getWar().getSectors()
              .getByID(meta.SectorID);
          List<ControlPoint> points = sector.getControlPoints();
          for (ControlPoint point : points) {
            if (point.getOwner() == warPlayer.getFaction()) {
              warPlayer.teleport(point.getLocation());
              return;
            }
          }
          warPlayer.sendMessage("You can't teleport to " + sector.getName()
              + ", because your faction hasn't any points there");
        }
        break;
      default:
        break;

      }
    }
  }*/

  public void callEvent(EpicWarEvent event) {
    Bukkit.getServer().getPluginManager().
  }
  
  public void onServerReload(Player[] playersOnline) {
    for (Player player : playersOnline) {
      if (isWarWorld(player.getWorld())) {
        onPlayerJoin(player);
      }
    }
  }

  public WarPlayer get(Player player) {
    return players.get(player);
  }

  public War getWar() {
    return war;
  }
  

  public void dump2File() throws IOException {
    Path dumpPath = Paths.get(getWar().getWarManager().getPlugin().getDataDumpPath());
    if (!Files.isDirectory(dumpPath)) {
      Files.createDirectory(dumpPath);
    }

    PrintWriter writer = new PrintWriter(Files.newBufferedWriter(
        dumpPath.resolve("Players" + "Dump.txt"), StandardCharsets.UTF_8));
    writer.println("[EpicWar] Players dump");
    for (WarPlayer pi : players.values()) {
      writer.println(pi);
    }
    writer.println("[EpicWar] Players end dump");
    writer.close();
  }

  public void dispose() {
  }

}
