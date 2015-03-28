/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionImpl implements DatabaseConnection {
  private Connection connection;
  private StatementCache statementCache;
  private DatabaseConnectionConfig config;

  public DatabaseConnectionImpl(Connection connection, DatabaseConnectionConfig config) {
    this.connection = connection;
    this.statementCache = new StatementCache(16);
    this.config = config;
  }

  @Override
  public int exec(String sql) throws SQLException {
    Statement st = connection.createStatement();
    int rowCount = st.executeUpdate(sql);
    st.close();
    
    return rowCount;
  }

  @Override
  public PreparedStatement prepareQuery(String sql) throws SQLException {
    return connection.prepareStatement(sql);
  }

  @Override
  public int prepareCachedQuery(String sql) throws SQLException {
    PreparedStatement st = connection.prepareStatement(sql);
    return statementCache.add(st);
  }

  @Override
  public PreparedStatement getCachedQuery(int index) throws SQLException {
    return statementCache.get(index);
  }

  @Override
  public void clearCache() throws SQLException {
    statementCache.clear();
  }

  @Override
  public void beginTransaction() throws SQLException {
    connection.setAutoCommit(false);
  }
  
  public void endTransaction() throws SQLException {
    connection.setAutoCommit(true);
  }

  @Override
  public void commit() throws SQLException {
    connection.commit();
  }

  @Override
  public void rollback() throws SQLException {
    connection.rollback();
  }

  @Override
  public DatabaseConnectionConfig getConfig() {
    return config;
  }

  @Override
  public void dispose() {
    if (connection != null) {
      try {
        clearCache();
        connection.close();
      } catch (SQLException e) {
      }
    }

  }
}
