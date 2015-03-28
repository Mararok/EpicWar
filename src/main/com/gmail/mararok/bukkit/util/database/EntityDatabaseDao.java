/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntityDatabaseDao {
  private DatabaseConnection connection;
  private int[] sqlQueries;
  
  public EntityDatabaseDao(DatabaseConnection connection, int[] sqlQueries) {
    this.connection = connection;
    this.sqlQueries = sqlQueries;
  }
  
  protected int executeQuery(String sql) throws SQLException {
    return connection.exec(sql);
  }
  
  protected PreparedStatement getCachedQuery(int index) throws SQLException {
    return connection.getCachedQuery(sqlQueries[index]);
  }
  
  protected DatabaseConnection getConnection() {
    return connection;
  }
}
