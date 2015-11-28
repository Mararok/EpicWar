/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.mararok.epicwar.EpicWarPlugin;

public class WoolsMapMetadata implements MetadataValue {
  private EpicWarPlugin Plugin;
  public int WoolsMapID;
  public int SectorID;

  public WoolsMapMetadata(EpicWarPlugin plugin, int woolsMapID, int sectorID) {
    Plugin = plugin;
    WoolsMapID = woolsMapID;
    SectorID = sectorID;
  }

  @Override
  public boolean asBoolean() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public byte asByte() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public double asDouble() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public float asFloat() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public int asInt() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public long asLong() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public short asShort() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public String asString() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public Plugin getOwningPlugin() {
    return Plugin;
  }

  @Override
  public void invalidate() {
    throw new RuntimeException("invalid value type");
  }

  @Override
  public Object value() {
    throw new RuntimeException("invalid value type");
  }

}
