/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction;

import java.sql.PreparedStatement;

import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.database.EntityDatabaseDao;

public class FactionDatabaseDao extends EntityDatabaseDao implements FactionDao {
  private static final int UPDATE_INFO = 0;
  private static final int FIND_ALL = 1;
  
  public FactionDatabaseDao(DatabaseConnection connection, int[] sqlQueries) {
    super(connection, sqlQueries);
  }

  @Override
  public void updateInfo(Faction faction) throws Exception {
    PreparedStatement query = getCachedQuery(UPDATE_INFO);
    query.setInt(1,faction.getFactions().getWar().getId());
    
    FactionInfo info = faction.getInfo();
    
    Position3D spawn = info.spawnPosition;
    query.setLong(2,spawn.x);
    query.setLong(3,spawn.x);
    query.setLong(4,spawn.x);
    
  }

  @Override
  public FactionInfo[] findAll() throws Exception {
    
  }

}
