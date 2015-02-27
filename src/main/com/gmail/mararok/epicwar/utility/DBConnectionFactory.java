/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2014 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public class DBConnectionFactory {
	private static final String SQLite_DRIVER = "org.sqlite.JDBC";
	private static final String MySQL_DRIVER = "com.mysql.jdbc.Driver";
	
	private EpicWarPlugin plugin;
	
	public DBConnectionFactory(EpicWarPlugin plugin) {
		this.plugin = plugin;
	}
	
	public DBConnection newConnection(DBConnectionConfig config) {
		try {
			Connection jdbcConnection = createJDBCConnection(config);
			return new DBConnectionImpl(jdbcConnection,config,plugin);
		} catch (SQLException e) {
			plugin.logCriticalException(e);
			return null;
		}
		
	}

	private Connection createJDBCConnection(DBConnectionConfig config) throws SQLException {
		String connectionURL;
		String engineDriverName;
		
		if (config.engine.equalsIgnoreCase("SQLite")) {
			connectionURL = createSQLiteConnectionURL(config);
			engineDriverName = SQLite_DRIVER;
		} else if (config.engine.equalsIgnoreCase("MySQL")) {
			connectionURL = createMySQLConnectionURL(config);
			engineDriverName = MySQL_DRIVER;
		} else {
			throw new SQLException("Not supported database engine: "+config.engine);
		}
		
		try {
			Class.forName(engineDriverName);
			return DriverManager.getConnection(connectionURL);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver "+engineDriverName+" not found");
		}
		
	}
	
	private String createSQLiteConnectionURL(DBConnectionConfig config) {
		return "jdbc:sqlite:"+plugin.getDataFolder().getPath()+"/"+config.dbName+".db";
	}
	
	private String createMySQLConnectionURL(DBConnectionConfig config) {
		return "jdbc:mysql://"+config.host+"/"+config.dbName+"?user="+config.user+"&password="+config.password;
	}
}
