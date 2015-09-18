/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.entity.DatabaseEntityMapper;
import com.gmail.mararok.epicwar.faction.internal.FactionImpl;
import com.gmail.mararok.epicwar.impl.WarImpl;
import com.gmail.mararok.epicwar.player.WarPlayerData;

public class DatabasePlayerDao extends DatabaseEntityMapper implements PlayerDAO {
  private final static int REGISTER_SQLID = 0;
  private final static int LOAD_INFO_SQLID = 1;
  private final static int UPDATE_STATS_SQLID = 2;
  
  public DatabasePlayerDao(DatabaseConnection connection) {
    super(connection, new int[]{
        
    });
  }

  @Override
  public int register(WarImpl war, UUID uuid, FactionImpl faction) {
    int id = 0;
    try {
      PreparedStatement query = getCachedQuery(LOAD_INFO_SQLID);
      query.setInt(1,war.getId());
      query.setString(2,uuid.toString());
      ResultSet rs = query.executeQuery();
    } catch (SQLException exception) {
      getConnection().logException(exception);
    }
    
    return id;
  }
  
  @Override
  public WarPlayerData loadInfoFromUUID(WarImpl war, UUID uuid) {
    WarPlayerData info = new WarPlayerData();
    try {
     PreparedStatement query = getCachedQuery(LOAD_INFO_SQLID);
     query.setInt(1,war.getId());
     query.setString(2,uuid.toString());
     ResultSet rs = query.executeQuery();
     if (rs.first()) {
       info.id = rs.getInt(1);
       info.factionId = rs.getInt(2);
       return info;
     }
    
    } catch (SQLException exception) {
      getConnection().logException(exception);
    }
    
    return info;
  }

  @Override
  public void updateStats(WarPlayerImpl player) {
    // TODO Auto-generated method stub
    
  }

}
