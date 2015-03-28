/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.sql.SQLException;

public interface SqlScriptLoader {
  public SqlScript load(String name) throws SQLException;
}
