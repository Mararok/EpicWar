/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epiccore.entity.EntityDatabaseMapper;
import com.mararok.epiccore.math.Vector3i;
import com.mararok.epiccore.misc.SpawnPointList;
import com.mararok.epicwar.control.ControlPointData;

public class ControlPointDatabaseMapper extends EntityDatabaseMapper<ControlPointImpl, ControlPointData>implements ControlPointMapper {

  private String insertColumns;

  public ControlPointDatabaseMapper(DMQL queries, String tableName, ControlPointFactory factory) {
    super(queries, tableName, factory);
    insertColumns = columns("name", "sectorId", "x", "y", "z", "radius", "maxPower");
  }

  @Override
  public ControlPointImpl insert(ControlPointData data) throws Exception {
    Vector3i position = data.position;
    data.id = insert(insertColumns, values(data.name, data.sectorId, position.x, position.y, position.z, data.radius, data.maxPower));
    return getFactory().create(data);
  }

  @Override
  protected ControlPointData createData(ResultSet resultSet) throws SQLException {
    ControlPointData data = new ControlPointData();

    data.id = resultSet.getInt("id");
    data.name = resultSet.getString("name");
    data.description = resultSet.getString("description");
    data.sectorId = resultSet.getInt("sectorId");
    data.ownerId = resultSet.getInt("ownerId");
    data.position = new Vector3i(resultSet.getInt("x"), resultSet.getInt("y"), resultSet.getInt("z"));
    data.radius = resultSet.getInt("radius");
    data.maxPower = resultSet.getInt("maxPower");

    String connectionsString = resultSet.getString("connections");
    if (!connectionsString.isEmpty()) {
      String connections[] = connectionsString.split(",");
      data.connections = new int[connections.length];
      int i = 0;
      for (String connection : connections) {
        data.connections[i++] = Integer.parseInt(connection);
      }
    }

    Vector3i[] points = null;
    String spawnPointsString = resultSet.getString("spawnPointList");
    if (!spawnPointsString.isEmpty()) {
      String[] spawnPoints = connectionsString.split("|");
      points = new Vector3i[spawnPoints.length];
      int i = 0;
      for (String p : spawnPoints) {
        String[] components = p.split(",");
        points[i++] = new Vector3i(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2]));
      }
    }

    data.spawnPointList = new SpawnPointList(new Random(), points);

    return data;
  }
}
