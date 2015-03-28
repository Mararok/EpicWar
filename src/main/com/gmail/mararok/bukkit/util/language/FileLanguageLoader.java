/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.language;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

import org.bukkit.configuration.file.YamlConfiguration;

public class FileLanguageLoader implements LanguageLoader {
  private Path basePath;
  
  public FileLanguageLoader(Path languagesBasePath) {
    
  }
  
  @Override
  public Language load(String languagePrefix) throws Exception {
    Path langPath = basePath.resolve(languagePrefix + ".yml");
    if (Files.exists(langPath, LinkOption.NOFOLLOW_LINKS)) {
      try {
        Reader file = Files.newBufferedReader(langPath);
        return parseLanguageFile(file);
      } catch (Exception e) {
        throw new Exception("Can't read language file " + langPath, e);
      }
    } else {
      throw new Exception("Language file " + langPath + " not exists");
    }
  }
  
  private Language parseLanguageFile(Reader file) {
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    
    String name = config.getString("name","EMPTY");
    String prefix = config.getString("prefix","EMPTY");
    String author = config.getString("author","EMPTY");
    String version = config.getString("version","EMPTY");
    
    Set<String> paths = config.getKeys(true);
    
    paths.remove("name");
    paths.remove("prefix");
    paths.remove("author");
    paths.remove("version");
    
    int size = paths.size();
    String[] keys = new String[size];
    String[] values = new String[size];
    int i = 0;
    for(String path : paths) {
      keys[i++] = path;
      values[i++] = config.getString(path);
    }
    
    Map<String,String> strings = HashObjObjMaps.newImmutableMap(keys,values);
    
    return new Language(name,prefix,author,version,strings);
  }
  
}
