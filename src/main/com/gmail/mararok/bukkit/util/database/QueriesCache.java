/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.SQLException;
import java.util.Arrays;

/** Class for manage sql queries.
 */
public class QueriesCache {
  private CachedQuery[] queries;
  private int nextIndex = 0;
  private int size;

  private DatabaseConnection connection;
  
  public QueriesCache(DatabaseConnection connection) {
    this(16,connection);
  }

  public QueriesCache(int initialSize, DatabaseConnection connection) {
    queries = new CachedQuery[initialSize];
    size = initialSize;
    
    this.connection = connection;
  }

  public CachedQuery add(String sql) {
    if (nextIndex > size - 1) {
      resize(size * 2);
    }
    queries[nextIndex] = new CachedQuery(nextIndex,connection,sql);
    return queries[nextIndex];
  }

  public CachedQuery get(int index) {
    return queries[index];
  }

  public void resize(int newSize) {
    queries = Arrays.copyOf(queries, newSize);
    size = newSize;
  }

  public void clear() throws SQLException {
    for (CachedQuery query : queries) {
      query.removeCompiled();
    }

    queries = new CachedQuery[size];
  }
  
  public DatabaseConnection getConnection() {
    return connection;
  }

}
