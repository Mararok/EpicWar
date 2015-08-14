/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.entity;

public abstract class EntityMapper<E extends Entity,ED> {
  protected EntityFactory<E,ED> entityFactory;
  
  public EntityMapper(EntityFactory<E, ED> entityFactory) {
    this.entityFactory = entityFactory;
  }
}
