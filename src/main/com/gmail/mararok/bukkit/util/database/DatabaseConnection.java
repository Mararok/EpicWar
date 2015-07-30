/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gmail.mararok.bukkit.util.Disposable;

public interface DatabaseConnection extends Disposable {
  public int exec(String sql) throws SQLException;
  public PreparedStatement prepareQuery(String sql) throws SQLException;
  
  public CachedQuery prepareCachedQuery(String sql) throws SQLException;
  public CachedQuery getCachedQuery(int index) throws SQLException;
  public void clearCache() throws SQLException;
  
  public void beginTransaction() throws SQLException;
  public void endTransaction() throws SQLException;
  public void commit() throws SQLException;
  public void rollback() throws SQLException;

  DatabaseConnectionConfig getConfig();

  public void logException(SQLException e);
}
