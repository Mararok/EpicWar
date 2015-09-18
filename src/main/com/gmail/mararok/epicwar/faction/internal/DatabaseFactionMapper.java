/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

import com.gmail.mararok.bukkit.util.Position3D;
import com.gmail.mararok.bukkit.util.database.CachedQuery;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.entity.DatabaseEntityMapper;
import com.gmail.mararok.epicwar.faction.FactionBannerPattern;
import com.gmail.mararok.epicwar.faction.FactionColor;
import com.gmail.mararok.epicwar.faction.FactionData;

public class DatabaseFactionMapper extends DatabaseEntityMapper<FactionImpl, FactionData>implements FactionMapper {

  public DatabaseFactionMapper(DatabaseConnection connection, CachedQuery[] sqlQueries, String tableName, FactionFactory factory) {
    super(connection, sqlQueries, tableName, factory);
  }

  @Override
  public FactionImpl create(FactionData data) throws Exception {
    String columns = "(id,name,shortcut,color,bannerPattern,spawnPositionX,spawnPositionY,spawnPositionZ)";
    data.id = data.color.ordinal(); // faction id is color;
    String values = "(" + Strings.join(data.toStringList(), ",") + ")";
    insert(columns, values);
    return getFactory().create(data);
  }

  @Override
  protected FactionData createData(ResultSet resultSet) throws SQLException {
    FactionData data = new FactionData();
    data.id = resultSet.getInt(1);
    data.name = resultSet.getString(2);
    data.shortcut = resultSet.getString(3);
    data.description = resultSet.getString(4);

    data.color = FactionColor.getByChar(resultSet.getString(5).charAt(0));
    data.bannerPattern = FactionBannerPattern.createFromSerialized(resultSet.getString(6));

    data.spawnPosition = new Position3D(resultSet.getLong(7), resultSet.getLong(8), resultSet.getLong(9));

    return data;
  }

}
