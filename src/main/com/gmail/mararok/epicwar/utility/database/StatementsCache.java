/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2013 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;

public class StatementsCache {
	private ArrayList<PreparedStatement> Statements;
	private Stack<Integer> FreeSlots;
	public StatementsCache() {
		Statements = new ArrayList<PreparedStatement>(5);
		FreeSlots = new Stack<Integer>();
	}
	
	public void clear() throws SQLException {
		int size = Statements.size();
		for (int i = 0; i < size;++i) {
			remove(i);
		}
		
		Statements.clear();
		FreeSlots.clear();
	}
	
	public int add(PreparedStatement statement) {
		int slot = findFreeSlot();
		if (slot == -1) {
			slot = Statements.size();
			Statements.add(statement);
		}
		
		return slot;
	}
	
	private int findFreeSlot() {
		if (FreeSlots.size() > 0) {
			return FreeSlots.pop();
		}
		return -1;
	}
	
	public void remove(int index) throws SQLException { 
		PreparedStatement st = get(index);
		if (st != null) {
			get(index).close();
			Statements.set(index,null);
			FreeSlots.add(index);
		}
	}
	
	PreparedStatement get(int index) throws SQLException {
		if (index != -1)
			return Statements.get(index);
		throw new SQLException("Not found precompiled statement");
	}
}
