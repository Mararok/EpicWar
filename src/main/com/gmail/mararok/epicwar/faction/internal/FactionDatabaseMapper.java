/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

import com.gmail.mararok.epiccore.Position3D;
import com.gmail.mararok.epiccore.database.DMQL;
import com.gmail.mararok.epiccore.entity.EntityDatabaseMapper;
import com.gmail.mararok.epicwar.faction.Faction;
import com.gmail.mararok.epicwar.faction.FactionData;

public class FactionDatabaseMapper extends EntityDatabaseMapper<FactionImpl, FactionData>implements FactionMapper {

  public FactionDatabaseMapper(DMQL queries, String tableName, FactionFactory factory) {
    super(queries, tableName, factory);
  }

  @Override
  public FactionImpl insert(FactionData data) throws Exception {
    String columns = "id,name,shortcut,color,bannerPattern,spawnPositionX,spawnPositionY,spawnPositionZ";
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

    data.color = Faction.Color.getByChar(resultSet.getString(5).charAt(0));
    data.bannerPattern = Faction.BannerPattern.createFromSerialized(resultSet.getString(6));

    data.spawnPosition = new Position3D(resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9));

    return data;
  }

}
