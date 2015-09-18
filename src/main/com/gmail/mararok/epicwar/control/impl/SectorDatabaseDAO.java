/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.gmail.mararok.bukkit.util.QueryString;
import com.gmail.mararok.bukkit.util.database.CachedQuery;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.entity.DatabaseEntityMapper;
import com.gmail.mararok.epicwar.impl.WarImpl;

public class SectorDatabaseDAO extends DatabaseEntityMapper implements SectorDAO {
  private DatabaseConnection connection;

  private final static int CREATE_SQLID = 0;
  private final static int FIND_ALL_SQLID = 1;
  private final static int UPDATE_SQLID = 2;

  public SectorDatabaseDAO(DatabaseConnection connection,
      CachedQuery[] sqlQueries) {
    super(connection, sqlQueries);
  }

  @Override
  public SectorData create(WarImpl war, String name) {
    SectorData info = new SectorData();
    info.name = name;
    try {
      PreparedStatement query = getCachedQuery(CREATE_SQLID);
      query.setInt(1, war.getId());
      query.setString(2, name);
      query.executeUpdate();
      ResultSet result = query.getGeneratedKeys();
      if (result.next()) {
        info.id = result.getInt(1);
      }
    } catch (SQLException e) {
      connection.logException(e);
    }
    
    return info;
  }

  @Override
  public List<SectorData> findAll(WarImpl war) {
    List<SectorData> infos = new LinkedList<SectorData>();
    try {
      PreparedStatement query = getCachedQuery(FIND_ALL_SQLID);
      query.setInt(1, war.getId());
      ResultSet result = query.executeQuery();
      while (result.next()) {
        SectorData info = new SectorData();
        info.id = result.getInt(1);
        info.name = result.getString(2);
        info.description = result.getString(3);
        info.ownerId = result.getInt(4);
        
        infos.add(info);
      }
      result.close();
    } catch (SQLException e) {
      connection.logException(e);
    }
    
    return infos;
  }

  @Override
  public void updateOne(SectorImpl sector, QueryString fields) {
    try {
      PreparedStatement query = getCachedQuery(UPDATE_SQLID);
      query.setInt(1, sector.getWar().getId());
      query.setString(2, fields.toString());
      query.setInt(3, sector.getId());
      query.executeUpdate();
    } catch (SQLException e) {
      connection.logException(e);
    }
  }

}
