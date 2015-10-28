/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gmail.mararok.epiccore.database.DMQL;
import com.gmail.mararok.epiccore.entity.EntityDatabaseMapper;
import com.gmail.mararok.epicwar.control.SectorData;

public class SectorDatabaseMapper extends EntityDatabaseMapper<SectorImpl, SectorData>implements SectorMapper {

  public SectorDatabaseMapper(DMQL queries, String tableName, SectorFactory factory) {
    super(queries, tableName, factory);
  }

  @Override
  public SectorImpl insert(SectorData entityData) throws Exception {
    String columns = "name,description";
    String values = entityData.name + "," + entityData.description;
    entityData.id = insert(columns, values);
    return getFactory().create(entityData);
  }

  @Override
  protected SectorData createData(ResultSet resultSet) throws SQLException {
    SectorData data = new SectorData();
    data.id = resultSet.getInt(1);
    data.name = resultSet.getString(2);
    data.description = resultSet.getString(3);
    return data;
  }

}
