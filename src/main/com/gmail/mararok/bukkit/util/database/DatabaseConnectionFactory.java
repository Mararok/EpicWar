/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseConnectionFactory {
  private static final String SQLite_DRIVER = "org.sqlite.JDBC";
  private static final String MySQL_DRIVER = "com.mysql.jdbc.Driver";

  public static DatabaseConnection newConnection(DatabaseConnectionConfig config, JavaPlugin plugin) 
      throws SQLException {
    
    Connection jdbcConnection = createJDBCConnection(config,plugin);
    return new DatabaseConnectionImpl(jdbcConnection,config);
  }

  private static Connection createJDBCConnection(DatabaseConnectionConfig config, JavaPlugin plugin) 
      throws SQLException {
    
    String connectionURL;
    String engineDriverName;

    if (config.engine.equalsIgnoreCase("SQLite")) {
      connectionURL = createSQLiteConnectionURL(config,plugin);
      engineDriverName = SQLite_DRIVER;
    } else if (config.engine.equalsIgnoreCase("MySQL")) {
      connectionURL = createMySQLConnectionURL(config);
      engineDriverName = MySQL_DRIVER;
    } else {
      throw new SQLException("Not supported database engine: " + config.engine);
    }

    try {
      Class.forName(engineDriverName);
      return DriverManager.getConnection(connectionURL);
    } catch (ClassNotFoundException e) {
      throw new SQLException("Driver " + engineDriverName + " not found");
    }

  }

  private static String createSQLiteConnectionURL(DatabaseConnectionConfig config, JavaPlugin plugin) {
    return String.format(
        "jdbc:sqlite:%s/%s.db",plugin.getDataFolder().getPath(),config.dbName);
  }

  private static String createMySQLConnectionURL(DatabaseConnectionConfig config) {
    return String.format(
        "jdbc:mysql://%s/%s?user=%s&password=%s",config.host,config.dbName,config.user,config.password);
  }
}
