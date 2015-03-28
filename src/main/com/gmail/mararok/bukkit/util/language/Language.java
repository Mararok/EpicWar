/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.language;

import java.util.Map;

public class Language {
  private String name;
  private String prefix;
  private String author;
  private String version;
  
  private Map<String, String> strings;

  public Language(String name, String prefix, String author, String version, Map<String,String> strings) {
    this.name = name;
    this.prefix = prefix;
    this.author = author;
    this.version = version;
    this.strings = strings;
  }

  public String getName() {
    return name;
  }
  
  public String getPrefix() {
    return prefix;
  }
  
  public String getAuthor() {
    return author;
  }
  
  public String getVersion() {
    return version;
  }
  
  /**
   * Returns string from key or when isn't exists returns key.
   */
  public String getString(String key) {
    String string = strings.get(key);
    return (string == null) ? key : string;
  }
  
}
