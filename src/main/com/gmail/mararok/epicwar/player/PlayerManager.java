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
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.controlpoint.ControlPoint;
import com.gmail.mararok.epicwar.sector.Sector;
import com.gmail.mararok.epicwar.sector.WoolsMapMetadata;
import com.gmail.mararok.epicwar.war.WarsManager;


public class PlayerManager {
	private WarsManager Wars;
	
	@SuppressWarnings("unused")
	private PlayerListener PlayersListener;
	
	private HashMap<Player, WarPlayer> DataSet;
	private HashMap<String, WarPlayer> NamesDataSet;
	
	public PlayerManager(WarsManager wars) {
		Wars = wars;
		DataSet = new HashMap<Player, WarPlayer>();
		NamesDataSet = new HashMap<String, WarPlayer>();
		PlayersListener = new PlayerListener(this);
	}
	
	public void onPlayerJoin(Player player) {
		World world = player.getWorld();
		
		if (isWarWorld(world)) {
			WarPlayer warPlayer = new WarPlayer(player,this);
			put(warPlayer);
			warPlayer.joinToWar(getWars().getByWorld(world));
		}
	}
	
	public void onPlayerQuite(Player player) {
		WarPlayer warPlayer = get(player);
		if (isAtWar(warPlayer)) {
			warPlayer.leaveFromWar();
			remove(player);
		}
	}
	
	public void onPlayerChangedWorld(Player player) {
		World world = player.getWorld();
		if (isWarWorld(world)) {
			WarPlayer warPlayer = get(player);
			if (isAtWar(warPlayer)) {
				warPlayer.leaveFromWar();
			} else {
				warPlayer = new WarPlayer(player,this);
				put(warPlayer);
			}
			
			warPlayer.joinToWar(getWars().getByWorld(world));
		} else {
			remove(player);
		}
	}
	
	public void onPlayerDeath(Player victim) {
		WarPlayer warPlayer = get(victim);
		if (isAtWar(warPlayer)) {
			get(victim.getKiller()).onKill(get(victim));
			get(victim).onDeath(get(victim.getKiller()));
		}
	}
	
	public void onPlayerMove(PlayerMoveEvent event) {
		WarPlayer warPlayer = get(event.getPlayer());
		if (isAtWar(warPlayer)) {
			warPlayer.updateLocation(event);
		}
	}
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		WarPlayer warPlayer = get(event.getPlayer());
		if (isAtWar(warPlayer) && warPlayer.hasFaction()) {
			switch (event.getAction()) {
			case LEFT_CLICK_BLOCK:
				if (event.getClickedBlock().hasMetadata("woolsMapMetadata")) {
					WoolsMapMetadata meta = (WoolsMapMetadata)event.getClickedBlock().getMetadata("woolsMapMetadata").get(0);
					Sector sector = warPlayer.getWar().getSectors().getByID(meta.SectorID);
					String[] messages = new String[4];
					messages[0] = "Sector Info";
					messages[1] = "Name: "+sector.getName();
					messages[2] = "Desc: "+sector.getInfo().desc;
					messages[3] = "Owner: "+sector.getOwner().getName();
					warPlayer.sendMessage(messages);
				}
				break;
			case RIGHT_CLICK_BLOCK:
				if (event.getClickedBlock().hasMetadata("woolsMapMetadata")) {
					WoolsMapMetadata meta = (WoolsMapMetadata)event.getClickedBlock().getMetadata("woolsMapMetadata").get(0);
					Sector sector = warPlayer.getWar().getSectors().getByID(meta.SectorID);
					List<ControlPoint> points = sector.getControlPoints();
					for (ControlPoint point : points) {
						if (point.getOwner() == warPlayer.getFaction()) {
							warPlayer.teleport(point.getLocation());
							return;
						}
					}
					warPlayer.sendMessage("You can't teleport to "+sector.getName()+", because your faction hasn't any points there");
				}
				break;
			default:
				break;
			
			}
		}
	}
	
	public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
		WarPlayer warPlayer = get(event.getNamedPlayer());
		if  (isAtWar(warPlayer) ) {
			ChatColor factionColor = warPlayer.getFaction().getChatColor();
			String name = event.getNamedPlayer().getName();
			event.setTag(factionColor+name);
		}
	}
	
	public void onServerReload(Player[] playersOnline) {
		for (Player player : playersOnline) {
			if (isWarWorld(player.getWorld())) {
				onPlayerJoin(player);
			}
		}
	}
	
	private void put(WarPlayer player) {
		DataSet.put(player.getBPlayer(),player);
		NamesDataSet.put(player.getName(),player);
	}
	
	private void remove(Player player) {
		DataSet.remove(player);
		NamesDataSet.remove(player.getName());
	}
	
	public WarPlayer get(Player player) {
		return DataSet.get(player);
	}
	
	public WarPlayer getByName(String name) {
		return NamesDataSet.get(name);
	}
	
	public boolean isAtWar(WarPlayer warPlayer) {
		return warPlayer != null;
	}
	
	public boolean isWarWorld(World world) {
		return getWars().isExists(world);
	}
	
	public EpicWarPlugin getPlugin() {
		return Wars.getPlugin();
	}
	
	public WarsManager getWars() {
		return Wars;
	}

	public void dump2File() throws IOException {
		Path dumpPath = Paths.get(getPlugin().getDataDumpPath());
		if (!Files.isDirectory(dumpPath)) {
			Files.createDirectory(dumpPath);
		}
		
		PrintWriter writer = new PrintWriter(Files.newBufferedWriter(
				dumpPath.resolve("Players"+"Dump.txt"),
				StandardCharsets.UTF_8));
		writer.println("[EpicWar] Players dump");
		for (WarPlayer pi : DataSet.values()) {
			writer.println(pi);
		}
		writer.println("[EpicWar] Players end dump");
		writer.close();
	}
	
	public void dispose() {}
}
