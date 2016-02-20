/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.subsector.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epiccore.entity.EntityDatabaseMapper;
import com.mararok.epicwar.control.SubsectorData;

public class SubsectorDatabaseMapper extends EntityDatabaseMapper<SubsectorImpl, SubsectorData>implements SubsectorMapper {

  private String insertColumns;

  public SubsectorDatabaseMapper(DMQL queries, String tableName, SubsectorFactory factory) {
    super(queries, tableName, factory);
    insertColumns = columns("id", "chunkX", "chunkZ", "controlPointId");
  }

  @Override
  public SubsectorImpl insert(SubsectorData data) throws Exception {
    insert(insertColumns, values(data.id, data.chunkX, data.chunkZ, data.controlPointId));
    return getFactory().create(data);
  }

  @Override
  protected SubsectorData createData(ResultSet resultSet) throws SQLException {
    SubsectorData data = new SubsectorData();
    int column = 1;
    data.id = resultSet.getInt(column++);
    data.chunkX = resultSet.getInt(column++);
    data.chunkZ = resultSet.getInt(column++);
    data.controlPointId = resultSet.getInt(column++);
    return data;
  }
}
