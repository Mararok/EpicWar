/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.sql.SQLException;

public class FileSqlScriptLoader implements SqlScriptLoader {
  private Path basePath;

  public FileSqlScriptLoader(Path scriptsBasePath) {
    this.basePath = scriptsBasePath;
  }

  public SqlScript load(String name) throws SQLException {
    Path scriptPath = basePath.resolve(name + ".sql");
    if (Files.exists(scriptPath, LinkOption.NOFOLLOW_LINKS)) {
      try {
        return new SqlScript(name, Files.readAllLines(scriptPath,StandardCharsets.UTF_8));
      } catch (IOException e) {
        throw new SQLException("Can't read script file " + name + " content");
      }
    } else {
      throw new SQLException("Script file " + name + " not exists");
    }
  }

  public Path getScriptsBasePath() {
    return basePath;
  }
}
