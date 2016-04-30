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
    insertColumns = columns("chunkX", "chunkZ", "controlPointId");
  }

  @Override
  public SubsectorImpl insert(SubsectorData data) throws Exception {
    data.id = insert(insertColumns, values(data.chunkX, data.chunkZ, data.controlPointId));
    return getFactory().create(data);
  }

  @Override
  protected SubsectorData createData(ResultSet resultSet) throws SQLException {
    SubsectorData data = new SubsectorData();
    data.id = resultSet.getInt("id");
    data.chunkX = resultSet.getInt("chunkX");
    data.chunkZ = resultSet.getInt("chunkZ");
    data.controlPointId = resultSet.getInt("controlPointId");
    return data;
  }
}
