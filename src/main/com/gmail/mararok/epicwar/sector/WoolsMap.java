/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.sector;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.gmail.mararok.epicwar.control.impl.SectorImpl;
import com.gmail.mararok.epicwar.util.DataObject;

public class WoolsMap implements DataObject<WoolsMapInfo> {
  private WoolsMapInfo Info;
  private WoolsMapManager Maps;
  private HashMap<Integer, Block> MapBlocks;

  public WoolsMap(WoolsMapInfo info, WoolsMapManager maps) {
    Info = info;
    Maps = maps;
    MapBlocks = new HashMap<Integer, Block>();
  }

  public void init() {
    generateMap();
  }

  public void generateMap() {
    Block currentBlock = getMaps().getWorld().getBlockAt(getInfo().x,
        getInfo().y, getInfo().z);

    int sectorsAmount = getMaps().getSectorManager().size() - 1;
    int lines = sectorsAmount / getInfo().sectorsPerLine;

    List<SectorImpl> sectors = getMaps().getSectorManager().getList();

    for (int y = 0; y < lines; ++y) {
      for (int x = 0; x < getInfo().sectorsPerLine; ++x) {

        currentBlock.setType(Material.WOOL);
        int sectorID = y * getInfo().sectorsPerLine + x + 1;
        SectorImpl sector = sectors.get(sectorID);

        currentBlock.setData(sector.getOwner().getDyeColor().getWoolData());

        WoolsMapMetadata value = new WoolsMapMetadata(getMaps().getPlugin(),
            getID(), sectorID);
        currentBlock.setMetadata("woolsMapMetadata", value);

        MapBlocks.put(sectorID, currentBlock);

        currentBlock = currentBlock.getRelative(BlockFace.EAST);
      }

      currentBlock = currentBlock.getRelative(BlockFace.WEST, 2);
      currentBlock = currentBlock.getRelative(BlockFace.DOWN);
    }
  }

  public void onSectorCapture(SectorImpl sector) {
    MapBlocks.get(sector.getID()).setData(
        sector.getOwner().getDyeColor().getWoolData());
  }

  public WoolsMapManager getMaps() {
    return Maps;
  }

  @Override
  public WoolsMapInfo getInfo() {
    return Info;
  }

  @Override
  public int getID() {
    return Info.id;
  }

  @Override
  public String getName() {
    return Info.name;
  }

}
