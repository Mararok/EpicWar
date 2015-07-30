/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SqlScript {
  private static final String SCRIPT_COMMENT_PREFIX = "-";
  private String name;
  private List<String> source;

  public SqlScript(String name, List<String> source) {
    this.name = name;
    this.source = source;
  }

  public String getName() {
    return name;
  }

  public List<String> getSource() {
    return source;
  }

  @Override
  public String toString() {
    StringBuilder scriptCode = new StringBuilder();
    for (String line : source) {
      scriptCode.append(line);
    }

    return scriptCode.toString();
  }

  public void exec(DatabaseConnection connection) throws SQLException {
    try {
      connection.exec(toString());
    } catch (SQLException e) {
      throw new SQLException("SQL Error in script: " + name, e);
    }
  }

  public CachedQuery[] prepareCachedQueriesFromScript(DatabaseConnection connection) throws SQLException {
    LinkedList<CachedQuery> queries = new LinkedList<CachedQuery>();
    int currentLine = 0;
    try {
      for (String line : source) {
        if (!line.isEmpty() && !line.startsWith(SCRIPT_COMMENT_PREFIX)) {
          queries.add(connection.prepareCachedQuery(line));
        }
        ++currentLine;
      }
      return queries.toArray(new CachedQuery[queries.size()]);
      
    } catch (SQLException e) {
      throw new SQLException("SQL Error in script file " + name + " line "+ currentLine + " Error: ", e);
    }
  }
}
