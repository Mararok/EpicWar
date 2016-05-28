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
import com.mararok.epiccore.math.Vector3i;
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
            data.name, data.shortcut, data.color.name(),
            data.spawnPosition.x, data.spawnPosition.y, data.spawnPosition.z));
    return getFactory().create(data);
  }

  @Override
  protected FactionData createData(ResultSet resultSet) throws SQLException {
    FactionData data = new FactionData();

    data.id = resultSet.getInt("id");
    data.name = resultSet.getString("name");
    data.shortcut = resultSet.getString("shortcut");
    data.description = resultSet.getString("description");

    data.color = Faction.Color.getByName(resultSet.getString("color"));
    data.bannerPattern = Faction.BannerPattern.createFromSerialized(resultSet.getString("bannerPattern"));
    data.spawnPosition = new Vector3i(resultSet.getInt("spawnX"), resultSet.getInt("spawnY"), resultSet.getInt("spawnZ"));

    return data;
  }

}
