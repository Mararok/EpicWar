/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.faction.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

import com.gmail.mararok.bukkit.util.database.CachedQuery;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.entity.EntityDatabaseMapper;
import com.gmail.mararok.bukkit.util.entity.EntityFactory;
import com.gmail.mararok.epicwar.faction.FactionAppearance;
import com.gmail.mararok.epicwar.faction.FactionBannerPattern;
import com.gmail.mararok.epicwar.faction.FactionColor;
import com.gmail.mararok.epicwar.faction.FactionData;

public class FactionMapperImpl extends EntityDatabaseMapper<FactionImpl, FactionData> implements FactionMapper {

  public FactionMapperImpl(EntityFactory<FactionImpl, FactionData> entityFactory, DatabaseConnection connection, CachedQuery[] sqlQueries, String tableName) {
    super(entityFactory, connection, sqlQueries, tableName);
  }
 
  public FactionImpl create(FactionData data) throws Exception {
    String columns = "(id,name,shortcut,description,color,banner,capitalSectorId)";
    data.id = data.appearance.color.ordinal(); // faction id is color;
    String values = "("+Strings.join(data.toStringList(),",")+")";
    insert(columns, values);
    return entityFactory.create(data);
  }
  
  @Override
  protected FactionData createEntityData(ResultSet resultSet) throws SQLException {
    FactionData data = new FactionData();
    data.id = resultSet.getInt(1);
    data.name = resultSet.getString(2);
    data.shortcut = resultSet.getString(3);
    data.description = resultSet.getString(4);
    data.capitalSectorId = resultSet.getInt(5);
    
    FactionAppearance appearance = new FactionAppearance();
    appearance.color = FactionColor.getByChar(resultSet.getString(6).charAt(0));
    appearance.bannerPattern = FactionBannerPattern.createFromSerialized(resultSet.getString(7));
    data.appearance = appearance;
    return data;
  }

}
