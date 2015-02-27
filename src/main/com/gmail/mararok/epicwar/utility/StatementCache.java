/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2014 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class StatementCache {
	private PreparedStatement[] statements;
	private int nextIndex = 0;
	private int size;
	
	public StatementCache() {
		this(16);
	}
	
	public StatementCache(int initialSize) {
		statements = new PreparedStatement[initialSize];
		size = initialSize;
	}
	
	public int add(PreparedStatement statement) {
		if (nextIndex > size-1) {
			resize(size*2);
		}
		statements[nextIndex] = statement;
		return ++nextIndex;
	}
	
	public PreparedStatement get(int index) {
		if (index != -1)
			return statements[index];
		return null;
	}
	
	public void resize(int newSize) {
		statements = Arrays.copyOf(statements,newSize);
		size = newSize;
	}

	public void clear() throws SQLException {
		for (PreparedStatement statement : statements) {
			statement.close();
		}
		
		statements = new PreparedStatement[size];
		
	}
	
}
