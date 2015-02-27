/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2014 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.sql.SQLException;

public class SQLScriptLoader {
	private Path scriptsBasePath;
	
	public SQLScriptLoader(Path scriptsBasePath) {
		this.scriptsBasePath = scriptsBasePath;
	}
	
	public SQLScript loadFromFile(String filename) throws SQLException {
		Path scriptPath = scriptsBasePath.resolve(filename+".sql");
		if (Files.exists(scriptPath,LinkOption.NOFOLLOW_LINKS)) {
			try {
				return new SQLScript(filename, Files.readAllLines(scriptPath, StandardCharsets.UTF_8));
			} catch (IOException e) {
				throw new SQLException("Can't read script file "+filename+" content");
			}
		} else {
			throw new SQLException("Script file "+filename+" not exists");
		}
	}
	
	public Path getScriptsBasePath() {
		return scriptsBasePath;
	}

}
