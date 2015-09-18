/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

import org.bukkit.World;

import com.gmail.mararok.bukkit.util.Disposable;
import com.gmail.mararok.epicwar.EpicWarPlugin;
import com.gmail.mararok.epicwar.impl.WarImpl;

public abstract class DataSetManager<DataType extends DataObject<?>> implements Disposable {
  private ArrayList<DataType> dataSet;
  private Map<String,DataType> namesDataSet; 
  private WarImpl war;

  public DataSetManager(WarImpl war) {
    this.dataSet = new ArrayList<DataType>(16);
    this.namesDataSet = HashObjObjMaps.newMutableMap();
    this.war = war;
  }

  public abstract void load() throws Exception;

  public abstract void init() throws Exception;

  protected void optimizeMemory() {
    dataSet.trimToSize();
  }

  protected void put(DataType data) {
    dataSet.add(data);
    namesDataSet.put(data.getName(), data);
  }

  public DataType getByID(int id) {
    return dataSet.get(id);
  }

  public DataType getByName(final String name) {
    return namesDataSet.get(name);
  }

  public List<DataType> getList() {
    return dataSet;
  }

  public int size() {
    return dataSet.size();
  }

  public boolean isExists(final String name) {
    return namesDataSet.containsKey(name);
  }

  public abstract void dump2File() throws IOException;

  public void dump2File(final String dumpName) throws IOException {
    Path dumpPath = Paths.get(getPlugin().getDataDumpPath());
    if (!Files.isDirectory(dumpPath)) {
      Files.createDirectory(dumpPath);
    }

    PrintWriter writer = new PrintWriter(Files.newBufferedWriter(
        dumpPath.resolve(dumpName + "Dump.txt"), StandardCharsets.UTF_8));
    writer.println("[EpicWar] " + dumpName + " dump");
    for (DataType pi : dataSet) {
      writer.println(pi);
    }
    writer.println("[EpicWar] " + dumpName + " end dump");
    writer.close();
  }

  public EpicWarPlugin getPlugin() {
    return getWar().getPlugin();
  }


  public World getWorld() {
    return getWar().getWorld();
  }

  public WarImpl getWar() {
    return war;
  }

  public void dispose() {
  }
}
