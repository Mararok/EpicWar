/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.airdrop;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public class AirdropConfig {

  public boolean enable;

  public List<AirdropItem> ItemsDrop;
  public int price;

  public int radiusMin;
  public int radiusMax;

  public int heightMin;
  public int heightMax;

  public int slotsMin;
  public int slotsMax;

  public AirdropConfig() {
    ItemsDrop = new LinkedList<AirdropItem>();
  }

  public static AirdropConfig createFromWarConfig(EpicWarPlugin plugin,
      FileConfiguration config) {
    AirdropConfig adConfig = new AirdropConfig();
    adConfig.enable = config.getBoolean("airdrops.enable");
    if (adConfig.enable) {
      Map<String, Object> items = config.getConfigurationSection(
          "airdrops.items.list").getValues(false);
      for (String key : items.keySet()) {
        plugin.logInfo("item: " + key);
        adConfig.ItemsDrop.add(new AirdropItem(Material.getMaterial(key
            .toUpperCase()), config.getInt("airdrop.items.list." + key
            + ".amount.min"), config.getInt("airdrop.items.list." + key
            + ".amount.max"), config.getInt("airdrop.items.list." + key
            + ".chance")));
      }
      adConfig.price = config.getInt("airdrops.items.price");

      adConfig.radiusMin = config.getInt("airdrops.radius.min");
      adConfig.radiusMax = config.getInt("airdrops.radius.max");

      adConfig.heightMin = config.getInt("airdrops.height.min");
      adConfig.heightMax = config.getInt("airdrops.height.max");

      adConfig.slotsMin = config.getInt("airdrops.slots.min");
      adConfig.slotsMax = config.getInt("airdrops.slots.max");
    }
    return adConfig;
  }
}
