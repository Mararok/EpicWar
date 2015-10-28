/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.player.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.gmail.mararok.epiccore.database.DMQL;
import com.gmail.mararok.epiccore.entity.EntityDatabaseMapper;
import com.gmail.mararok.epicwar.player.PlayerStats;
import com.gmail.mararok.epicwar.player.WarPlayer;
import com.gmail.mararok.epicwar.player.WarPlayerData;

public class WarPlayerDatabaseMapper extends EntityDatabaseMapper<WarPlayerImpl, WarPlayerData>implements WarPlayerMapper {

  public WarPlayerDatabaseMapper(DMQL queries, String tableName, WarPlayerFactory factory) {
    super(queries, tableName, factory);
  }

  @Override
  public WarPlayerImpl insert(WarPlayerData entityData) throws Exception {
    return null;
  }

  @Override
  public WarPlayer findByUUID(UUID uuid) throws Exception {
    return findOne("uuid=" + uuid.toString());
  }

  @Override
  public WarPlayer findByPlayer(Player player) throws Exception {
    return findByUUID(player.getUniqueId());
  }

  @Override
  protected WarPlayerData createData(ResultSet resultSet) throws SQLException {
    WarPlayerData data = new WarPlayerData();
    int column = 1;
    data.id = resultSet.getInt(column++);
    data.uuid = UUID.fromString(resultSet.getString(column++));
    data.factionId = resultSet.getInt(column++);
    data.stats = createStats(resultSet, column);
    return data;
  }

  private PlayerStats createStats(ResultSet resultSet, int statsOffset) throws SQLException {
    PlayerStats stats = new PlayerStats();
    stats.kills = resultSet.getInt(statsOffset++);
    stats.teamkills = resultSet.getInt(statsOffset++);
    stats.deaths = resultSet.getInt(statsOffset++);
    stats.teamkills = resultSet.getInt(statsOffset++);
    stats.points = resultSet.getInt(statsOffset++);
    stats.capturedControlPoints = resultSet.getInt(statsOffset++);
    stats.capturedSectors = resultSet.getInt(statsOffset++);
    return stats;
  }

}
