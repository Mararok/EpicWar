/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.faction.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epiccore.entity.EntityDatabaseMapper;
import com.mararok.epiccore.math.Position3D;
import com.mararok.epicwar.faction.Faction;
import com.mararok.epicwar.faction.FactionData;

public class FactionDatabaseMapper extends EntityDatabaseMapper<FactionImpl, FactionData>implements FactionMapper {

  public FactionDatabaseMapper(DMQL queries, String tableName, FactionFactory factory) {
    super(queries, tableName, factory);
  }

  @Override
  public FactionImpl insert(FactionData data) throws Exception {
    data.id = data.color.ordinal(); // faction id is color

    insert(columns("id", "name", "shortcut", "color", "spawnX", "spawnY", "spawnZ"),
        values(data.id,
            data.name, data.shortcut, data.color,
            data.spawnPosition.x, data.spawnPosition.y, data.spawnPosition.z));
    return getFactory().create(data);
  }

  @Override
  protected FactionData createData(ResultSet resultSet) throws SQLException {
    FactionData data = new FactionData();
    int column = 1;
    data.id = resultSet.getInt(column++);
    data.name = resultSet.getString(column++);
    data.shortcut = resultSet.getString(column++);
    data.description = resultSet.getString(column++);

    data.color = Faction.Color.getByChar(resultSet.getString(column++).charAt(0));
    data.bannerPattern = Faction.BannerPattern.createFromSerialized(resultSet.getString(column++));
    data.spawnPosition = new Position3D(resultSet.getInt(column++), resultSet.getInt(column++), resultSet.getInt(column++));

    return data;
  }

}
