/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epiccore.entity.EntityDatabaseMapper;
import com.mararok.epicwar.control.ControlPointData;

public class ControlPointDatabaseMapper extends EntityDatabaseMapper<ControlPointImpl, ControlPointData>implements ControlPointMapper {

  public ControlPointDatabaseMapper(DMQL queries, String tableName, ControlPointFactory factory) {
    super(queries, tableName, factory);
  }

  @Override
  public ControlPointImpl insert(ControlPointData entityData) throws Exception {
    String columns = "name,description";
    String values = entityData.name + "," + entityData.description;
    entityData.id = insert(columns, values);
    return getFactory().create(entityData);
  }

  @Override
  protected ControlPointData createData(ResultSet resultSet) throws SQLException {
    ControlPointData data = new ControlPointData();
    data.id = resultSet.getInt(1);
    data.name = resultSet.getString(2);
    data.description = resultSet.getString(3);
    return data;
  }
}
