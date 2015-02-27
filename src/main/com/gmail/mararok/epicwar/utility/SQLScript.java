/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2014 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SQLScript {
	private static final String SCRIPT_COMMENT_PREFIX = "-";
	private String name;
	private List<String> source;
	
	public SQLScript(String name, List<String> source) {
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
	
	public void exec(DBConnection connection) throws SQLException {
		try {
			connection.exec(toString());
		} catch (SQLException e) {
			throw new SQLException("SQL Error in script: "+name,e);
		}
	}
	
	public int[] prepareCachedQueriesFromScript(DBConnection connection) throws SQLException {
		LinkedList<Integer> queriesIDs = new LinkedList<Integer>();
		int currentLine = 0;
		try {
			for (String line : source) {
				if (!line.isEmpty() && !line.startsWith(SCRIPT_COMMENT_PREFIX)) {
					queriesIDs.add(connection.prepareCachedQuery(line));
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
			throw new SQLException("SQL Error in script file "+name+" line "+currentLine+" Error: "+e.getMessage());
		} 
	}
}
