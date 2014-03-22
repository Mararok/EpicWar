/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility.database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.utility.Disposable;

public class DB implements Disposable {
	private static final String SQLite_DRIVER = "org.sqlite.JDBC";
	private static final String MySQL_DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String SCRIPT_COMMENT_PREFIX = "-";
	private static DB Instance;
	private EpicWarPlugin PluginInstance;
	private Path ScriptsPath;
	private Connection DBConnection;
	private StatementsCache Statements;
	
	public static void init(EpicWarPlugin plugin, DBInfo config, boolean autoCommit) throws SQLException {
		Instance = new DB(plugin,config, autoCommit);
	}
	
	public static DB get() {
		return Instance;
	}
	
	private DB(EpicWarPlugin plugin, DBInfo config, boolean autoCommit) throws SQLException {
		PluginInstance = plugin;
		ScriptsPath = Paths.get(plugin.getDataFolder().getPath()+"/sqlscripts");
		Statements = new StatementsCache();
		
		if (config.engine.equalsIgnoreCase("SQLite")) {
			initSQLite(config);
		} else if (config.engine.equalsIgnoreCase("MySQL")) {
			initMySQL(config);
		} else {
			throw new SQLException("Not supported database engine: "+config.engine);
		}
		
		DBConnection.setAutoCommit(autoCommit);
	}
	
	public void dispose() {
		try {
			closeConnection();
			Instance = null;
		} catch(SQLException e) {
			PluginInstance.logCriticalException(e);
		} 
	}
	
	public void closeConnection() throws SQLException {
		if (DBConnection == null) 
			return;
		
		clearCache();
		DBConnection.close(); 
		DBConnection = null;
	}
	
	public void openConnection(String connectionURL) throws SQLException {
		closeConnection();
		DBConnection = DriverManager.getConnection(connectionURL);
	}
	
	private void initSQLite(DBInfo config) throws SQLException {
		initDriver(DB.SQLite_DRIVER,
			"jdbc:sqlite:"+PluginInstance.getDataFolder().getPath()+"/"+config.name+".db");
	}

	private void initMySQL(DBInfo config) throws SQLException {
		initDriver(DB.MySQL_DRIVER,
			"jdbc:mysql://"+config.host+"/"+config.name+
			"?user="+config.user+"&password="+config.password);
	}
	
	private void initDriver(String driverName, String connectionURL) throws SQLException {
		try {
			Class.forName(driverName);
			DBConnection = DriverManager.getConnection(connectionURL);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver "+driverName+" not found");
		}
	}
	
	public void execScript(String scriptName) throws SQLException {
		List<String> lines = loadScript(scriptName);
		try {
			StringBuilder scriptCode = new StringBuilder();
			for (String line : lines) {
				scriptCode.append(line);
			}
			Statement st = DBConnection.createStatement();
			st.execute(scriptCode.toString());
			commit();
			st.close();
		} catch (SQLException e) {
			throw new SQLException("SQL Error in script file "+scriptName+" Error: "+e.getMessage());
		}
	}
	
	public List<String> loadScript(String scriptName) throws SQLException {
		
		Path scriptPath = ScriptsPath.resolve(scriptName+".sql");
		if (Files.exists(scriptPath,LinkOption.NOFOLLOW_LINKS)) {
			try {
				return Files.readAllLines(scriptPath, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new SQLException("Can't read script file "+scriptName+" content");
			}
		} else {
			throw new SQLException("Script file "+scriptName+" not exists");
		}
	}
	public int[] prepareCachedQueriesFromScript(String scriptName) throws SQLException {
		List<String> lines = loadScript(scriptName);
		LinkedList<Integer> queriesIDs = new LinkedList<Integer>();
		int currentLine = 0;
		try {
			for (String line : lines) {
				if (!line.isEmpty() && !line.startsWith(SCRIPT_COMMENT_PREFIX)) {
					queriesIDs.add(prepareCachedQuery(line));
				}
				++currentLine;
			}
			int[] retIDs = new int[queriesIDs.size()];
			int current = 0;
			for (Integer qid : queriesIDs) {
				retIDs[current++] = qid;
			}
			return retIDs;
		} catch (SQLException e) {
			throw new SQLException("SQL Error in script file "+scriptName+" line "+currentLine+" Error: "+e.getMessage());
		} 
	}
	
	/**
	 * Prepare sql statement for query.
	 */
	public PreparedStatement prepareQuery(String sql) throws SQLException {
		return DBConnection.prepareStatement(sql);
	}
	
	/**
	 * Prepare sql statement for query and put her to cache.
	 */
	public int prepareCachedQuery(String sql) throws SQLException {
		PreparedStatement st = DBConnection.prepareStatement(sql);
		return Statements.add(st);
	}
	
	public PreparedStatement getCachedQuery(int index) throws SQLException {
		return Statements.get(index);
	}
	
	public void removeCachedQuery(int index) throws SQLException {
		Statements.remove(index);
	}
	
	public void clearCache() throws SQLException {
		Statements.clear();
	}
	
	public void commit() throws SQLException {
		DBConnection.commit();
	}
	
	public void rollback() { 
		try {
			DBConnection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
