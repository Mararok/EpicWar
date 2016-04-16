/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.player.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.mararok.epiccore.database.DMQL;
import com.mararok.epiccore.entity.EntityDatabaseMapper;
import com.mararok.epicwar.player.PlayerStats;
import com.mararok.epicwar.player.WarPlayerData;

public class WarPlayerDatabaseMapper extends EntityDatabaseMapper<WarPlayerImpl, WarPlayerData>implements WarPlayerMapper {
  private String columns;

  public WarPlayerDatabaseMapper(DMQL queries, String tableName, WarPlayerFactory factory) {
    super(queries, tableName, factory);
    columns = columns("uuid", "factionId");
  }

  @Override
  public WarPlayerImpl insert(WarPlayerData entityData) throws Exception {
    entityData.id = insert(columns, values(entityData.uuid, entityData.factionId));
    entityData.stats = new PlayerStats();

    return getFactory().create(entityData);
  }

  @Override
  public WarPlayerImpl findByUUID(UUID uuid) throws Exception {
    return findOne("uuid='" + uuid.toString() + "'");
  }

  @Override
  public WarPlayerImpl findByPlayer(Player player) throws Exception {
    return findByUUID(player.getUniqueId());
  }

  @Override
  protected WarPlayerData createData(ResultSet resultSet) throws SQLException {
    WarPlayerData data = new WarPlayerData();

    data.id = resultSet.getInt("id");
    data.uuid = UUID.fromString(resultSet.getString("uuid"));
    data.factionId = resultSet.getInt("factionId");
    data.stats = createStats(resultSet);
    
    return data;
  }

  private PlayerStats createStats(ResultSet resultSet) throws SQLException {
    PlayerStats stats = new PlayerStats();
    stats.kills = resultSet.getInt("kills");
    stats.teamkills = resultSet.getInt("teamkills");
    stats.deaths = resultSet.getInt("deaths");
    stats.points = resultSet.getInt("points");
    stats.capturedControlPoints = resultSet.getInt("capturedControlPoints");
    stats.capturedSectors = resultSet.getInt("capturedSectors");
    
    return stats;
  }

}
