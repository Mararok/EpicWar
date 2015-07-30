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
import java.util.logging.Logger;

public class DatabaseConnectionImpl implements DatabaseConnection {
  private Connection connection;
  private QueriesCache statementCache;
  
  private DatabaseConnectionConfig config;
  private Logger log;

  public DatabaseConnectionImpl(Connection connection, DatabaseConnectionConfig config, Logger log) {
    this.connection = connection;
    this.statementCache = new QueriesCache(32,this);
    
    this.config = config;
    this.log = log;
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
  public CachedQuery prepareCachedQuery(String sql) throws SQLException {
    return statementCache.add(sql);
  }

  @Override
  public CachedQuery getCachedQuery(int index) throws SQLException {
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
        logException(e);
      }
    }

  }

  @Override
  public void logException(SQLException e) {
    log.severe("Database error: "+e);
  }
}
