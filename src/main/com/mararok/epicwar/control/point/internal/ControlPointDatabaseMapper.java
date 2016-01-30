/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epiccore.entity.EntityDatabaseMapper;
import com.mararok.epiccore.math.Vector3i;
import com.mararok.epicwar.control.ControlPointData;

public class ControlPointDatabaseMapper extends EntityDatabaseMapper<ControlPointImpl, ControlPointData>implements ControlPointMapper {

  private String insertColumns;

  public ControlPointDatabaseMapper(DMQL queries, String tableName, ControlPointFactory factory) {
    super(queries, tableName, factory);
    insertColumns = columns("name", "x", "y", "z", "radius", "maxPower");
  }

  @Override
  public ControlPointImpl insert(ControlPointData data) throws Exception {
    Vector3i position = data.position;
    data.id = insert(insertColumns,
        values(data.name,
            position.x, position.y, position.z,
            data.radius,
            data.maxPower));
    return getFactory().create(data);
  }

  @Override
  protected ControlPointData createData(ResultSet resultSet) throws SQLException {
    ControlPointData data = new ControlPointData();
    int column = 1;
    data.id = resultSet.getInt(column++);
    data.name = resultSet.getString(column++);
    data.description = resultSet.getString(column++);

    data.position = new Vector3i(resultSet.getInt(column++), resultSet.getInt(column++), resultSet.getInt(column++));
    data.radius = resultSet.getInt(column++);
    data.maxPower = resultSet.getInt(column++);

    String connections[] = resultSet.getString(column++).split(",");
    data.connections = new int[connections.length];

    int i = 0;
    for (String connection : connections) {
      data.connections[i++] = Integer.parseInt(connection);
    }
    return data;
  }
}
