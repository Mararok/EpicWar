/**
 * EpicWar
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.controlpoint;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.sector.Sector;
import com.gmail.mararok.epicwar.utility.DataObject;
import com.gmail.mararok.epicwar.utility.NameConverter;
import com.gmail.mararok.epicwar.utility.RegionsUtlil;
import com.gmail.mararok.epicwar.utility.UMath;
import com.gmail.mararok.epicwar.utility.database.DB;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ControlPoint implements DataObject<ControlPointInfo> {
	public static final String REGION_PREFIX = "cp";
	
	private ControlPointInfo Info;
	private ControlPointsManager ControlPoints;
	
	private short FriendOccupiersAmount;
	private short EnemyOccupiersAmount;
	private List<WarPlayer> Occupiers;
	private Block[] PowerBlocks;
	private int PowerPerPowerBlock;
	
	public ControlPoint(ControlPointInfo info, ControlPointsManager controlPoints) {
		Info = info;
		ControlPoints = controlPoints;
	}
	
	public void init() {
		createRegionIfNotExists();
		initPowerBlocks();
		Occupiers = new LinkedList<WarPlayer>();
	}
	
	private void createRegionIfNotExists() {
		if (getRegion() == null) {
			int radius = getInfo().radius+2;
			
			BlockVector position1 = new BlockVector(
					getInfo().centerX+radius,
					getInfo().centerY+radius,
					getInfo().centerZ+radius);
			BlockVector position2 = new BlockVector(
					getInfo().centerX-radius,
					getInfo().centerY-radius,
					getInfo().centerZ-radius);
			
			RegionsUtlil.createRegion(getPoints().getRegions(),
				NameConverter.nameToRegionName(ControlPoint.REGION_PREFIX,getName()), 
				position1, position2
			);
		}
	}
	
	private void initPowerBlocks() {
		PowerBlocks = new Block[8];
		PowerPerPowerBlock = getInfo().maxPower/8;
		Block centerBlock = ControlPoints.getWorld().getBlockAt(getInfo().centerX,getInfo().centerY,getInfo().centerZ);
		
		if (centerBlock.getType() != Material.GLOWSTONE) {
			centerBlock.setType(Material.GLOWSTONE);
		}
		
		PowerBlocks[0] = centerBlock.getRelative(BlockFace.NORTH);
		PowerBlocks[1] = centerBlock.getRelative(BlockFace.NORTH_EAST);
		PowerBlocks[2] = centerBlock.getRelative(BlockFace.EAST);
		PowerBlocks[3] = centerBlock.getRelative(BlockFace.SOUTH_EAST);
		PowerBlocks[4] = centerBlock.getRelative(BlockFace.SOUTH);
		PowerBlocks[5] = centerBlock.getRelative(BlockFace.SOUTH_WEST);
		PowerBlocks[6] = centerBlock.getRelative(BlockFace.WEST);
		PowerBlocks[7] = centerBlock.getRelative(BlockFace.NORTH_WEST);
		
		DyeColor color = getOwner().getDyeColor();
		
		for (int i = 0; i < PowerBlocks.length;++i) {
			if (PowerBlocks[i].getType() != Material.WOOL) {
				PowerBlocks[i].setType(Material.WOOL);
			}
			
			PowerBlocks[i].setData(color.getWoolData());
		}
	}
	
	public void addOccupantPlayer(WarPlayer occupant) {
		if (occupant.getInfo().factionID == Info.ownerFactionID) {
			addFirendOccupantPlayer(occupant);
		} else {
			addEnemyOccupantPlayer(occupant);
		}
		Occupiers.add(occupant);
	}
	
	private void addFirendOccupantPlayer(WarPlayer occupant) {
		++FriendOccupiersAmount;
		sendMessageToNewDefender(occupant);
	}
	
	private void sendMessageToNewDefender(WarPlayer defender) {
		String defenderMessage = String.format("You are currently defending %1$s point(%2$d / %3$d), A: %4$d D: %5$d",
			getName(),getInfo().power,getInfo().maxPower,EnemyOccupiersAmount,FriendOccupiersAmount);
		
		defender.sendMessage(defenderMessage);
	}
	
	private void addEnemyOccupantPlayer(WarPlayer occupant) {
		++EnemyOccupiersAmount;
		sendMessageToNewAttacker(occupant);
	}
	
	private void sendMessageToNewAttacker(WarPlayer attacker) {
		String attackerMessage = String.format("You are attack %1$s point(%2$d / %3$d), A: %4$d D: %5$d",
				getName(),getInfo().power,getInfo().maxPower,EnemyOccupiersAmount,FriendOccupiersAmount);
		attacker.sendMessage(attackerMessage);
	}
	
	public void removeOccupantPlayer(WarPlayer occupant) {
		if (occupant.getInfo().factionID == Info.ownerFactionID) {
			removeFriendOccupantPlayer(occupant);
		} else {
			removeEnemyOccupantPlayer(occupant);
		}
		Occupiers.remove(occupant);
		sendMessageToLeavingOccupant(occupant);
	}
	
	private void removeFriendOccupantPlayer(WarPlayer warPlayer) {
		--FriendOccupiersAmount;
	}
	
	private void removeEnemyOccupantPlayer(WarPlayer warPlayer) {
		--EnemyOccupiersAmount;
	}

	private void sendMessageToLeavingOccupant(WarPlayer occupant) {
		String leavingMessage = String.format("You are leaving the area of %1$s (%2$d / %3$d), A: %4$d D: %5$d",
				getName(),getInfo().power,getInfo().maxPower,EnemyOccupiersAmount,FriendOccupiersAmount);
		occupant.sendMessage(leavingMessage);
	}
	
	public void update() {
		if (isUnderSige()) {
			int subedPower = (EnemyOccupiersAmount*getPowerPerAttacker() - FriendOccupiersAmount*getPowerPerDefender());
			subPower(subedPower);
			updateWool();
			sendMessageToOccupiers();
			
			if (canCapture()) {
				capture();
			}
			
		} else if (Info.power < Info.maxPower && FriendOccupiersAmount > 0 ) {
			addPower((FriendOccupiersAmount-EnemyOccupiersAmount)*getPowerPerDefender());
			updateWool();
			String defenderMessage = "You are currently defending " +getName()+" power: "+Info.power+" / "+Info.maxPower;
			for (WarPlayer player: Occupiers) {
				player.sendMessage(defenderMessage);
			}
		}
	}
	
	private void sendMessageToOccupiers() {
		String defenderMessage = String.format("You are currently defending %1$s point(%2$d / %3$d), A: %4$d D: %5$d",
			getName(),getInfo().power,getInfo().maxPower,EnemyOccupiersAmount,FriendOccupiersAmount);
		
		String enemyMessage = String.format("You are capturing %1$s point(%2$d / %3$d), A: %4$d D: %5$d",
				getName(),getInfo().power,getInfo().maxPower,EnemyOccupiersAmount,FriendOccupiersAmount);
		
		for (WarPlayer player: Occupiers) {
			if (player.getInfo().factionID == Info.ownerFactionID) {
				player.sendMessage(defenderMessage);
			} else {
				player.sendMessage(enemyMessage);
			}
		}
	}
	
	private void updateWool() {
			int current = getInfo().power/PowerPerPowerBlock;
			
			for (int i = current; i < 8;++i) {
				if (PowerBlocks[i].getData() != getNeutralColor().getWoolData()) {
					PowerBlocks[i].setData(getNeutralColor().getWoolData());
				}
			}
			
			DyeColor ownerColor = getOwner().getDyeColor();
			for (int i = 0; i < current;++i) {
				if (PowerBlocks[i].getData() != ownerColor.getWoolData()) {
					PowerBlocks[i].setData(ownerColor.getWoolData());
				}
			}
	}
	
	public boolean isUnderSige() {
		return FriendOccupiersAmount < EnemyOccupiersAmount;
	}
	
	public boolean canCapture() {
		return (Info.power == 0);
	}
	
	private void capture() {
		OccupantInfo newOccupant = findNewFactionOccupant();
		
		EnemyOccupiersAmount -= newOccupant.amount;
		EnemyOccupiersAmount += FriendOccupiersAmount;
		
		Info.ownerFactionID = newOccupant.factionID;
		FriendOccupiersAmount = newOccupant.amount;
		
		Info.power = 1;
		try {
			PreparedStatement st = DB.get().prepareQuery("UPDATE ew_ControlPoints SET ownerID = ? WHERE id = ?");
			st.setInt(1,Info.ownerFactionID);
			st.setInt(2,getID());
			st.executeUpdate();
			DB.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String newOwnerName = ControlPoints.getWar().getFactions().getByID(Info.ownerFactionID).getName();
		for (WarPlayer player: Occupiers) {
			if (player.getInfo().factionID == Info.ownerFactionID) {
				player.sendMessage("Your faction captured "+getName()+" point");
			} else {
				player.sendMessage("Faction"+newOwnerName+" captured "+getName()+" point");
			}
		}
		ControlPoints.onCapture(this);
	}
	
	private OccupantInfo findNewFactionOccupant() {
		short[] factionOccupants= new short[ControlPoints.getWar().getFactions().size()+1];
		
		for (WarPlayer occupant : Occupiers) {
			++factionOccupants[occupant.getInfo().factionID];
		}
		OccupantInfo factionOccupant = new OccupantInfo(1);
		factionOccupant.amount = factionOccupants[1];
		
		for (int i= 2; i < factionOccupants.length; ++i) {
			if (factionOccupant.amount  < factionOccupants[i]) {
				factionOccupant.amount = factionOccupants[i];
				factionOccupant.factionID = i;
			}
		}
		
		return factionOccupant;
	}
	
	public void addPower(int power) {
		Info.power += power;
		Info.power = (Info.power > Info.maxPower)? Info.maxPower : Info.power;
	}
	
	public void subPower(int power) {
		Info.power -= power;
		Info.power = (Info.power < 0)? 0 : Info.power;
	}


	public boolean isWithin(WarPlayer warPlayer) {
		return (int)UMath.getDistance3D(
				warPlayer.getBlockX(),warPlayer.getBlockY(),warPlayer.getBlockZ(), 
				Info.centerX,Info.centerY,Info.centerZ) <= Info.radius;
	}
	
	public Location getLocation() {
		return new Location(
			getPoints().getWorld(),
			getInfo().centerX,getInfo().centerY,getInfo().centerZ);
	}
	
	public void setOwner(int newOwnerID) {
		getInfo().ownerFactionID = newOwnerID;
	}
	
	public Faction getOwner() {
		return getPoints().getFactions().getByID(getInfo().ownerFactionID);
	}
	
	private DyeColor getNeutralColor() {
		return DyeColor.WHITE;
	}
	
	private int getPowerPerAttacker() {
		return getPoints().getWar().getInfo().powerAttacker;
	}
	
	private int getPowerPerDefender() {
		return getPoints().getWar().getInfo().powerDefender;
	}
	
	public Sector getSector() {
		return ControlPoints.getSectors().getByID(getInfo().sectorID);
	}
	
	public ProtectedRegion getRegion() {
		return getPoints().getRegions().getRegion(NameConverter.nameToRegionName(REGION_PREFIX,getName()));
	}
	
	public ControlPointsManager getPoints() {
		return ControlPoints;
	}
	
	@Override
	public ControlPointInfo getInfo() {
		return Info;
	}

	@Override
	public int getID() {
		return Info.id;
	}

	@Override
	public String getName() {
		return Info.name;
	}
	
	@Override
	public String toString() {
		return getInfo().toString();
	}
	
}
