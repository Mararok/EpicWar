/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntityDatabaseDAO {
  private DatabaseConnection connection;
  private CachedQuery[] cachedQueries;
  
  public EntityDatabaseDAO(DatabaseConnection connection, CachedQuery[] sqlQueries) {
    this.connection = connection;
    this.cachedQueries = sqlQueries;
  }
  
  protected int executeQuery(String sql) throws SQLException {
    return connection.exec(sql);
  }
  
  protected PreparedStatement getCachedQuery(int index) throws SQLException {
    return cachedQueries[index].getCompiledQuery();
  }
  
  protected DatabaseConnection getConnection() {
    return connection;
  }
}
