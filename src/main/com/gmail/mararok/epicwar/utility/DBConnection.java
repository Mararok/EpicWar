/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DBConnection extends Disposable {
	public void exec(String statement) throws SQLException;

	public PreparedStatement prepareQuery(String sql) throws SQLException;
	public int prepareCachedQuery(String sql) throws SQLException;
	public PreparedStatement getCachedQuery(int index) throws SQLException;
	public void clearCache() throws SQLException;
	
	public void startTransaction() throws SQLException;
	public void commit() throws SQLException;
	public void rollback() throws SQLException;

	DBConnectionConfig getConfig();
}
