/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CachedQuery {
  private int id;
  private String sql;
  private PreparedStatement compiledQuery;
  
  private DatabaseConnection connection;
  
  public CachedQuery(int id, DatabaseConnection connection, String sql) {
    this.connection = connection;
    this.sql = sql;
  }
  
  public PreparedStatement getCompiledQuery() throws SQLException {
    if (isCompiled()) {
      return compiledQuery;
    } 
    
    return connection.prepareQuery(sql);
  }
  
  public void compile() throws SQLException {
    if (!isCompiled()) {
      compiledQuery = connection.prepareQuery(sql);
    }
  }
  
  public void removeCompiled() throws SQLException {
    if (isCompiled()) {
      compiledQuery.close();
      compiledQuery = null;
    }
  }
  
  public boolean isCompiled() {
    return (compiledQuery != null);
  }
  
  public int getId() {
    return id;
  }
  
  public String getSql() {
    return sql;
  }
  
  public DatabaseConnection getConnection() {
    return connection;
  }

}
