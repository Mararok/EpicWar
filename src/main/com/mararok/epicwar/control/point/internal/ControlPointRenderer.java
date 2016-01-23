/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2016 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.internal;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.mararok.epiccore.block.BlockHelper;
import com.mararok.epiccore.math.Position3D;
import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPoint;

/**
 * Class for rendering representation of Control Point in minecraft world
 */
public class ControlPointRenderer {
  private War war;

  public ControlPointRenderer(War war) {
    this.war = war;
  }

  /**
   * Rendering full Control Point representation
   * Used when create controlPoint
   */
  public void render(ControlPoint controlPoint) {
    Position3D centerPosition = controlPoint.getPosition();
    Block centerBlock = war.getWorld().getBlockAt(centerPosition.x, centerPosition.y, centerPosition.z);

    Block goldPlatformBlock = centerBlock.getRelative(-1, -1, -1);
    for (int x = 0; x < 3; x++) {
      for (int z = 0; z < 3; z++) {
        goldPlatformBlock.setType(Material.GOLD_BLOCK);
        goldPlatformBlock = goldPlatformBlock.getRelative(x, 0, z);
      }
    }

    centerBlock.setType(Material.BEACON);
    BlockHelper.setStainedGlassBlock(centerBlock.getRelative(BlockFace.UP), controlPoint.getOwner().getColor().getDyeColor());
  }

  /**
   * Rendering only changed elements of Control Point representation based on Faction
   */
  public void update(ControlPoint controlPoint) {
    Position3D centerPosition = controlPoint.getPosition();
    Block glassBlock = war.getWorld().getBlockAt(centerPosition.x, centerPosition.y + 1, centerPosition.z);
    BlockHelper.setBlockColor(glassBlock, controlPoint.getOwner().getColor().getDyeColor());
  }

}
