/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player;

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
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.Sector;
import com.gmail.mararok.epicwar.faction.Faction;

public class PlayerManager {
  private Map<Player, WarPlayer> players;
  
  private PlayerDao playerDao;
  private War war;

  public PlayerManager(PlayerDao playerDao, War war) {
    players = HashObjObjMaps.newMutableMap();
    this.playerDao = playerDao;
    this.war = war;
  }

  public void onPlayerJoined(Player player) {
    if (war.getWorld() == player.getWorld() && get(player) == null) {
      WarPlayer warPlayer = new WarPlayer(id, nativePlayer, players);
      warPlayer.joinToWar(war);
    }
  }
  
  public void onPlayerSelectedFaction(Player player, Faction faction) {
    
  }
  
  private WarPlayer newPlayer(Player player) {
    WarPlayer warPlayer = new WarPlayer(player,this);
    players.put(player, warPlayer);
    
    return warPlayer;
  }
  
  public void onLeft(Player player) {
    War war = getWars().getByWorld(player.getWorld());
    if (war != null) {
      WarPlayer warPlayer = get(player);
      warPlayer.leaveFromWar();
      
      dataSet.remove(player);
      namesDataSet.remove(player.getName());
    }
  }
  

  public void onPlayerChangedWorld(Player player) {
    World world = player.getWorld();
    if (isWarWorld(world)) {
      WarPlayer warPlayer = get(player);
      if (isAtWar(warPlayer)) {
        warPlayer.leaveFromWar();
      } else {
        warPlayer = new WarPlayer(player, this);
        put(warPlayer);
      }

      warPlayer.joinToWar(getWars().getByWorld(world));
    } else {
      remove(player);
    }
  }

  public void onKill(Player killer, Player victim) {
    if (killer.getType() == EntityType.PLAYER && victim.getType() == killer.getType()) {
      WarPlayer warKiller = get(killer);
      WarPlayer warVictim = get(victim);
      if (warKiller != null && warVictim != null) {
        warKiller.onKill(warVictim);
        warVictim.onDeath(warKiller);
      }
    }
  }

  public void onPlayerMove(PlayerMoveEvent event) {
    WarPlayer warPlayer = get(event.getPlayer());
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

  public void onServerReload(Player[] playersOnline) {
    for (Player player : playersOnline) {
      if (isWarWorld(player.getWorld())) {
        onPlayerJoin(player);
      }
    }
  }

  public WarPlayer get(Player player) {
    return dataSet.get(player);
  }

  public WarPlayer getByName(String name) {
    return namesDataSet.get(name);
  }

  public WarManager getWars() {
    return wars;
  }
  
  public EpicWarPlugin getPlugin() {
    return wars.getPlugin();
  }

  public void dump2File() throws IOException {
    Path dumpPath = Paths.get(getPlugin().getDataDumpPath());
    if (!Files.isDirectory(dumpPath)) {
      Files.createDirectory(dumpPath);
    }

    PrintWriter writer = new PrintWriter(Files.newBufferedWriter(
        dumpPath.resolve("Players" + "Dump.txt"), StandardCharsets.UTF_8));
    writer.println("[EpicWar] Players dump");
    for (WarPlayer pi : dataSet.values()) {
      writer.println(pi);
    }
    writer.println("[EpicWar] Players end dump");
    writer.close();
  }

  public void dispose() {
  }

}
