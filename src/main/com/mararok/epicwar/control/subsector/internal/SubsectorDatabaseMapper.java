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
    insertColumns = super.columns("id", "chunkX", "chunkZ", "controlPointId");
  }

  @Override
  public SubsectorImpl insert(SubsectorData data) throws Exception {
    insert(insertColumns, super.values(Integer.toString(data.id), Integer.toString(data.chunkX), Integer.toString(data.chunkZ), Integer.toString(data.controlPointId)));
    return getFactory().create(data);
  }

  @Override
  protected SubsectorData createData(ResultSet resultSet) throws SQLException {
    SubsectorData data = new SubsectorData();
    data.id = resultSet.getInt(1);
    data.chunkX = resultSet.getInt(2);
    data.chunkZ = resultSet.getInt(3);
    data.controlPointId = resultSet.getInt(4);
    return data;
  }
}
