/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.sector.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epiccore.entity.EntityDatabaseMapper;
import com.mararok.epicwar.control.SectorData;

public class SectorDatabaseMapper extends EntityDatabaseMapper<SectorImpl, SectorData>implements SectorMapper {

  private String insertColumns;

  public SectorDatabaseMapper(DMQL queries, String tableName, SectorFactory factory) {
    super(queries, tableName, factory);
    insertColumns = columns("name", "description");
  }

  @Override
  public SectorImpl insert(SectorData data) throws Exception {
    data.id = insert(insertColumns, values(data.name));
    return getFactory().create(data);
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
