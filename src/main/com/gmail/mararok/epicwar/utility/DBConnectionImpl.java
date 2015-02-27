/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2014 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public class DBConnectionImpl implements DBConnection {
	private Connection connection;
	private StatementCache statementCache;
	private DBConnectionConfig config;
	private EpicWarPlugin plugin;
	
	public DBConnectionImpl(Connection connection, DBConnectionConfig config, EpicWarPlugin plugin) {
		this.connection = connection;
		statementCache = new StatementCache(16);
		this.config = config;
	}
	
	@Override
	public void exec(String sql) throws SQLException {
		Statement st = connection.createStatement();
		st.executeUpdate(sql);
		st.close();
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
	public void startTransaction() throws SQLException {
		
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
	public DBConnectionConfig getConfig() {
		return config;
	}
	
	@Override
	public void dispose() {
		if (connection != null) {
			try {
			clearCache();
			connection.close();
			} catch (SQLException e) {
				plugin.logCriticalException(e);
			}
		}
		
	}
}
